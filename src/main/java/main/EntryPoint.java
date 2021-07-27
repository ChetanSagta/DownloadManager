package main;

import windows.MainWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class EntryPoint extends Application {

    @Override
    public void start(Stage primaryStage){

        MainWindow mainWindow = MainWindow.getInstance();
        mainWindow.createMainWindow(primaryStage);
    }
}
