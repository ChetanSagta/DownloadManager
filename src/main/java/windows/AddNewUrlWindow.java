package windows;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.DownloadTask;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;


public class AddNewUrlWindow extends Stage {

    private TextField filePathTextField = null;
    private TextField fileNameTextField = null;

    AddNewUrlWindow(Stage primaryStage) {
        var addUrlStage = new Stage();
        var textMessage = new Text("ADD URL: ");
        DownloadTask downloadTask = new DownloadTask();

        var textField = new TextField();
        textField.textProperty().addListener((observable, oldValue,newValue) -> {
                new Thread(()-> {
                    if(textField.getText().length() > 0) {
                        downloadTask.setNetworkClient(textField.getText());
                        String fileName = downloadTask.getNetworkClient().getRemoteFileName();
                        fileNameTextField.setText(fileName);
                        downloadTask.getNetworkClient().setNetworkUrl(textField.getText());
                        downloadTask.getItem().setName(fileName);
                        downloadTask.getItem().setProgressBar(0);
                        downloadTask.getItem().setTotalSizeStr(downloadTask.getNetworkClient().getFileSizeStr());
                        downloadTask.getItem().setTotalSize(downloadTask.getNetworkClient().getRemoteFileSize());
                    }
                }).start();
        });

        var okBtn = new Button("OK");
        okBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            logger.info("FilePathTextField : " + fileNameTextField.getText());
            if(filePathTextField.getLength() > 0 ){
                downloadTask.getItem().setFilePath(filePathTextField.getText());
                MainWindow mainWindow = MainWindow.getInstance();
                downloadTask.getItem().setSNo(mainWindow.tableViewLength()+1);
                mainWindow.addDownloadItemToTheList(downloadTask);
                addUrlStage.close();
            }
        });

        var cancelBtn = new Button("Cancel");
        cancelBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> addUrlStage.close());

        var hBox = new HBox(textMessage, textField, okBtn, cancelBtn);
        hBox.setSpacing(5);
        hBox.setPadding(new Insets(5, 0, 10, 10));

        filePathTextField = new TextField();
        filePathTextField.setPrefWidth(230);

        var browseBtn = new Button("Browse");
        browseBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedFile = directoryChooser.showDialog(addUrlStage);
            filePathTextField.setText(selectedFile.getAbsolutePath());
        });

        var fileSelectHBox = new HBox(browseBtn, filePathTextField);
        fileSelectHBox.setSpacing(10);
        fileSelectHBox.setPadding(new Insets(5, 0, 10, 10));

        var fileNameText = new Text("FileName : ");
        fileNameTextField = new TextField();

        var fileInfoBox = new HBox(fileNameText, fileNameTextField);
        fileInfoBox.setSpacing(10);
        fileInfoBox.setPadding(new Insets(5, 0, 10, 10));

        var vBox = new VBox(hBox, fileInfoBox, fileSelectHBox);

        var scene = new Scene(vBox);

        double centerXPosition = primaryStage.getX() + primaryStage.getWidth() / 2d;
        double centerYPosition = primaryStage.getY() + primaryStage.getHeight() / 2d;

        // Hide the pop-up stage before it is shown and becomes relocated
        addUrlStage.setOnShowing(ev -> addUrlStage.hide());

        // Relocate the pop-up Stage
        addUrlStage.setOnShown(ev -> {
            addUrlStage.setX(centerXPosition - addUrlStage.getWidth() / 2d);
            addUrlStage.setY(centerYPosition - addUrlStage.getHeight() / 2d);
            addUrlStage.show();
        });

        addUrlStage.initOwner(primaryStage);
        addUrlStage.getIcons().add(new Image(getClass().getResourceAsStream("/Icons/downloadIcon.jpg")));
        addUrlStage.setScene(scene);
        addUrlStage.initModality(Modality.APPLICATION_MODAL);
        addUrlStage.sizeToScene();
        addUrlStage.setResizable(false);
        addUrlStage.show();

    }

    private static final Logger logger = LogManager.getLogger(AddNewUrlWindow.class.getSimpleName());


}
