package models;

import javafx.scene.control.ProgressBar;

public class DownloadItem{

    private int sNo;
    private String fileName;
    private long totalSize;
    private String totalSizeStr;
    private DownloadState downloadState;
    private final ProgressBar progressBar;
    private String filePath;
    private String downloadSpeed;

    public DownloadItem(){
        sNo=0;
        fileName ="";
        totalSize=0;
        totalSizeStr="";
        downloadState = DownloadState.STOP;
        filePath="";
        downloadSpeed="0";
        progressBar=new ProgressBar(0);
    }

    public DownloadItem(int sNo, String name, long totalSize, DownloadState downloadState,ProgressBar progressBar) {
        this.sNo = sNo;
        this.fileName = name;
        this.totalSize = totalSize;
        this.downloadState = downloadState;
        this.progressBar = progressBar;
    }

    public int getSNo() {
        return sNo;
    }

    public void setSNo(int sNo) {
        this.sNo = sNo;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public DownloadState getDownloadState() {
        return downloadState;
    }

    public void setDownloadState(DownloadState downloadState) {
        this.downloadState = downloadState;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getDownloadSpeed() {
        return downloadSpeed;
    }

    public void setDownloadSpeed(String downloadSpeed) {
        this.downloadSpeed = downloadSpeed;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(double progress) {
        progressBar.setProgress(progress);
    }

    public String getTotalSizeStr() {
        return totalSizeStr;
    }

    public void setTotalSizeStr(String totalSizeStr) {
        this.totalSizeStr = totalSizeStr;
    }

    @Override
    public String toString() {
        return "DownloadItem{" +
                "sNo=" + sNo +
                ", name='" + fileName + '\'' +
                ", totalSize=" + totalSize +
                ", downloadState=" + downloadState +
                ", progressBar=" + progressBar +
                ", filePath='" + filePath + '\'' +
                ", downloadSpeed='" + downloadSpeed + '\'' +
                '}';
    }
}
