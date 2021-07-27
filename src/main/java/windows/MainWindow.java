package windows;

import models.DownloadItem;
import models.DownloadState;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class MainWindow {

    private MainWindow(){
        tableView = new TableView<>();
    }

    public void createMainWindow(Stage primaryStage) {
        primaryStage.setTitle("Download Manager");
        primaryStage.setX(500);
        primaryStage.setY(150);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/Icons/downloadIcon.jpg")));


        Button addButton = new Button("Add");
        addButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            new AddNewUrlWindow(primaryStage);
        });
        Button deleteButton = new Button("Delete");
        deleteButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

        });
        Button playPauseButton = new Button("Play/Pause");
        playPauseButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (selectedItem.getDownloadState() == DownloadState.PAUSE || selectedItem.getDownloadState() == DownloadState.STOP)
                selectedItem.setDownloadState(DownloadState.PLAY);
            else if (selectedItem.getDownloadState() == DownloadState.PLAY)
                selectedItem.setDownloadState(DownloadState.PAUSE);

        });
        Button stop = new Button("Stop");
        stop.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            selectedItem.setDownloadState(DownloadState.STOP);
        });
        Button settings = new Button("Settings");
        settings.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            logger.info("Settings Button Pressed");
        });

        HBox optionButtons = new HBox(addButton, deleteButton, playPauseButton, stop, settings);
        optionButtons.setSpacing(10);
        optionButtons.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        optionButtons.setPadding(new Insets(10, 0, 10, 100));
        optionButtons.fillHeightProperty();

        BorderPane borderPane = new BorderPane();

        tableView = new TableView<>();
        TableColumn<DownloadItem, Integer> sNoCol = new TableColumn<>("S.No.");
        sNoCol.setCellValueFactory(new PropertyValueFactory<>("sNo"));

        TableColumn<DownloadItem, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<DownloadItem, Double> sizeCol = new TableColumn<>("Size");
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("totalSize"));

        TableColumn<DownloadItem, ProgressBar> progressCol = new TableColumn<>("Progress");
        progressCol.setCellValueFactory(new PropertyValueFactory<>("progressBar"));

        tableView.getColumns().addAll(sNoCol, nameCol, sizeCol, progressCol);

        tableView.getItems().add(new DownloadItem(1, "chetan", 10.0, DownloadState.PLAY, new ProgressBar(0.50)));

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        borderPane.setCenter(tableView);

        VBox vBox = new VBox(optionButtons, borderPane);
        vBox.setCursor(Cursor.HAND);
        vBox.setFillWidth(true);

        Scene primaryScene = new Scene(vBox, 500, 500);
        primaryStage.setScene(primaryScene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public void addDownloadItemToTheList(DownloadItem item) {
        tableView.getItems().add(item);
    }

    public static MainWindow getInstance() {
        if (mainWindowInstance == null) {
            mainWindowInstance = new MainWindow();
        }
        return mainWindowInstance;
    }


    private static MainWindow mainWindowInstance = null;

    private TableView<DownloadItem> tableView;
    private DownloadItem selectedItem;

    private static final Logger logger = LogManager.getLogger(MainWindow.class.getSimpleName());
}
