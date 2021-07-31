package models;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import utilities.FileSystemClient;
import utilities.NetworkClient;
import windows.MainWindow;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DownloadTask extends Thread {

    private NetworkClient networkClient;
    private DownloadItem item;
    private FileSystemClient fileSystemClient;

    public DownloadTask() {
        item = new DownloadItem();
        networkClient = new NetworkClient("");
        fileSystemClient = new FileSystemClient();
    }

    public DownloadTask(String networkUrl) {
        networkClient = new NetworkClient(networkUrl);
        item = new DownloadItem();
        fileSystemClient = new FileSystemClient();
    }

    public NetworkClient getNetworkClient() {
        return networkClient;
    }

    public void setNetworkClient(String networkUrl) {
        networkClient = new NetworkClient(networkUrl);
    }

    public DownloadItem getItem() {
        return item;
    }

    public void setItem(DownloadItem item) {
        this.item = item;
    }

    public void updateProgressBar(){
        long remoteFileSize = networkClient.getRemoteFileSize();
        long localFileSize = fileSystemClient.getFileSize();
        item.setProgressBar(remoteFileSize/localFileSize);
    }

    public void downloadFile(){
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;

        try {
            logger.info("FilePath : " + item.getFilePath());
            String fileName = fileSystemClient.getFileName();
            fileSystemClient.setFilePath(item.getFilePath()); fileSystemClient.setFileName(item.getName());
            logger.info("FileName : " + fileName);
            fileOutputStream = new FileOutputStream(item.getFilePath() + File.pathSeparatorChar + item.getName());
            inputStream = networkClient.getInputStream();
            logger.info("InputStream Created");
            byte[] networkBytes = new byte[1024];
            int bytesRead;
            long remoteFileSize = networkClient.getRemoteFileSize();
            logger.info("Downloading started");
            while (item.getDownloadState() == DownloadState.PLAY && (bytesRead = inputStream.read(networkBytes,0,1024)) != -1) {
                fileOutputStream.write(networkBytes,0,bytesRead);
                fileSystemClient.findFileSize();
//                logger.info("File Size : " + fileSystemClient.getFileSize() + "Remote File : " + remoteFileSize);
                item.setProgressBar(fileSystemClient.getFileSize()/remoteFileSize);
//                logger.info("Progress Bar: "+ item.getProgressBar());
                MainWindow.getInstance().updateTableView();
            }
        } catch(IOException ex){
            logger.info("Exception caught while downloading file : " + ex);
        }
        finally {
            try {
                logger.info("File Downloaded at: " + item.getFilePath());
                assert fileOutputStream != null;
                fileOutputStream.close();
                assert inputStream != null;
                inputStream.close();
            }
            catch(IOException ex){
                logger.info("Exception caught while downloading file : " + ex);
            }
        }
    }

    @Override
    public void run() {
        downloadFile();
    }

    private static final Logger logger = LogManager.getLogger(DownloadTask.class.getSimpleName());

}
