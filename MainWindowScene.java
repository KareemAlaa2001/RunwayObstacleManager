
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXBException;

public class MainWindowScene extends WindowScene {

    private Airport ap;
    private Map<String, RunwayCanvas> runways;
    private List<String> runwayNames;
    private ComboBox<String> runwaySelectionBox;
    private ComboBox<String> obstacleSelectionBox;
    private double currentScroll;
    private double mouseDX, mouseDY;
    private RunwayCanvas currentCanvas;
    private double currentXOffset, currentYOffset;
    private TextArea outputTextArea, activityTextArea;

    public MainWindowScene(Airport airp) {

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        gridPane.setPrefSize(getAppStage().getWidth(), getAppStage().getWidth());
        gridPane.setAlignment(Pos.TOP_LEFT);

        obstacleSelectionBox = new ComboBox<>();

        Pane emptyPane = new Pane();
        emptyPane.setPrefSize(1100, 900);

        CheckBox rotateSelect = new CheckBox("Rotate Runway");
        CheckBox hideObsSelect = new CheckBox("Hide obstacles");

        RunwayCanvas defaultCanvas = new RunwayCanvas(1100, 900);
        emptyPane.getChildren().add(defaultCanvas);
        defaultCanvas.render(1.0, 0, 0, false, false);
        currentCanvas = defaultCanvas;
        emptyPane.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent t) {
                if (t.getY() <= emptyPane.getHeight() / 2) {
                    double canvasX = ((currentXOffset + t.getX()) / (1.0 + currentScroll * 0.001));
                    double canvasY = ((currentYOffset + t.getY()) / (1.0 + currentScroll * 0.001));
                    double prevScroll = currentScroll;
                    currentScroll = Math.max(0.0, currentScroll + t.getDeltaY());
                    double delta = currentScroll - prevScroll;
                    currentXOffset += canvasX * delta * 0.001;
                    currentXOffset = Math.max(0.0, Math.min(emptyPane.getWidth() * (1.0 + currentScroll * 0.001) - emptyPane.getWidth(), currentXOffset));
                    currentYOffset += canvasY * delta * 0.001;
                    currentYOffset = Math.max(0.0, Math.min(emptyPane.getWidth() * (1.0 + currentScroll * 0.001) - emptyPane.getHeight() * 0.7, currentYOffset));
                    updateCanvas(emptyPane, currentCanvas, currentScroll, currentXOffset, currentYOffset, rotateSelect.isSelected(), hideObsSelect.isSelected());
                }
            }
        });
        emptyPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                currentXOffset -= t.getX() - mouseDX;
                currentYOffset -= t.getY() - mouseDY;
                currentXOffset = Math.max(0.0, Math.min(emptyPane.getWidth() * (1.0 + currentScroll * 0.001) - emptyPane.getWidth(), currentXOffset));
                currentYOffset = Math.max(0.0, Math.min(emptyPane.getWidth() * (1.0 + currentScroll * 0.001) - emptyPane.getHeight() * 0.7, currentYOffset));
                mouseDX = t.getX();
                mouseDY = t.getY();
                updateCanvas(emptyPane, currentCanvas, currentScroll, currentXOffset, currentYOffset, rotateSelect.isSelected(), hideObsSelect.isSelected());
            }
        });
        emptyPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                mouseDX = t.getX();
                mouseDY = t.getY();
            }
        });

        runwaySelectionBox = new ComboBox();

        runwaySelectionBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldVal, String newVal) {
                currentScroll = 0.0;
                currentXOffset = 0.0;
                currentYOffset = 235.0;
                currentCanvas = runways.get(newVal);
                populateObstacles(newVal);
                updateCanvas(emptyPane, runways.get(newVal), currentScroll, currentXOffset, currentYOffset, rotateSelect.isSelected(), hideObsSelect.isSelected());
            }
        });

        ComboBox presetSelectionBox = new ComboBox();
        presetSelectionBox.getItems().add("Custom");
        presetSelectionBox.getItems().add("Boeing 747 on right");
        presetSelectionBox.getItems().add("Luggage truck on left");
        presetSelectionBox.setValue("Custom");

        Button presetSetButton = new Button("Set");
        presetSetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                switch (presetSelectionBox.getValue() + "") {
                    case "Custom":
                        break;
                    case "Boeing 747 on right":
                        ap.clearRunway(runwaySelectionBox.getValue());
                        ap.addObstacle(19, 
                                (int) ((ap.getRunwayFull(runwaySelectionBox.getValue()).getRunL().getRunwaySpec().TORA - ap.getRunwayFull(runwaySelectionBox.getValue()).getRunL().getRunwaySpec().takeoffThreshold) * 0.9),
                                0, runwaySelectionBox.getValue(), true);
                        break;
                    case "Luggage truck on left":
                        ap.clearRunway(runwaySelectionBox.getValue());
                        ap.addObstacle(2, 
                                (int) ((ap.getRunwayFull(runwaySelectionBox.getValue()).getRunL().getRunwaySpec().TORA - ap.getRunwayFull(runwaySelectionBox.getValue()).getRunL().getRunwaySpec().takeoffThreshold) * 0.2),
                                0, runwaySelectionBox.getValue(), true);
                        break;
                }
                updateCanvas(emptyPane, currentCanvas, currentScroll, currentXOffset, currentYOffset, rotateSelect.isSelected(), hideObsSelect.isSelected());
            }
        });

        rotateSelect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                updateCanvas(emptyPane, currentCanvas, currentScroll, currentXOffset, currentYOffset, rotateSelect.isSelected(), hideObsSelect.isSelected());
            }
        });

        TextField obstacleHeightInput = initNumTextField("Enter obstacle height");
        TextField obstacleLocationInput = initNumTextField("Enter obstacle distance from start of runway");
        TextField obsCentrelineInput = initNumTextField("Enter obstacle distance from centreline");

        Text addObstacle = new Text("Add a new obstacle: ");
        addObstacle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        Label obsHeightLabel = new Label("Obstacle height: ");
        Label obsDistLabel = new Label("Distance along runway: ");
        Label obsCentrelineLabel = new Label("Distance from centreline:");

        Button addObstacleButton = new Button("Add Obstacle");
        addObstacleButton.setOnAction(e -> {
            if (runwaySelectionBox.getValue().isEmpty()) {
                generateAlert("Unable to add an obstacle", "You must first select a runway");
            } else if (obstacleHeightInput.getText().isEmpty() || obstacleLocationInput.getText().isEmpty() || obsCentrelineInput.getText().isEmpty()) {
                generateAlert("Unable to add an obstacle", "You must first fill all 3 obstacle information fields");
            } else {
                try {
                    ap.addObstacle(Integer.parseInt(obstacleHeightInput.getText()),
                            Integer.parseInt(obstacleLocationInput.getText()),
                            Integer.parseInt(obsCentrelineInput.getText()),
                            (String) (runwaySelectionBox.getValue()),
                            true);

                    obstacleSelectionBox.getItems().add("Max Height: " + obstacleHeightInput.getText());

                    obstacleHeightInput.clear();
                    obstacleLocationInput.clear();
                    obsCentrelineInput.clear();

                    updateCanvas(emptyPane, currentCanvas, currentScroll, currentXOffset, currentYOffset, rotateSelect.isSelected(), hideObsSelect.isSelected());
                } catch (Exception ex) {
                    Logger.getLogger(MainWindowScene.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Button rmObstacles = new Button("Remove obstacle");
        rmObstacles.setOnAction(e -> {
            if (runwaySelectionBox.getValue().isEmpty()) {
                generateAlert("Unable to remove obstacle", "You must first select a runway");
            } else if (obstacleSelectionBox.getValue() == null || obstacleSelectionBox.getValue().isEmpty()) {
                generateAlert("Unable to remove obstacle", "Yout must first select an obstacle to remove");
            } else {
                String runName = runwaySelectionBox.getValue();
                String obsName = obstacleSelectionBox.getValue();
                ap.removeObstacle(Airport.findObstacleByName(obsName, ap.getObstacles()));
                obstacleSelectionBox.getItems().remove(obsName);
                updateCanvas(emptyPane, currentCanvas, currentScroll, currentXOffset, currentYOffset, rotateSelect.isSelected(), hideObsSelect.isSelected());
            }
        });

        Button clearRunway = new Button("Clear runway");
        clearRunway.setOnAction(e -> {
            if (runwaySelectionBox.getValue() == null || runwaySelectionBox.getValue().isEmpty()) {
                generateAlert("Unable to clear runway", "You must first select a runway");
            } else {
                String runName = runwaySelectionBox.getValue();
                ap.clearRunway(runName);
                obstacleSelectionBox.getItems().clear();
                updateCanvas(emptyPane, currentCanvas, currentScroll, currentXOffset, currentYOffset, rotateSelect.isSelected(), hideObsSelect.isSelected());
            }
        });

        Button exportButton = new Button("Export airport configuration");
        exportButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Airport");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                    "XML files (*.xml)", "*.xml");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showSaveDialog(getAppStage());
            if (file != null) {
                try {
                    XMLLoader.exportAirport(this.ap, file.getPath());
                } catch (JAXBException ex) {
                    generateAlert("Unable to export airport", "There was a problem with exporting the airport to XML");
                }
            }
        });

        TabPane tabPane = new TabPane();
        Tab outputTab = new Tab("Output");
        outputTab.setClosable(false);
        outputTextArea = new TextArea();
        outputTextArea.setPrefWidth(800);
        outputTextArea.setWrapText(true);
        outputTextArea.setEditable(false);

        ScrollPane outputScrollPane = new ScrollPane();
        outputScrollPane.setContent(outputTextArea);
        outputScrollPane.setFitToWidth(true);
        outputScrollPane.setPrefWidth(800);
        outputScrollPane.setPrefHeight(700);
        outputTab.setContent(outputScrollPane);

        Tab activityTab = new Tab("Activity");
        activityTab.setClosable(false);
        activityTextArea = new TextArea();
        activityTextArea.setPrefWidth(800);
        activityTextArea.setWrapText(true);
        activityTextArea.setEditable(false);

        ScrollPane activityScrollPane = new ScrollPane();
        activityScrollPane.setContent(activityTextArea);
        activityScrollPane.setFitToWidth(true);
        activityScrollPane.setPrefWidth(800);
        activityScrollPane.setPrefHeight(700);
        activityTab.setContent(activityScrollPane);

        Button toAirportScene = new Button("To airport scene");
        toAirportScene.setOnAction(e -> MainWindowController.goToCreateScreen());

        Button toRunwayScene = new Button("To runway scene");
        toRunwayScene.setOnAction(e -> MainWindowController.goToRunwayScreen(ap));

        Label rmObsLabel = new Label("Remove an obstacle: ");

        hideObsSelect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                updateCanvas(emptyPane, currentCanvas, currentScroll, currentXOffset, currentYOffset, rotateSelect.isSelected(), hideObsSelect.isSelected());
            }
        });

        tabPane.getTabs().add(outputTab);
        tabPane.getTabs().add(activityTab);

        gridPane.add(emptyPane, 0, 0, 1, 15);
        gridPane.add(new Label("Select Runway:"), 1, 0);
        gridPane.add(runwaySelectionBox, 2, 0);
        gridPane.add(rotateSelect, 1, 1, 2, 1);
        gridPane.add(obstacleHeightInput, 1, 4, 1, 1);
        gridPane.add(obstacleLocationInput, 2, 4, 1, 1);
        gridPane.add(obsCentrelineInput, 3, 4);
        gridPane.add(addObstacleButton, 1, 5, 2, 1);
        gridPane.add(tabPane, 1, 8, 5, 5);
        gridPane.add(toRunwayScene, 2, 12);
        gridPane.add(toAirportScene, 3, 12);
        gridPane.add(addObstacle, 1, 2, 2, 1);
        gridPane.add(presetSelectionBox, 2, 2, 2, 1);
        gridPane.add(presetSetButton, 3, 2, 2, 1);
        gridPane.add(obsHeightLabel, 1, 3, 1, 1);
        gridPane.add(obsDistLabel, 2, 3, 1, 1);
        gridPane.add(obsCentrelineLabel, 3, 3);
        gridPane.add(rmObstacles, 4, 5, 1, 1);
        gridPane.add(rmObsLabel, 2, 5);
        gridPane.add(obstacleSelectionBox, 3, 5);
        gridPane.add(hideObsSelect, 1, 6);
        gridPane.add(clearRunway, 1, 7);
        gridPane.add(exportButton, 1, 13);
        scene = new Scene(gridPane);

        this.setAirport(airp);

    }

    public void updateCanvas(Pane pane, RunwayCanvas canvas, double scale, double tx, double ty, boolean rotate, boolean hideObs) {
        pane.getChildren().clear();
        pane.getChildren().add(canvas);
        canvas.render((1.0 + scale * 0.001), tx, ty, rotate, hideObs);
    }

    public void addOutputText(String string) {
        if (outputTextArea.getText().equals("")) {
            outputTextArea.setText(string);
        } else {
            outputTextArea.appendText("\n" + string);
        }
    }

    public void addActivityText(String string) {
        if (activityTextArea.getText().equals("")) {
            activityTextArea.setText(string);
        } else {
            activityTextArea.appendText("\n" + string);
        }
    }

    public void clearOutput() {
        outputTextArea.clear();
    }

    public void clearActivity() {
        activityTextArea.clear();
    }

    public void setAirport(Airport ap) {
        this.ap = ap;

        runways = new HashMap();
        runwayNames = new ArrayList();
        for (Runway runway : ap.getRunways()) {
            runwayNames.add(runway.getName());
            RunwayCanvas runwayCanvas = new RunwayCanvas(1100, 900, runway, ap.getRunwayPos(runway.getName()));
            runways.put(runway.getName(), runwayCanvas);
        }

        runwaySelectionBox.getItems().clear();
        runwaySelectionBox.getItems().addAll(runwayNames);
    }

    private void populateObstacles(String runName) {
        obstacleSelectionBox.getItems().clear();
        List<ObstacleData> runObs = ap.getRunwayObstacles(Airport.findRunwayByName(runName, ap.getRunways()));
        runObs.forEach(obs -> {
            obstacleSelectionBox.getItems().add(obs.getName());
        });
    }

    private Stage getAppStage() {
        return MainWindowController.getAppStage();
    }

}
