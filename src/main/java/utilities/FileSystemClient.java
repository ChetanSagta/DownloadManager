package utilities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class FileSystemClient {

    private long fileSize;
    private String filePath;
    private String fileName;
    File file = null;

    public void findFileSize() {
        file = new File(filePath + File.separatorChar + fileName);
        fileSize = file.length();
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileSizeStr() {
        long tempFileSize;
        if ((tempFileSize = fileSize / 1024) < 1024) {
            return tempFileSize + "KB";
        } else if ((tempFileSize = fileSize / (1024 * 1024)) < 1024) {
            return tempFileSize + "MB";
        } else if ((tempFileSize = fileSize / (1024 * 1024 * 1024)) < 1024) {
            return tempFileSize + "GB";
        } else
            return fileSize + "B";
    }

    public void createDirectories() {
        file = new File(filePath);
        if (!file.isDirectory()) {
            boolean directoryCreated = file.mkdirs();
            if (!directoryCreated) {
                logger.info("Directory not created");
            }
        }
    }

    public boolean isFilePresent(){
        File tempFile = new File(filePath + File.separatorChar + fileName);
        return tempFile.exists();
    }

    public void updateFileName(){
        fileName = fileName + "(1)";
    }

    public void navigateToFilePath(){
        try {
            Runtime.getRuntime().exec("explorer.exe /select," + filePath + File.separatorChar + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final Logger logger = LogManager.getLogger(FileSystemClient.class.getSimpleName());
}
