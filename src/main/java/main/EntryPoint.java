package main;

import windows.MainWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class EntryPoint extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){

        MainWindow mainWindow = MainWindow.getInstance();
        mainWindow.createMainWindow(primaryStage);
    }
}
