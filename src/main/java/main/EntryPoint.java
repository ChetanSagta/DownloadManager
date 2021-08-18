package main;

import javafx.application.Application;
import javafx.stage.Stage;
import models.DownloadTask;
import models.XmlDownloadTask;
import preference.SystemPreference;
import windows.MainWindow;

public class EntryPoint extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){

        SystemPreference systemPreference = new SystemPreference();
        XmlDownloadTask xmlObjectList = systemPreference.getApplicationState();

        for(DownloadTask task : xmlObjectList.getDownloadTasks()){
            String percentage = task.getItem().getPercentage();
            task.getItem().setProgressBar(Double.parseDouble(percentage.substring(0,percentage.length()-1)));
            task.setNetworkClient(task.getNetworkClient().getNetworkUrl());
        }

        MainWindow mainWindow = MainWindow.getInstance();

        mainWindow.setTableView(xmlObjectList.getDownloadTasks());
        mainWindow.createMainWindow(primaryStage);
    }
}
