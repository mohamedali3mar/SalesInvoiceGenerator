package com.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;


@SuppressWarnings("serial")
public class InvoiceLinesTableModel extends AbstractTableModel {

	private ArrayList<InvoiceLines> data;
	private String cols[]= {"Item Name","Item Price","count","Item Total"};
	
	
	public InvoiceLinesTableModel(ArrayList<InvoiceLines> data) {
		super();
		this.data = data;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
        if(this.data == null){
        	data = new ArrayList<>();
        }
		return data.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return cols.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		InvoiceLines line  =data.get(rowIndex);
		switch(columnIndex)
		{
		case 0:
			return line.getItemName();
		case 1:
			return line.getItemPrice();
		case 2:
			return line.getItemCount();

		case 3:
			return line.getitemTotal();
		}
		return "";
		
	}
	
	@Override
	 public String getColumnName(int column)
	 {
		 return cols[column];
	 }
	
    public void removeRow(int row) {
            data.remove(row);

   }
    
    /////////////////////////////////////
    
    public List<InvoiceLines> getInvoiceLines() {
        return data;
    }

}
