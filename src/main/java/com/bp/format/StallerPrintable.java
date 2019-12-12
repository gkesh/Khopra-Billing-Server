/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.format;

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
public class StallerPrintable implements Printable{
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        int result = NO_SUCH_PAGE;    
            if (pageIndex == 0) {     
                Graphics2D g2d = (Graphics2D) graphics;           
                g2d.translate((int) pageFormat.getImageableX(),(int) pageFormat.getImageableY()); 
                try{
                    
                    g2d.setFont(new Font("Monospaced",Font.PLAIN,9));
                    g2d.drawString("........................",15,20);
                }
                catch(NumberFormatException r){
                    System.err.println(r);
                }
                result = PAGE_EXISTS;    
            }    
            return result;
    }
    
}
