package utilities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;

public class NetworkClient {

    private URL url = null;
    private HttpURLConnection httpURLConnection = null;
    private String networkUrl = null;
    private long remoteFileSize ;
    private String downloadSpeed;

    public NetworkClient(){}

    public NetworkClient(String networkUrl) {
        try {
            setNetworkUrl(networkUrl);
            if(this.networkUrl.equals(""))  return ;
            url = new URL(this.networkUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException ex) {
            logger.debug("Exception raised : " + ex);
        }
    }

    public void setNetworkUrl(String networkUrl) {
        this.networkUrl = networkUrl;
    }

    public String getRemoteFileName() {
        return Paths.get(url.getPath()).getFileName().toString();
    }

    public String getFileSizeStr() {
        long contentLength = httpURLConnection.getContentLengthLong();
        long tempContentLength;
        if ((tempContentLength = contentLength / 1024) < 1024) {
            return tempContentLength + "KB";
        } else if ((tempContentLength = contentLength / (1024 * 1024)) < 1024) {
            return tempContentLength + "MB";
        } else if ((tempContentLength = contentLength / (1024 * 1024 * 1024)) < 1024) {
            return tempContentLength + "GB";
        } else
            return contentLength + "B";
    }

    public void setRemoteFileSize() {
        remoteFileSize = httpURLConnection.getContentLengthLong();
    }

    public long getRemoteFileSize() {
        return remoteFileSize;
    }

    public String getNetworkUrl() {
        return networkUrl;
    }

    public String getDownloadSpeed() {
        return downloadSpeed;
    }

    public void setDownloadSpeed(String downloadSpeed) {
        this.downloadSpeed = downloadSpeed;
    }

    public InputStream getInputStream() {
        try {
            setRemoteFileSize();
            return httpURLConnection.getInputStream();
        } catch (IOException ex) {
            logger.info("Exception Caught While Returning input stream from Network Client : " + ex);
        }
        return null;
    }

    public void openConnection(){
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setRangeBytes(long fileLength){
        httpURLConnection.setRequestProperty("Range", "bytes="+fileLength+"-");
        try {
            url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void calculateDownloadSpeed(long size, long timeTaken){
        double downloadRatio = 1.0*(size*1000000000)/timeTaken;
        if ((downloadRatio = downloadRatio / 1024) < 1024) {
            downloadSpeed =  downloadRatio + "KB/s";
        } else if ((downloadRatio = downloadRatio / 1024) < 1024) {
            downloadSpeed = downloadRatio + "MB/s";
        } else if ((downloadRatio = downloadRatio / 1024) < 1024) {
            downloadSpeed = downloadRatio + "GB/s";
        } else
            downloadSpeed = downloadRatio + "B/s";

    }

    private static final Logger logger = LogManager.getLogger(NetworkClient.class.getSimpleName());
}
