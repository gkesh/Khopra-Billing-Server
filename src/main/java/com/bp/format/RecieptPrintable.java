/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.format;

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
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        int result = NO_SUCH_PAGE;    
            if (pageIndex == 0) {                    

                Graphics2D g2d = (Graphics2D) graphics;                                 

                g2d.translate((int) pageFormat.getImageableX(),(int) pageFormat.getImageableY()); 
                
                try{
                    int y = 20;
                    int yShift = 10;
                    int headerRectHeight = 15;
                    double sum = 0;
                    
                    g2d.setFont(new Font("Monospaced",Font.PLAIN,9));
                    g2d.drawString("-------------------------------------",12,y);y+=yShift;
                    g2d.drawString("      Restaurant Bill Receipt        ",12,y);y+=yShift;
                    g2d.drawString("-------------------------------------",12,y);y+=headerRectHeight;
                    g2d.drawString("-------------------------------------",10,y);y+=yShift;
                    g2d.drawString("User: " + order.getUser(), 10, y);y+=yShift;
                    g2d.drawString("-------------------------------------",10,y);y+=yShift;
                    g2d.drawString(" Food Name                 T.Price   ",10,y);y+=yShift;
                    g2d.drawString("-------------------------------------",10,y);y+=headerRectHeight;
                    for (Item item : order.getItems()) {
                        g2d.drawString(" "+item.getName()+"                  " + item.getAmount()*item.getQuantity()+"  ",10,y);
                        y += yShift;
                        sum += item.getAmount()*item.getQuantity();
                    }
                    g2d.drawString("-------------------------------------",10,y);y+=yShift;
                    g2d.drawString(" Total amount: " + sum + "               ",10,y);y+=yShift;
                    g2d.drawString("-------------------------------------",10,y);y+=yShift;
                    g2d.drawString("          Free Home Delivery         ",10,y);y+=yShift;
                    g2d.drawString("             03111111111             ",10,y);y+=yShift;
                    g2d.drawString("*************************************",10,y);y+=yShift;
                    g2d.drawString("    THANKS TO VISIT OUR RESTUARANT   ",10,y);y+=yShift;
                    g2d.drawString("*************************************",10,y);y+=yShift;
                }
                catch(NumberFormatException r){
                }
                result = PAGE_EXISTS;    
            }    
            return result;
    }
    
}
