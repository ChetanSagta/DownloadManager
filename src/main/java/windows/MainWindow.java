package windows;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.DownloadState;
import models.DownloadTask;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import utilities.ThreadPool;

public class MainWindow {

    private static MainWindow mainWindowInstance = null;

    private TableView<DownloadTask> tableView;
    private DownloadTask selectedItem;

    private MainWindow() {
        tableView = new TableView<>();
    }

    public void createMainWindow(Stage primaryStage) {
        primaryStage.setTitle("Download Manager");
        primaryStage.setX(500);
        primaryStage.setY(150);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/Icons/downloadIcon.jpg")));


        Button addButton = new Button("Add");
        addButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> new AddNewUrlWindow(primaryStage));
        Button deleteButton = new Button("Delete");
        deleteButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //Todo: Kill the task assosiated as well, the download keeps on working
            tableView.getItems().remove(selectedItem);
        });

        Button playPauseButton = new Button("Play/Pause");
        playPauseButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if ((selectedItem.getItem().getDownloadState() == DownloadState.PAUSE) || (selectedItem.getItem().getDownloadState() == DownloadState.STOP)) {
                selectedItem.getItem().setDownloadState(DownloadState.PLAY);
                ThreadPool.getInstance().executeTask(selectedItem);
            } else if (selectedItem.getItem().getDownloadState() == DownloadState.PLAY)
                selectedItem.getItem().setDownloadState(DownloadState.PAUSE);

            tableView.refresh();
        });

        Button stop = new Button("Stop");
        stop.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            selectedItem.getItem().setDownloadState(DownloadState.STOP);
            tableView.refresh();
        });

        Button settings = new Button("Settings");
        settings.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            logger.info("Settings Button Pressed");
        });

        HBox optionButtons = new HBox(addButton, deleteButton, playPauseButton, stop, settings);
        optionButtons.setSpacing(10);
        optionButtons.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        optionButtons.setPadding(new Insets(10, 0, 10, 170));
        optionButtons.fillHeightProperty();

        tableView = new TableView<>();
        tableView.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            selectedItem = tableView.getSelectionModel().getSelectedItem();
        });

        TableColumn<DownloadTask, Integer> sNoCol = new TableColumn<>("S.No.");
        sNoCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getItem().getSNo()));

        TableColumn<DownloadTask, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFileSystemClient().getFileName()));

        TableColumn<DownloadTask, Long> sizeCol = new TableColumn<>("Size");
        sizeCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getItem().getTotalSize()));

        TableColumn<DownloadTask, String> sizeStrCol = new TableColumn<>("Size String");
        sizeStrCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getItem().getTotalSizeStr()));

        TableColumn<DownloadTask, String> downloadSpeed = new TableColumn<>("Download Speed");
        downloadSpeed.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getItem().getDownloadSpeed()));

        TableColumn<DownloadTask, String> fileLocation = new TableColumn<>("Location");
        fileLocation.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFileSystemClient().getFilePath()));

        TableColumn<DownloadTask, ProgressBar> progressCol = new TableColumn<>("Progress");
        progressCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getItem().getProgressBar()));

        TableColumn<DownloadTask, DownloadState> downloadState = new TableColumn<>("DownloadState");
        downloadState.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getItem().getDownloadState()));

        tableView.getColumns().addAll(sNoCol, nameCol, sizeCol, sizeStrCol, downloadSpeed, fileLocation, progressCol, downloadState);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox vBox = new VBox(optionButtons, tableView);
        vBox.setCursor(Cursor.HAND);
        vBox.setFillWidth(true);

        Scene primaryScene = new Scene(vBox);
        primaryStage.setScene(primaryScene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
                    Platform.exit();
                    System.exit(0);
        });

    }

    public void addDownloadItemToTheList(DownloadTask item) {
        tableView.getItems().add(item);
    }

    public static MainWindow getInstance() {
        if (mainWindowInstance == null) {
            mainWindowInstance = new MainWindow();
        }
        return mainWindowInstance;
    }

    public int tableViewLength() {
        return tableView.getItems().size();
    }

    public void updateTableView() {
        tableView.refresh();
    }

    private static final Logger logger = LogManager.getLogger(MainWindow.class.getSimpleName());
}
