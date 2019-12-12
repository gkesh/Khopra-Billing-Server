/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.job;

import com.bp.logging.LocalLog;
import com.bp.models.Order;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author gkesh
 */
public class JobFactory {
    private Scheduler scheduler;
    private Trigger trigger;
    
    public void schedule(Order order) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(RecieptJob.class).build();
        trigger = TriggerBuilder.newTrigger().withIdentity("Order Printiing")
                .startAt(order.getTime()).withSchedule(SimpleScheduleBuilder
                .simpleSchedule()
                .withRepeatCount(0))
                .build();
        scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.getContext().put("order", order);
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
        new LocalLog(getClass()).log("Order Data Recieved...", LocalLog.INFO);
    }
    
    public void stall() throws SchedulerException {
        JobDetail stallDetail = JobBuilder.newJob(StallerJob.class).build();
        trigger = TriggerBuilder.newTrigger().withIdentity("Staller Triggered")
                .startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInHours(1).repeatForever()).build();
        scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(stallDetail, trigger);
        new LocalLog(getClass()).log("Stalling...", LocalLog.INFO);
    }
    
    public void backup() throws SchedulerException {
        JobDetail backupDetail = JobBuilder.newJob(BackupJob.class).build();
        trigger = TriggerBuilder.newTrigger().withIdentity("Backup Triggered")
                    .withSchedule(CronScheduleBuilder
                        .cronSchedule("0 0 22 * * ? *")).build();
        scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(backupDetail, trigger);
        new LocalLog(getClass()).log("Backup scheduled...", LocalLog.INFO);
    }
}
