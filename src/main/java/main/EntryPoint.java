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
            task.setNetworkClient(task.getNetworkClient().getNetworkUrl());
        }

        MainWindow mainWindow = MainWindow.getInstance();
        if(xmlObjectList!= null) mainWindow.setTableView(xmlObjectList.getDownloadTasks());
        mainWindow.createMainWindow(primaryStage);
    }
}
