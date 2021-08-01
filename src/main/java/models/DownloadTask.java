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

public class DownloadTask implements Runnable{

    private NetworkClient networkClient;
    private DownloadItem item;
    private final FileSystemClient fileSystemClient;

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
        logger.info("Remote: " + remoteFileSize + " -> Local: " + localFileSize);
        item.setProgressBar((remoteFileSize*1.0)/localFileSize);
    }

    public void downloadFile(){
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;

        try {
            logger.info("FilePath : " + item.getFilePath());
            fileSystemClient.setFilePath(item.getFilePath());
            fileSystemClient.setFileName(item.getFileName());

            fileSystemClient.createFilePath();

            fileOutputStream = new FileOutputStream(fileSystemClient.getFilePath() + File.separatorChar + fileSystemClient.getFileName());
            inputStream = networkClient.getInputStream();
            logger.info("InputStream Created");
            byte[] networkBytes = new byte[1024];
            int bytesRead;
            logger.info("Downloading started");
            while (item.getDownloadState() == DownloadState.PLAY && (bytesRead = inputStream.read(networkBytes,0,1024)) != -1) {
                fileOutputStream.write(networkBytes,0,bytesRead);
                fileSystemClient.findFileSize();
                updateProgressBar();
                logger.info("Current Progress Value : " + item.getProgressBar().getProgress());
                if(item.getProgressBar().getProgress() == 1.0) item.setDownloadState(DownloadState.COMPLETED);
                MainWindow.getInstance().updateTableView();
            }
            logger.info("Current Download Status " + item.getDownloadState());
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
