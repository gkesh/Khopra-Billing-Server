/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.serverconfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author gkesh
 */
public class ClientListener extends Thread {
    
    private final Socket client;

    public ClientListener(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        
        try {
            PrintStream ps = new PrintStream(client.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    client.getInputStream()));
            
            String line;
            
            while((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
}
