package windows;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

public class SettingsWindow {

    public SettingsWindow(Stage primaryStage){

        var settingsStage = new Stage();

        SplitPane splitPane = new SplitPane();

        Button generalButtons = new Button("General");
        generalButtons.setStyle("-fx-background-radius: 0");
        generalButtons.setPrefWidth(Double.MAX_VALUE);
        Button notificationButtons = new Button("Notification");
        notificationButtons.setStyle("-fx-background-radius: 0");
        notificationButtons.setPrefWidth(Double.MAX_VALUE);

        VBox leftControl  = new VBox(generalButtons, notificationButtons);

        VBox rightControl = new VBox(new Label("Right Control"));

        splitPane.getItems().addAll(leftControl, rightControl);
        splitPane.setDividerPositions(0.3);

        var scene = new Scene(splitPane,300,100);

        double centerXPosition = primaryStage.getX() + primaryStage.getWidth() / 2d;
        double centerYPosition = primaryStage.getY() + primaryStage.getHeight() / 2d;

        // Hide the pop-up stage before it is shown and becomes relocated
        settingsStage.setOnShowing(ev -> settingsStage.hide());

        // Relocate the pop-up Stage
        settingsStage.setOnShown(ev -> {
            settingsStage.setX(centerXPosition - settingsStage.getWidth() / 2d);
            settingsStage.setY(centerYPosition - settingsStage.getHeight() / 2d);
            settingsStage.show();
        });

        settingsStage.initOwner(primaryStage);
        settingsStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Icons/downloadIcon.jpg"))));
        settingsStage.setScene(scene);
        settingsStage.initModality(Modality.APPLICATION_MODAL);
        settingsStage.sizeToScene();
        settingsStage.setResizable(false);
        settingsStage.show();


    }


}
