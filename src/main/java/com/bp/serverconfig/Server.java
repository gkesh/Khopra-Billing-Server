/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.serverconfig;

import com.bp.format.OrderFormat;
import com.bp.schedule.JobFactory;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import org.json.JSONObject;
import org.quartz.SchedulerException;
/**
 *
 * @author gkesh
 */

public class Server {
    
    private final int port;

    public Server(int port) {
        this.port = port;
    }

    public void serve() throws IOException, SchedulerException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server Listening at:: " + this.port);
        while (true) {
            try (Socket client = serverSocket.accept()) {
                
                PrintStream ps = new PrintStream(client.getOutputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        client.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null ){
                    System.out.println(line);
                    JSONObject data = new JSONObject(line.substring(1, line.length()-1).replace("\\", ""));
                    System.out.println(data);
                    
                    try {
                        new JobFactory().schedule(OrderFormat.mapOrder(data));
                    } catch (SchedulerException ex) {
                        System.err.println(ex);
                    }
                }
                
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
}