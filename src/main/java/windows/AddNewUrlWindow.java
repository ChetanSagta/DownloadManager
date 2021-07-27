package windows;

import models.DownloadItem;
import models.DownloadState;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class AddNewUrlWindow extends Stage {

    private int count = 0;

    AddNewUrlWindow(Stage primaryStage){
        var addUrlStage = new Stage();
        var textMessage = new Text("ADD URL: ");
        var textField = new TextField();

        var okBtn = new Button("OK");
        okBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            var item = new DownloadItem(count++, "name", 10.0, DownloadState.PLAY, new ProgressBar(0.1*count));
            MainWindow mainWindow = MainWindow.getInstance();
            mainWindow.addDownloadItemToTheList(item);
        });

        var cancelBtn = new Button("Cancel");
        cancelBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> addUrlStage.close());

        var hBox = new HBox(textMessage, textField, okBtn, cancelBtn);
        hBox.setSpacing(5);
        hBox.setPadding(new Insets(5, 0, 10, 10));

        var scene = new Scene(hBox, 375, 40);


        double centerXPosition = primaryStage.getX() + primaryStage.getWidth()/2d;
        double centerYPosition = primaryStage.getY() + primaryStage.getHeight()/2d;

        // Hide the pop-up stage before it is shown and becomes relocated
        addUrlStage.setOnShowing(ev -> addUrlStage.hide());

        // Relocate the pop-up Stage
        addUrlStage.setOnShown(ev -> {
            addUrlStage.setX(centerXPosition - addUrlStage.getWidth()/2d);
            addUrlStage.setY(centerYPosition - addUrlStage.getHeight()/2d);
            addUrlStage.show();
        });

        addUrlStage.initOwner(primaryStage);
        addUrlStage.setScene(scene);
        addUrlStage.initModality(Modality.APPLICATION_MODAL);
        addUrlStage.sizeToScene();
        addUrlStage.setResizable(false);
        addUrlStage.show();

    }

    private static final Logger logger = LogManager.getLogger(AddNewUrlWindow.class.getSimpleName());


}
