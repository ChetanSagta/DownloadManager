package windows;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import preference.SystemPreference;
import utilities.ThreadPool;

import java.util.List;
import java.util.Objects;

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
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Icons/downloadIcon.jpg"))));

        Button addButton = new Button("Add");
        addButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> new AddNewUrlWindow(primaryStage));
        Button deleteButton = new Button("Delete");
        deleteButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            selectedItem.getItem().setDownloadState(DownloadState.STOP);
            tableView.getItems().remove(selectedItem);
        });

        Button playButton = new Button("Play");
        playButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if ((selectedItem.getItem().getDownloadState() == DownloadState.STOP) && (selectedItem.getItem().getDownloadState() != DownloadState.COMPLETED)) {
                selectedItem.getItem().setDownloadState(DownloadState.PLAY);
                ThreadPool.getInstance().executeTask(selectedItem);
            }
            tableView.refresh();
        });

        Button stop = new Button("Stop");
        stop.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(selectedItem.getItem().getDownloadState() == DownloadState.COMPLETED)
                return ;
            selectedItem.getItem().setDownloadState(DownloadState.STOP);
            tableView.refresh();
        });

        Button settings = new Button("Settings");
        settings.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            new SettingsWindow(primaryStage);
        });

        HBox optionButtons = new HBox(addButton, deleteButton, playButton, stop, settings);
        optionButtons.setSpacing(20);
        optionButtons.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        optionButtons.setPadding(new Insets(10, 0, 10, 0));
        optionButtons.setAlignment(Pos.CENTER);
        optionButtons.fillHeightProperty();

        tableView.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            selectedItem = tableView.getSelectionModel().getSelectedItem();
            if(event.getClickCount() == 2){
                selectedItem.getFileSystemClient().navigateToFilePath();
            }
        });

        TableColumn<DownloadTask, Integer> sNoCol = new TableColumn<>("S.No.");

        sNoCol.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(tableView.getItems().indexOf(column.getValue())+1));

        TableColumn<DownloadTask, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFileSystemClient().getFileName()));

//        TableColumn<DownloadTask, Long> sizeCol = new TableColumn<>("Size");
//        sizeCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getItem().getTotalSize()));

        TableColumn<DownloadTask, String> sizeStrCol = new TableColumn<>("Size String");
        sizeStrCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNetworkClient().getFileSizeStr()));

        TableColumn<DownloadTask, String> downloadSpeed = new TableColumn<>("Speed");
        downloadSpeed.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNetworkClient().getDownloadSpeed()));

        TableColumn<DownloadTask, String> fileLocation = new TableColumn<>("Location");
        fileLocation.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFileSystemClient().getFilePath()));

        TableColumn<DownloadTask, ProgressBar> progressCol = new TableColumn<>("Progress");
        progressCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getItem().getProgressBar()));

        TableColumn<DownloadTask, String> progressPercentage = new TableColumn<>("Progress");
        progressPercentage.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getItem().getPercentage()));

        TableColumn<DownloadTask, DownloadState> downloadState = new TableColumn<>("DownloadState");
        downloadState.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getItem().getDownloadState()));

        tableView.getColumns().addAll(sNoCol, nameCol, sizeStrCol, downloadSpeed, fileLocation, progressCol,progressPercentage,downloadState);
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        VBox vBox = new VBox(optionButtons, tableView);
        vBox.setCursor(Cursor.HAND);
        vBox.setFillWidth(true);

        Scene primaryScene = new Scene(vBox,740,450);
        primaryStage.setScene(primaryScene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            SystemPreference systemPreference = new SystemPreference();
            systemPreference.saveApplicationState();
            Platform.exit();
            System.exit(0);
        });

    }

    public TableView<DownloadTask> getTableView(){
        return tableView;
    }

    public void setTableView(List<DownloadTask> downloadTasksList){
        tableView.getItems().addAll(downloadTasksList);
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