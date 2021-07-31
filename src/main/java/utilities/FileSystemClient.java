package utilities;

import java.io.File;

public class FileSystemClient {

    private long fileSize;
    private String filePath;
    private String fileName;

    public void findFileSize(){
        File file = new File(filePath + File.pathSeparator + fileName);
        fileSize = file.length();
    }

    public long getFileSize(){
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
}
