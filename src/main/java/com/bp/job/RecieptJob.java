/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.job;

import com.bp.format.RecieptFormat;
import com.bp.format.RecieptPrintable;
import com.bp.logging.LocalLog;
import com.bp.models.Order;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;

/**
 *
 * @author gkesh
 */
public class RecieptJob implements Job{
    
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
        } catch (SchedulerException | PrinterException exp) {
            new LocalLog(getClass()).log(exp.getMessage(), LocalLog.ERROR);
        }
    }
    
}
