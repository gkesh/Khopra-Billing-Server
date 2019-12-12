/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.job;

import com.bp.format.RecieptFormat;
import com.bp.format.StallerPrintable;
import com.bp.logging.LocalLog;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author Admin
 */
public class StallerJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws 
            JobExecutionException {
        try {
            PrinterJob pj = PrinterJob.getPrinterJob();
            pj.setPrintable(new StallerPrintable(),
                    RecieptFormat.getPageFormat(pj));
            pj.print();
            new LocalLog(getClass()).log("Staller Executed", LocalLog.INFO);
        } catch (PrinterException exp) {
            new LocalLog(getClass()).log(exp.getMessage(), LocalLog.ERROR);
        }
        
    }
    
}
