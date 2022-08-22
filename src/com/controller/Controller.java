package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.model.InvoiceHeader;
import com.model.InvoiceLinesTableModel;
import com.model.InvoiceLines;
import com.view.Frame;
import com.view.HeaderDialog;
import com.view.LinesDialog;

public class Controller implements ActionListener,ListSelectionListener{
	
	private Frame frame;
	
	public Controller (Frame frame)
	{
		this.frame= frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		String actionCommand =e.getActionCommand();
		switch (actionCommand)
		{
		case "Load File":
			loadFile();
			break;
			
		case "Save File":
			saveFile();
			break;
			
		case "Exit":
			exit();
			break;
			
		case "Create New Invoice":
			createInvoice();
			break;
			
		case "Delete Invoice":
			deleteInvoice();
			break;
			
		case "Save":
			save();
			break;
			
		case "Cancel":
			deleteSelectedLine();
			break;
		
		case "Save Invoice":
			saveInvoice();
		break;
		
		case "Cancel Invoice":
			cancelInvoiceBtn();
			break;
			
		case "Save Item":
			saveItemBtn();
		break;
		
		case "Cancel Item":
			cancelItemBtn();
			break;	
		}		
	}

	/*>> 	To Discard changes		<<*/
	private void cancelItemBtn() {
		/*>> 	Disappear the Invoice Items dialog		<<*/
		frame.getLineDialog().setVisible(false);
	}

	private void saveItemBtn() {
		
		/*>> Enter and Store user inputs Data (Items) 	<<*/
        String itemName= frame.getLineDialog().getItemNameField().getText();
        String itemCountStr= frame.getLineDialog().getItemCountField().getText();
        String itemPriceStr= frame.getLineDialog().getItemPriceField().getText();
        frame.getLineDialog().setVisible(false);
        
        int itemCount =Integer.parseInt(itemCountStr);
        double itemPrice=Double.parseDouble(itemPriceStr);
        
        /*>> Save user inputs Data (Items) at invoice Lines Table	<<*/
        InvoiceHeader invoiceHeader= frame.getInvoiceHeaderList().get(frame.getjTable_InvoiceTable().getSelectedRow());
        InvoiceLines line= new InvoiceLines(itemName,itemPrice,itemCount,invoiceHeader);
        invoiceHeader.addLine(line);
        frame.getLineTableModel().fireTableDataChanged();
        frame.getHeaderTableModel().fireTableDataChanged();
	}

	private void cancelInvoiceBtn() {
		/*>> 	Disappear the Invoices Header dialog	<<*/
		frame.getHeaderDialog().setVisible(false);
	}

	private void deleteSelectedLine() {
		/*>> 	Check if there is Item selected	<<*/
		if(frame.getInvoiceHeaderList()== null)
		{
			JOptionPane.showMessageDialog(frame, "Select Item Firstly." ,"Error", JOptionPane.ERROR_MESSAGE);
		}
		/*>> 	Get index of selected line	<<*/
        int selectedLine = frame.getjTable_invoiceLine().getSelectedRow();
        /*>> 	Remove selected line	<<*/
        frame.getLineTableModel().removeRow(selectedLine);
        /*>> 	Apply change on the Header and Line Table	<<*/
        frame.getLineTableModel().fireTableDataChanged();
        frame.getHeaderTableModel().fireTableDataChanged();
	}

	private void saveInvoice() {
		
		/*>> Enter and Store user inputs Data (Items) 	<<*/
        String customerName=  frame.getHeaderDialog().getCustomerNameField().getText();
        String invoiceDateStr =  frame.getHeaderDialog().getDateField().getText();
        
        try{
        	/*>> Check the validation of date format 	<<*/
            if( isValidFormat("dd-MM-yyyy",invoiceDateStr,Locale.ENGLISH) )
            {
            	/*>> Save user inputs Data (Invoice) at invoice Header Table	<<*/
            	frame.getHeaderDialog().setVisible(false);
                int num= getMaxInvoiceNum() +1;
                InvoiceHeader newInvoiceHeader = new InvoiceHeader(num,invoiceDateStr,customerName);
                frame.getInvoiceHeaderList().add(newInvoiceHeader);
                frame.getHeaderTableModel().fireTableDataChanged();
            }
            /*>> Throw Exception for the invalid date format 	<<*/
            else
            {
            	throw new ParseException("Invalid Date Format", 0);
            }           
         	}
        
        catch(ParseException ex){
        	 JOptionPane.showMessageDialog(frame, "Wrong date format" ,"Error", JOptionPane.ERROR_MESSAGE);
         }
	}

	private void save() {
		/*>> 	Check if there is Invoice selected	<<*/
		if(frame.getInvoiceHeaderList()== null)
		{
			JOptionPane.showMessageDialog(frame, "Select Invoice Lines Firstly." ,"Error", JOptionPane.ERROR_MESSAGE);
		}
		frame.setLineDialog(new LinesDialog(frame));
        frame.getLineDialog().setVisible(true);
		
	}

