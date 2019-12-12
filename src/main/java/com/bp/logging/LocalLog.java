/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Admin
 */
public class LocalLog {
    private static Logger logger;
    public static final int ERROR = 100;
    public static final int INFO = 101;
    
    public LocalLog() {
        logger = LogManager.getLogger(LocalLog.class);
    }
   
    public LocalLog(Class clazz) {
        logger = LogManager.getLogger(clazz);
    }
    
    public void log(String message, int logLevel) {
        if (logLevel == ERROR) {
            logger.error(message);
        } else if ( logLevel == INFO) {
            logger.info(message);
        }
    }
    
}
