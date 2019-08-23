/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.controller;

//import com.bp.models.Item;

import com.bp.serverconfig.Server;
import java.io.IOException;

//import com.bp.models.Order;
//import com.bp.schedule.JobFactory;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import org.quartz.SchedulerException;

/**
 *
 * @author gkesh
 */
public class Controller {
    public static void main(String[] args) throws IOException {
//        List<Item> items = new ArrayList<>();
//        items.add(new Item("Ramen", 120.50, 2));
//        items.add(new Item("Thukpa", 240.23, 1));
//        
//        Order order = new Order(items, new Timestamp(System.currentTimeMillis() + 10000));
//        try {
//            new JobFactory().schedule(order);
//        } catch (SchedulerException ex) {
//            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
//        }
        int port = 9000;
        Server server = new Server(port);
        server.serve();
    }
}
