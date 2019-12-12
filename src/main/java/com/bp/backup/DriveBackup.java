/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.backup;

import com.bp.logging.LocalLog;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.File;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.security.GeneralSecurityException;

import java.text.SimpleDateFormat;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Admin
 */
public class DriveBackup {

    private static final String APPLICATION_NAME = "Lodge Data Backup";
    private static final JsonFactory JSON_FACTORY = JacksonFactory
            .getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static final List<String> SCOPES = Collections.
            singletonList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_PATH = "/credentials.json";

    private static Credential 
        getCredentials(final NetHttpTransport HTTP_TRANSPORT) {
        try (InputStream in = DriveBackup.class
                .getResourceAsStream(CREDENTIALS_PATH)) {
            GoogleClientSecrets clientSecrets = GoogleClientSecrets
                    .load(JSON_FACTORY, new InputStreamReader(in));
            GoogleAuthorizationCodeFlow flow
                    = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT,
                            JSON_FACTORY, clientSecrets, SCOPES)
                            .setDataStoreFactory(new FileDataStoreFactory(
                                    new java.io.File(TOKENS_DIRECTORY_PATH))
                            ).setAccessType("offline").build();
            LocalServerReceiver receiver = new LocalServerReceiver.Builder()
                    .setPort(8888).build();
            return new AuthorizationCodeInstalledApp(flow, receiver)
                    .authorize("user");
        } catch (IOException exp) {
            new LocalLog(DriveBackup.class).log(exp.getMessage() 
                    + "\nResource Not Found:: " + CREDENTIALS_PATH,
                    LocalLog.ERROR);
            return null;
        }
    }

    private Drive getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport
                .newTrustedTransport();
        return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME).build();
    }

    public void upload() throws IOException, GeneralSecurityException {
        File fileMetaData = new File();
        fileMetaData.setName("lodge-backup-"
                + new SimpleDateFormat("YYYY-MM-dd").format(new Date()));
        java.io.File filePath = new java.io.File(LocalBackup.JSON_FILE);
        FileContent mediaContent
                = new FileContent("application/json", filePath);
        File file = getService().files().create(fileMetaData, mediaContent)
                .setFields("id").execute();
        new LocalLog(getClass()).log("File ID: " + file.getId(), LocalLog.INFO);

        // Read files from drive for confirmation
        List<File> files = list();
        if (files == null || files.isEmpty()) {
            new LocalLog(getClass()).log("No files found...", LocalLog.INFO);
        } else {
            new LocalLog(getClass()).log("Files:", LocalLog.INFO);
            files.forEach((File temp) -> {
                new LocalLog().log(temp.getName() + " " +
                        temp.getId() + "\n", LocalLog.INFO);
                if (file.getId().equals(temp.getId())) {
                    new LocalBackup().clearJSON();
                    new LocalLog(getClass()).log("Backup file cleaned..",
                        LocalLog.INFO);
                }
            });
        }
    }

    public List<File> list() throws IOException, GeneralSecurityException {
        FileList result = getService().files().list()
                .setPageSize(10).setFields("nextPageToken, files(id, name)")
                .execute();
        List<File> files = result.getFiles();
        if (files == null || files.isEmpty()) {
            return null;
        } else {
            return files;
        }
    }
}
