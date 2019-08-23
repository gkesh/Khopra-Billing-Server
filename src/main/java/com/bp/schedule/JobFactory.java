/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.schedule;

import com.bp.models.Order;
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
    private final JobDetail jobDetail = JobBuilder.newJob(ScheduledJob.class)
            .build();
    private Scheduler scheduler;
    private Trigger trigger;
    
    public void schedule(Order order) throws SchedulerException{
        System.out.println("Reached...");
        trigger = TriggerBuilder.newTrigger().withIdentity("Order Printiing")
                .startAt(order.getTime()).withSchedule(SimpleScheduleBuilder
                .simpleSchedule()
                .withRepeatCount(0))
                .build();
        scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.getContext().put("order", order);
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
