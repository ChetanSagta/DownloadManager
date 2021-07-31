package utilities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;

public class NetworkClient {

    private URL url = null;
    private URLConnection urlConnection = null;
    private String networkUrl = null;
    private long remoteFileSize = 1;

    public NetworkClient(String networkUrl) {
        try {
            url = new URL(networkUrl);
            urlConnection = url.openConnection();
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
        long contentLength = urlConnection.getContentLengthLong();
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
        remoteFileSize = urlConnection.getContentLengthLong();
    }

    public long getRemoteFileSize() {
        return remoteFileSize;
    }

    public String getNetworkUrl() {
        return networkUrl;
    }

    public InputStream getInputStream() {
        try {
            setRemoteFileSize();
            return urlConnection.getInputStream();
        } catch (IOException ex) {
            logger.info("Exception Caught While Returning input stream from Network Client : " + ex);
        }
        return null;
    }

    private static final Logger logger = LogManager.getLogger(NetworkClient.class.getSimpleName());
}