	private void deleteInvoice() {
		/*>> 	Check if there is Invoice selected	<<*/
		if(frame.getInvoiceHeaderList()== null)
		{
			JOptionPane.showMessageDialog(frame, "Select Invoice Firstly." ,"Error", JOptionPane.ERROR_MESSAGE);
		}
        int row = frame.getjTable_InvoiceTable().getSelectedRow();
        frame.getHeaderTableModel().removeRow(row);
        frame.getHeaderTableModel().fireTableDataChanged();
        frame.getLineTableModel().fireTableDataChanged();
		
	}

	private void createInvoice() {
		/*>> 	Check if there is Invoice selected	<<*/
		if(frame.getInvoiceHeaderList()== null)
		{
			JOptionPane.showMessageDialog(frame, "Load Invoice Header Table Firstly." ,"Error", JOptionPane.ERROR_MESSAGE);
		}
		
		else {
		frame.showHeaderDialog(new HeaderDialog(frame));
		frame.getHeaderDialog().setVisible(true);
		}
	}

	private void exit() {
		System.exit(0);
	}

	private void saveFile() {
		/*>>	Check if there is any file to save		<<*/
		if(frame.getInvoiceHeaderList()== null)
		{
			JOptionPane.showMessageDialog(frame, "Nothing to save.", "Invooice Header", JOptionPane.INFORMATION_MESSAGE);
		}
		
/*>>	Select or create file.csv to save Invoices	<<*/
		else {
	        JOptionPane.showMessageDialog(frame, "Please Choose or Create File.csv for Header to Save", "Invooice Header",
	        		JOptionPane.INFORMATION_MESSAGE);
	        JFileChooser file = new JFileChooser();
	        int option = file.showOpenDialog(frame);
	        if (option == JFileChooser.APPROVE_OPTION) {
	            File headerfile = file.getSelectedFile();
	            
	            //SimpleDateFormat  d=new SimpleDateFormat("dd-MM-yyyy",Locale.ENGLISH);
	            //DateTimeFormatter oldPattern = DateTimeFormatter .ofPattern("dd/MM/yyyy");
	            //DateTimeFormatter newPattern = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	            
	            /*>>	 Save Invoices Header Table 	<<*/
				try {
					PrintWriter out = new PrintWriter(headerfile);
		            for (InvoiceHeader header : frame.getInvoiceHeaderList()) {
		               
		            	//date =header.getInvoiceDate();
		            	//LocalDateTime datetime = LocalDateTime.parse(strDate, oldPattern);
		            	System.out.println("strDate: " +header.getInvoiceDate());
		            	
		                out.printf("%d,%s,%s",header.getInvoiceNum(),header.getInvoiceDate(),header.getCustomerName());
		                out.println();
		             }
		            
		            out.close();
		            
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 

	             JOptionPane.showMessageDialog(frame, "Successfully Header Saved", "Information", JOptionPane.INFORMATION_MESSAGE);
	        }
	        
	        /*>> 	Save Invoices Lines Table 	<<*/
	        JOptionPane.showMessageDialog(frame, "Please Chosce or Create File.csv for Lines to Save", "Invooice Line",
	        		JOptionPane.INFORMATION_MESSAGE);
	        file = new JFileChooser();
	        option = file.showOpenDialog(frame);
	        if (option == JFileChooser.APPROVE_OPTION) {
	          File Csvfilee = file.getSelectedFile();
	          
	          try {
	          PrintWriter outt = new PrintWriter(Csvfilee);
	          for (InvoiceHeader header : frame.getInvoiceHeaderList()) {
	              for(InvoiceLines Lines :header.getLines()){
	              outt.printf("%d ,%s ,%s,%d",header.getInvoiceNum(),Lines.getItemName(),""+Lines.getItemPrice(),Lines.getItemCount());
	              outt.println();
	           }}
	          outt.close();
	      
	          	}
	          catch(Exception e)
	          	{
					e.printStackTrace();  
	          	}
	          	JOptionPane.showMessageDialog(frame, "Successfully Lines Saved", "Information", JOptionPane.INFORMATION_MESSAGE);
	        	}
			}	
		
	}

	private void loadFile() {
		JOptionPane.showMessageDialog(frame, "Please, Choose Invoices Table file.csv format.", "Help - Selecting Tables",
				JOptionPane.INFORMATION_MESSAGE);
		
		// Get absolute path of the file 
		File file = new File("");
		String currentPath = file.getAbsolutePath();
		try {
		// Show FileChoser 
		JFileChooser fileChooser = new JFileChooser(currentPath);
		int result = fileChooser.showOpenDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION)
		{
			
			File headerFile = fileChooser.getSelectedFile();
			String headerStrPath = headerFile.getAbsolutePath();
			Path headerPath = Paths.get(headerStrPath);
			List<String> headerLines =Files.lines(headerPath).collect(Collectors.toList());
			
			ArrayList<InvoiceHeader> invoiceHeaderList = new ArrayList<>();
			
			// Convert CSV File Into array of String
			for (String headerLine : headerLines)
			{
				String parts[]= headerLine.split(",");
				int no =Integer.parseInt(parts[0]);
				String invoiceDateStr = parts[1];
				//Date invoiceDate=vDate.parse(invDateStr);
				//int totalPrice = getInvoiceTotal();
				InvoiceHeader invoiceHeader= new InvoiceHeader(no , invoiceDateStr,parts [2]);
				invoiceHeaderList.add(invoiceHeader);	
			}
			
			JOptionPane.showMessageDialog(frame, "Please, Choose Invoices Items Table file.csv format.", "Help - Selecting Tables",
					JOptionPane.INFORMATION_MESSAGE);
			result = fileChooser.showOpenDialog(frame);
			if (result == JFileChooser.APPROVE_OPTION)
			{
				String lineStringPath =fileChooser.getSelectedFile().getAbsolutePath();
				Path LinePath = Paths.get(lineStringPath);
				List<String> lineLines =Files.lines(LinePath).collect(Collectors.toList());
				
				for (String Line : lineLines)
				{
					String parts[]= Line.split(",");
					int invoiceNo =Integer.parseInt(parts[0]);
					int count = Integer.parseInt(parts[3]);
					double price =Double.parseDouble(parts[2]);
					InvoiceHeader header = getInvoiceHeaderById(invoiceHeaderList,invoiceNo);
					InvoiceLines line=new InvoiceLines( parts[1],price,count,header);
					header.getLines().add(line);
					
				}
				frame.setInvoiceHeaderList(invoiceHeaderList);
			}
		} 
			
		}
		
		catch (NoSuchFileException e) {
			JOptionPane.showMessageDialog(frame, " Folder/File path is not found." ,
					"Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(frame, "Wrong file format.",
					"Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		/*>> 	Check if there is Invoice Header loaded	<<*/
		if(frame.getInvoiceHeaderList()== null)
		{
			JOptionPane.showMessageDialog(frame, "Load Invoice Header Table Firstly." ,"Error", JOptionPane.ERROR_MESSAGE);
		}
		int selectedRow =frame.getjTable_invoiceHeader().getSelectedRow();
		if(selectedRow >= 0){
	        InvoiceHeader row = frame.getHeaderTableModel().getInvoices().get(selectedRow);
	       
	        frame.getjLabel_CustomerName().setText(row.getCustomerName());
	        frame.getjTextField_InvoiceDate().setText(row.getInvoiceDate().toString());
	        frame.getjLabel_InvoiceNumberDisplay().setText(""+row.getInvoiceNum());
	        frame.getjLabel_InvoiceTotalDisplay().setText(""+row.getInvoiceTotal());
	        
	        ArrayList<InvoiceLines> lines= row.getLines();
	        frame.setLineTableModel(new InvoiceLinesTableModel(lines));
	        
	        frame.getjTable_invoiceLine().setModel( frame.getLineTableModel());
	        frame.getLineTableModel().fireTableDataChanged();
	    
		}
	}
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////
	
	public static boolean isValidFormat(String format, String value, Locale locale) {
	    LocalDateTime ldt = null;
	    DateTimeFormatter fomatter = DateTimeFormatter.ofPattern(format, locale);

	    try {
	        ldt = LocalDateTime.parse(value, fomatter);
	        String result = ldt.format(fomatter);
	        return result.equals(value);
	    } catch (DateTimeParseException e) {
	        try {
	            LocalDate ld = LocalDate.parse(value, fomatter);
	            String result = ld.format(fomatter);
	            return result.equals(value);
	        } catch (DateTimeParseException exp) {
	            try {
	                LocalTime lt = LocalTime.parse(value, fomatter);
	                String result = lt.format(fomatter);
	                return result.equals(value);
	            } catch (DateTimeParseException e2) {
	                // Debugging purposes
	                //e2.printStackTrace();
	            }
	        }
	    }

	    return false;
	}
	
	private int getMaxInvoiceNum()
    {
      int num = 0;
      for(InvoiceHeader headr:  frame.getInvoiceHeaderList())
      {
         if(headr.getInvoiceNum() > num)
         {
           num= headr.getInvoiceNum();
         }
      }
      return num;
    }
	
	private InvoiceHeader getInvoiceHeaderById(ArrayList<InvoiceHeader> invoices, int id)
	{
		for(InvoiceHeader invioce: invoices)
		{
			if (invioce.getInvoiceNum()== id)
			{
				return invioce;
			}
		}
		return null;
	}
	
}
