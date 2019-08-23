/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.schedule;

import com.bp.format.RecieptFormat;
import com.bp.format.RecieptPrintable;
import com.bp.models.Order;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;

/**
 *
 * @author gkesh
 */
public class ScheduledJob implements Job{
    
    @Override
    public void execute(JobExecutionContext context) throws 
            JobExecutionException {
        try {
            SchedulerContext schedulerContext = context.getScheduler()
                    .getContext();
            PrinterJob pj = PrinterJob.getPrinterJob();
            pj.setPrintable(new RecieptPrintable(
                    (Order) schedulerContext.get("order")),
                    RecieptFormat.getPageFormat(pj));
            pj.print();
        } catch (SchedulerException | PrinterException ex) {
            Logger.getLogger(ScheduledJob.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
}
