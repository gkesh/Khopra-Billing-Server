/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.serverconfig;

import com.bp.backup.LocalBackup;
import com.bp.format.OrderFormat;
import com.bp.job.JobFactory;
import com.bp.logging.LocalLog;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import org.json.JSONException;
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
        new LocalLog(getClass()).log("Server Started Listening at:: " 
                + this.port, LocalLog.INFO);
        System.out.println();
        while (true) {
            try (Socket client = serverSocket.accept()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        client.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null ){
                    try {
                        JSONObject data = new JSONObject(line);
                        new LocalBackup().appendJSON(data);
                        
                        try {
                            new JobFactory().schedule(OrderFormat.mapOrder(data));
                        } catch (SchedulerException exp) {
                            new LocalLog(getClass()).log(exp.getMessage(), 
                                    LocalLog.ERROR);
                        }
                        
                    } catch(JSONException exp) {
                        new LocalLog(getClass()).log(exp.getMessage(), LocalLog.ERROR);
                    }
                }
                
            } catch (IOException exp) {
                new LocalLog(getClass()).log(exp.getMessage(), LocalLog.ERROR);
            }
        }
    }
}