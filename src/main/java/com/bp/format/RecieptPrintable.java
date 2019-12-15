/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.format;

import com.bp.logging.LocalLog;
import com.bp.models.Item;
import com.bp.models.Order;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author gkesh
 */
public class RecieptPrintable implements Printable{
    private final Order order;

    public RecieptPrintable(Order order) {
        this.order = order;
    }
    
    
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
            throws PrinterException {
        int result = NO_SUCH_PAGE;    
            if (pageIndex == 0) {                    

                Graphics2D g2d = (Graphics2D) graphics;                                 

                g2d.translate((int) pageFormat.getImageableX(),
                        (int) pageFormat.getImageableY()); 
                
                try{
                    int y = 20;
                    int x = 10;
                    int yShift = 20;
                    int headerRectHeight = 30;
                    double sum = 0;
                    
                    g2d.setFont(new Font("Monospaced",Font.BOLD,9));
                    g2d.drawString("|----------------------|",x,y);y+=yShift;
                    g2d.drawString("|Khopra Community Lodge|",x,y);y+=yShift;
                    g2d.drawString("|----------------------|",x,y);
                    y+=headerRectHeight;
                    g2d.drawString("|----------------------|",x,y);y+=yShift;
                    String customer = pad("|Cust: " + order.getCustomer()),
                            user = pad("|User: " + order.getUser());
                    g2d.drawString(customer,x, y);y+=yShift;
                    g2d.drawString("|----------------------|",x,y);y+=yShift;
                    g2d.drawString("|Food Name      Q.  Amt|",x,y);y+=yShift;
                    g2d.drawString("|----------------------|",x,y);
                    y+=headerRectHeight;
                    for (Item item : order.getItems()) {
                        String subAmt = (int) item.getAmount()*item
                                .getQuantity() + "", qty = " " +
                                item.getQuantity();
                        while (subAmt.length() < 5) {
                            subAmt = " " + subAmt;
                        }
                        while (qty.length() < 3) {
                            qty = " " + qty;
                        }
                        g2d.drawString("|" + item.getName()
                                + qty +  subAmt + "|",x,y);
                        y += yShift;
                        sum += item.getAmount()*item.getQuantity();
                    }
                    g2d.drawString("|----------------------|",x,y);y+=yShift;
                    String total = pad("|Total amount: " + (int) sum);
                    g2d.drawString(total ,x,y);y+=yShift;
                    g2d.drawString("|----------------------|",x,y);y+=yShift;
                    g2d.drawString(user, x, y);y+=yShift;
                    g2d.drawString("|**********************|",x,y);y+=yShift;
                    g2d.drawString("|THANKS FOR YOUR VISIT |",x,y);y+=yShift;
                    g2d.drawString("|**********************|",x,y);y+=yShift;
                    g2d.drawString("|Date: " + 
                            new SimpleDateFormat("yyyy-MM-dd HH:mm")
                            .format(new Date()) +"|", x, y);y+=yShift;
                    g2d.drawString("|----------------------|",x,y);y+=yShift;
                    g2d.drawString("|                      |",x,y);y+=yShift;
                    g2d.drawString("|                      |",x,y);y+=yShift;
                }
                catch(NumberFormatException exp){
                    new LocalLog(getClass()).log(exp.getMessage(), LocalLog.ERROR);
                }
                result = PAGE_EXISTS;    
            }    
            return result;
    }
    
    private String pad(String str) {
        while (str.length() < 23) {
            str += " ";
        } 
        return str + "|";
    }
    
}
