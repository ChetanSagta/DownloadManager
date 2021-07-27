package models;

import javafx.scene.control.ProgressBar;

public class DownloadItem{

    private int sNo;
    private String name;
    private double totalSize;
    private DownloadState downloadState;
    private ProgressBar progressBar;
    private String filePath;
    private String downloadSpeed;

    public DownloadItem(){
        sNo=0;
        name="";
        totalSize=0;
        downloadState = DownloadState.STOP;
        filePath="";
        downloadSpeed="0";
        progressBar=new ProgressBar(0);
    }

    public DownloadItem(int sNo, String name, double totalSize, DownloadState downloadState,ProgressBar progressBar) {
        this.sNo = sNo;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(double totalSize) {
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

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }
}
