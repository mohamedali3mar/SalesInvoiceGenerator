package com.model;

import java.util.ArrayList;

public class InvoiceHeader {

	/*>>	 Declaration of variables		<<*/
    private int invoiceNum;
    private String invoiceDate;
    private String customerName;
	private double invoiceTotal;
    private ArrayList<InvoiceLines> lines;
    
    /*>>		Generated Constructor	<<*/
    public InvoiceHeader(int invoiceNum, String invoiceDate, String customerName) {
        this.invoiceNum = invoiceNum;
        this.invoiceDate = invoiceDate;
        this.customerName = customerName;
    }
    
 

	/*>>	 Generated Getter and Setter methods 	<<*/
	public int getInvoiceNum() {
		return invoiceNum;
	}

	public void setInvoiceNum(int invoiceNum) {
		this.invoiceNum = invoiceNum;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public double getInvoiceTotal() {
		double invoiceTotal =0;
		
		/*>> Calculate the price of the Items <<*/
		for(InvoiceLines line : getLines())
		{
			invoiceTotal += line.getitemTotal();
		}
		
		return invoiceTotal;
	}

	
	public void setInvoiceTotal(double invoiceTotal) {
		this.invoiceTotal = invoiceTotal;
	}
	
	public ArrayList<InvoiceLines> getLines() {
		if(null == lines)
		{
			lines =new ArrayList<InvoiceLines>();
		}
		return lines;
	}

        
	public void addLine (InvoiceLines line){
            getLines().add(line);
            setInvoiceTotal(getInvoiceTotal() + line.getItemTotal());
        }

}
