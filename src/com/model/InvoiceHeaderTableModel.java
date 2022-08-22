package com.model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class InvoiceHeaderTableModel extends AbstractTableModel {

	private ArrayList<InvoiceHeader> data;
	private String cols[]= {"No.","Date","Customer","Total"};
	
	
	public InvoiceHeaderTableModel(ArrayList<InvoiceHeader> data) {
		//super();
		this.data = data;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		//return 3;
		return cols.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		InvoiceHeader header  =data.get(rowIndex);
		switch(columnIndex)
		{
		case 0:
			return header.getInvoiceNum();
		case 1:
			return header.getInvoiceDate();
			//break;
		case 2:
			return header.getCustomerName();
			//break;
		case 3:
			return header.getInvoiceTotal();
			//break;
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
    
    public ArrayList<InvoiceHeader> getInvoices() {
        return data;
    }
}
