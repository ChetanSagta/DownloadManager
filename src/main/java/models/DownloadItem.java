package models;

import javafx.scene.control.ProgressBar;

public class DownloadItem{

    private int sNo;
    private long totalSize;
    private String totalSizeStr;
    private DownloadState downloadState;
    private final ProgressBar progressBar;
    private String percentage;

    public DownloadItem(){
        sNo=0;
        totalSize=0;
        totalSizeStr="";
        downloadState = DownloadState.STOP;
        progressBar=new ProgressBar(0);
    }

    public DownloadItem(int sNo, long totalSize, DownloadState downloadState,ProgressBar progressBar) {
        this.sNo = sNo;
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
                "sNo=" + sNo +
                ", totalSize=" + totalSize +
                ", downloadState=" + downloadState +
                ", progressBar=" + progressBar +
                '}';
    }
}
