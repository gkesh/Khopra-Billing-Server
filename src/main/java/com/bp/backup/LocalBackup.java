/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.backup;

import com.bp.logging.LocalLog;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Admin
 */
public class LocalBackup {
    public static final String JSON_FILE = "/home/pi/lodge/backup.json";
    
    public LocalBackup() {
        initBackup();
    }
    
    private void initBackup() {
        File jf = new File(JSON_FILE);
        try {
            if (!jf.exists()) {
                jf.createNewFile();
                clearJSON();
            }
        } catch (IOException exp) {
            new LocalLog(getClass()).log(exp.getMessage(), LocalLog.ERROR);
        }
    }
    
    public JSONArray readJSON() {
        try {
            return new JSONArray(new String(Files.readAllBytes(
                    Paths.get(JSON_FILE)), StandardCharsets.UTF_8));
        } catch (IOException exp) {
            new LocalLog(getClass()).log(exp.getMessage(), LocalLog.ERROR);
            return null;
        }
    }
    public void appendJSON(JSONObject jSONObject) {
        JSONArray backup = readJSON();
        backup.put(jSONObject);
        writeJSON(backup);
    }
    
    private void writeJSON(JSONArray jSONArray) {
        try (FileWriter fw = new FileWriter(JSON_FILE)) {
            fw.write(jSONArray.toString());
            fw.flush();
        } catch (IOException exp) {
            new LocalLog(getClass()).log(exp.getMessage(), LocalLog.ERROR);
        }
    }
    
    public void clearJSON() {
        writeJSON(new JSONArray());
    }
}
