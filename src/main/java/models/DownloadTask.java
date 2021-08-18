package models;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import utilities.FileSystemClient;
import utilities.NetworkClient;
import windows.MainWindow;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@XmlRootElement
public class DownloadTask implements Runnable{

    @XmlElement
    private NetworkClient networkClient;
    private DownloadItem item;
    @XmlElement
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

    public FileSystemClient getFileSystemClient(){
        return fileSystemClient;
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
        item.setProgressBar((localFileSize*1.0)/remoteFileSize);
    }

    public void downloadFile(){
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        long startSize = 0;

        try {
            fileSystemClient.createDirectories();
            String fileStreamPath = fileSystemClient.getFilePath() + File.separatorChar + fileSystemClient.getFileName();
            //just a workaround to create new connection for downloading using old connection already created
            // when fetching the remote file name during adding new url to the download list
            if(fileSystemClient.isFilePresent()) {
                fileSystemClient.findFileSize();
                startSize = fileSystemClient.getFileSize();
                if( startSize== networkClient.getRemoteFileSize()) {
                    item.setDownloadState(DownloadState.COMPLETED);
                    updateProgressBar();
                    MainWindow.getInstance().updateTableView();
                    return;
                }
                networkClient = new NetworkClient(networkClient.getNetworkUrl());
                networkClient.setRangeBytes(startSize);
                fileOutputStream = new FileOutputStream(fileStreamPath, true);
            }
            else
                fileOutputStream = new FileOutputStream(fileStreamPath);
            inputStream = networkClient.getInputStream();
            networkClient.setRemoteFileSize();
            byte[] networkBytes = new byte[4096];
            int bytesRead;
            long startTime = System.nanoTime();

            while (item.getDownloadState() == DownloadState.PLAY && (bytesRead = inputStream.read(networkBytes,0,4096)) != -1) {
                fileOutputStream.write(networkBytes,0,bytesRead);
                fileSystemClient.findFileSize();
                updateProgressBar();
                if(item.getProgressBar().getProgress() == 1.0) item.setDownloadState(DownloadState.COMPLETED);
                long currentTime = System.nanoTime();
                long currentSize = fileSystemClient.getFileSize();
                networkClient.calculateDownloadSpeed(currentSize - startSize,currentTime-startTime);
                item.setPercentage(fileSystemClient.getFileSize() * 100 / networkClient.getRemoteFileSize() +"%");
                MainWindow.getInstance().updateTableView();
            }
        } catch(IOException ex){
            logger.info("Exception caught while downloading file : " + ex);
        }
        finally {
            try {
                if(fileOutputStream!=null)
                    fileOutputStream.close();
                if(inputStream!=null)
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
