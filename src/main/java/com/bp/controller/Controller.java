/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.controller;

//import com.bp.models.Item;

import com.bp.job.JobFactory;
import com.bp.logging.LocalLog;
import com.bp.serverconfig.Server;
import java.io.IOException;
import org.quartz.SchedulerException;


/**
 *
 * @author gkesh
 */
public class Controller {
    public static void main(String[] args) throws IOException {
        int port = 9000;
        Server server = new Server(port);
        JobFactory jf = new JobFactory();
        try {
            jf.backup();
            jf.stall();
            server.serve();
        } catch (SchedulerException exp) {
            new LocalLog()
                    .log(exp.getMessage(), LocalLog.ERROR);
        }
    }
}
