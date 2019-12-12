/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.job;

import com.bp.backup.DriveBackup;
import com.bp.logging.LocalLog;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author Admin
 */
public class BackupJob implements Job {

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        try {
            new DriveBackup().upload();
        } catch (IOException | GeneralSecurityException exp) {
            new LocalLog(getClass()).log(exp.getMessage(), LocalLog.ERROR);
        }
    }

}
