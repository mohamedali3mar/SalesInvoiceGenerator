package com.view;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class HeaderDialog extends JDialog{
    private JTextField customerNameField;
    private JTextField dateField;
    private JLabel customerNameLabel;
    private JLabel dateLabel;
    private JButton SaveInvoiceBtn;
    private JButton cancelInvoiceBtn;
    
    public HeaderDialog(Frame frame)
    {
    	this.setTitle("Invoice Header Dialog");
        customerNameLabel = new JLabel("customer Name:");
        customerNameField = new JTextField(25);
        SaveInvoiceBtn = new JButton("Ok");
        SaveInvoiceBtn.addActionListener(frame.getHandler());
        SaveInvoiceBtn.setActionCommand("Save Invoice");
        dateLabel =new JLabel("Invoice Date:");
        dateField =new JTextField(25);
        dateField.setText("dd-MM-yyyy");
        cancelInvoiceBtn = new JButton("Cancel");
        cancelInvoiceBtn.addActionListener(frame.getHandler());
        cancelInvoiceBtn.setActionCommand("Cancel Invoice");
        setLayout(new GridLayout(3,2));
        add(dateLabel);
        add(dateField);
        add(customerNameLabel);
        add(customerNameField);
        add(SaveInvoiceBtn);
        add(cancelInvoiceBtn);
       
       pack();
    }

    public JTextField getCustomerNameField() {
        return customerNameField;
    }

   public JTextField getDateField() {
        return dateField;
    }
}

