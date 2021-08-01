package utilities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;

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

    public void createFilePath() {
        file = new File(filePath);
        if (!file.isDirectory()) {
            boolean directoryCreated = file.mkdirs();
            if (!directoryCreated) {
                logger.info("Directory not created");
            }
        }
    }

    private static final Logger logger = LogManager.getLogger(FileSystemClient.class.getSimpleName());
}
