package com.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class LinesDialog extends JDialog{

	private JTextField itemNameField;
    private JTextField itemCountField;
    private JTextField itemPriceField;
    private JLabel itemNameLabel;
    private JLabel itemCountLabel;
    private JLabel itemPriceLabel;
    private JButton saveItemBtn;
    private JButton cancelBtn;
    
    
    public LinesDialog(Frame pFrame) {
    	
    	this.setTitle("Invoice Line Dialog");
        itemNameLabel = new JLabel("Item Name:");
        itemNameField = new JTextField(25);
        
        
        itemCountLabel =new JLabel("Item Count");
        itemCountField =new JTextField(25);
        
        itemPriceLabel =new JLabel("Item Price:");
        itemPriceField =new JTextField(25);
        
        saveItemBtn = new JButton("save Item");
        saveItemBtn.addActionListener(pFrame.getHandler());
        saveItemBtn.setActionCommand("Save Item");
        cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(pFrame.getHandler());
        cancelBtn.setActionCommand("Cancel Item");
        
        setLayout(new GridLayout(4,2));
        add(itemNameLabel);
        add(itemNameField);
        add(itemCountLabel);
        add(itemCountField);
        add(itemPriceLabel);
        add(itemPriceField);
        add(saveItemBtn);
        add(cancelBtn);
       
       pack();
    }
    
    public JTextField getItemNameField() {
        return itemNameField;
    }

   public JTextField getItemCountField() {
        return itemCountField;
    }
   
   public JTextField getItemPriceField() {
        return itemPriceField;
    }
}
