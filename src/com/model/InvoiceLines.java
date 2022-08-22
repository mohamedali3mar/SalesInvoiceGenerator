package com.model;

public class InvoiceLines {
	/*>>	 Declaration of variables		<<*/
    private String itemName;
    private double itemPrice;
    private int count;
    private double lineTotal;
    private InvoiceHeader invoice;

    /*>>		Generated Constructor	<<*/
    public InvoiceLines(String itemName, double itemPrice, int Count, InvoiceHeader invoice) 
    {
    	
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.count = Count;
        this.invoice = invoice;
        this.setItemTotal(this.count * this.itemPrice);
    }

    /*>>	 Generated Getter and Setter methods 	<<*/
    public InvoiceHeader getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceHeader invoice) {
        this.invoice = invoice;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemCount() {
        return count;
    }

    public void setItemCount(int itemCount) {
        this.count = itemCount;
    }

    public double getitemTotal() {
        return count * itemPrice;
    }

    public double getItemTotal() {
    	return lineTotal;
    }

    public void setItemTotal(double lineTotal) {
    	this.lineTotal = lineTotal;
    }
	@Override
	public String toString() {
		return "InvoiceLine [itemName=" + itemName + ", itemPrice=" + itemPrice + ", Count=" + count +"]";
	}
}
