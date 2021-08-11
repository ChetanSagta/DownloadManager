package models;

import javafx.scene.control.ProgressBar;

public class DownloadItem{

    private long totalSize;
    private String totalSizeStr;
    private DownloadState downloadState;
    private final ProgressBar progressBar;
    private String percentage;

    public DownloadItem(){
        totalSize=0;
        totalSizeStr="";
        downloadState = DownloadState.STOP;
        progressBar=new ProgressBar(0);
    }

    public DownloadItem(long totalSize, DownloadState downloadState,ProgressBar progressBar) {
        this.totalSize = totalSize;
        this.downloadState = downloadState;
        this.progressBar = progressBar;
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

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "DownloadItem{" +
                ", totalSize=" + totalSize +
                ", downloadState=" + downloadState +
                ", progressBar=" + progressBar +
                '}';
    }
}
