
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
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Window {

    private Airport ap;
    private Map<String, RunwayCanvas> runways;
    private double currentScroll;
    private double mouseDX, mouseDY;
    private RunwayCanvas currentCanvas;
    private double currentXOffset, currentYOffset;
    private TextArea outputTextArea, activityTextArea;

    public Window(Airport ap) {
        this.ap = ap;
    }

    public Stage getStage(Stage stage) throws Exception {

        stage.setResizable(false);
        stage.setMaximized(true);

        GridPane gridMainScene = new GridPane();
        gridMainScene.setAlignment(Pos.CENTER);
        gridMainScene.setHgap(10);
        gridMainScene.setVgap(10);
        gridMainScene.setPadding(new Insets(25, 25, 25, 25));
        gridMainScene.setPrefSize(stage.getWidth(), stage.getHeight());
        gridMainScene.setAlignment(Pos.TOP_LEFT);
        stage.setTitle("Airport Display");
        Pane emptyPane = new Pane();
        emptyPane.setPrefSize(1100, 900);

        runways = new HashMap();
        List<String> runwayNames = new ArrayList();
        for (Runway runway : ap.getRunways()) {
            runwayNames.add(runway.getName());
            RunwayCanvas runwayCanvas = new RunwayCanvas(1100, 900, runway);
            runways.put(runway.getName(), runwayCanvas);
        }

        CheckBox rotateSelect = new CheckBox("Rotate Runway");

        RunwayCanvas defaultCanvas = new RunwayCanvas(1100, 900);
        emptyPane.getChildren().add(defaultCanvas);
        defaultCanvas.render(1.0, 0, 0, false);
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
                    updateCanvas(emptyPane, currentCanvas, currentScroll, currentXOffset, currentYOffset, rotateSelect.isSelected());
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
                updateCanvas(emptyPane, currentCanvas, currentScroll, currentXOffset, currentYOffset, rotateSelect.isSelected());
            }
        });
        emptyPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                mouseDX = t.getX();
                mouseDY = t.getY();
            }
        });

        ComboBox runwaySelectionBox = new ComboBox();
        runwaySelectionBox.getItems().addAll(runwayNames);

        runwaySelectionBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldVal, String newVal) {
                currentScroll = 0.0;
                currentXOffset = 0.0;
                currentYOffset = 235.0;
                currentCanvas = runways.get(newVal);
                updateCanvas(emptyPane, runways.get(newVal), currentScroll, currentXOffset, currentYOffset, rotateSelect.isSelected());
            }
        });

        rotateSelect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                updateCanvas(emptyPane, currentCanvas, currentScroll, currentXOffset, currentYOffset, rotateSelect.isSelected());
            }
        });

        TextField obstacleHeightInput = new TextField();
        TextField obstacleLocationInput = new TextField();
        Button addObstacleButton = new Button("Add Obstacle");
        addObstacleButton.setOnAction(e -> {
            if (runwaySelectionBox.getValue() != null) {
                try {
                    ap.addObstacle(new ObstacleData(Integer.parseInt(obstacleLocationInput.getText()),
                            Integer.parseInt(obstacleHeightInput.getText())), (String) (runwaySelectionBox.getValue()), true);
                    updateCanvas(emptyPane, currentCanvas, currentScroll, currentXOffset, currentYOffset, rotateSelect.isSelected());
                } catch (Exception ex) {
                    Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
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

        Button toCreate = new Button("Go to create scene");

        tabPane.getTabs().add(outputTab);
        tabPane.getTabs().add(activityTab);

        gridMainScene.add(emptyPane, 0, 0, 1, 4);
        gridMainScene.add(new Label("Select Runway:"), 1, 0);
        gridMainScene.add(runwaySelectionBox, 2, 0);
        gridMainScene.add(rotateSelect, 1, 1, 2, 1);
        gridMainScene.add(obstacleHeightInput, 1, 2, 1, 1);
        gridMainScene.add(obstacleLocationInput, 2, 2, 1, 1);
        gridMainScene.add(addObstacleButton, 1, 3, 2, 1);
        gridMainScene.add(tabPane, 1, 4, 2, 2);
        gridMainScene.add(toCreate, 2, 5);

        GridPane gridCreateScene = new GridPane();
        GridPane gridAddRunways = new GridPane();

        Scene sceneMain = new Scene(gridMainScene);
        Scene sceneLoadOrCreate = new Scene(gridCreateScene, 300, 250);
        Scene sceneAddRunways = new Scene(gridAddRunways, 300, 250);

        gridCreateScene.setStyle("-fx-background-color: rgba(172,168,247,0.58)");
        gridCreateScene.setAlignment(Pos.CENTER);
        gridCreateScene.setHgap(20);
        gridCreateScene.setVgap(20);
        gridCreateScene.setPadding(new Insets(25, 25, 25, 25));

        gridAddRunways.setStyle("-fx-background-color: rgba(172,168,247,0.58)");
        gridAddRunways.setAlignment(Pos.CENTER);
        gridAddRunways.setHgap(20);
        gridAddRunways.setVgap(20);
        gridAddRunways.setPadding(new Insets(25, 25, 25, 25));

        //all buttons
        Button chooseAirportFile = new Button("Choose file");
        Button loadAirportButton = new Button("Load Airport");
        Button createAirportButton = new Button("Create Airport");

        Button chooseRunwayFile = new Button("Choose file(s)");
        Button addRunway = new Button("Add runway to the airport");
        Button finish = new Button("Finish");
        Button back = new Button("<-- Back");

        HBox hbChooseAirport = new HBox(10);
        hbChooseAirport.getChildren().add(chooseAirportFile);

        HBox hbLoadAirport = new HBox(10);
        hbLoadAirport.getChildren().add(loadAirportButton);
        hbLoadAirport.setAlignment(Pos.CENTER);

        HBox hbCreateAirport = new HBox(10);
        hbCreateAirport.getChildren().add(createAirportButton);
        hbCreateAirport.setAlignment(Pos.CENTER);

        HBox hbChooseRunway = new HBox(10);
        hbChooseRunway.getChildren().add(chooseRunwayFile);

        HBox hbFinish = new HBox(10);
        hbFinish.getChildren().add(finish);
        hbFinish.setAlignment(Pos.CENTER_RIGHT);

        HBox hbAddRunway = new HBox(10);
        hbAddRunway.getChildren().add(addRunway);
        hbAddRunway.setAlignment(Pos.CENTER);

        gridCreateScene.add(hbChooseAirport, 2, 3);
        gridCreateScene.add(hbLoadAirport, 1, 4);
        gridCreateScene.add(hbCreateAirport, 1, 14);

        gridAddRunways.add(hbChooseRunway, 2, 1);
        gridAddRunways.add(back, 0, 10);
        gridAddRunways.add(hbAddRunway, 1, 8);
        gridAddRunways.add(hbFinish, 2, 10);

        //all text fields
        TextField selectedAirportTextField = new TextField();
        selectedAirportTextField.setPromptText("No file selected");
        gridCreateScene.add(selectedAirportTextField, 1, 3);

        TextField airportNameTextField = new TextField();
        airportNameTextField.setPromptText("Example Text");
        gridCreateScene.add(airportNameTextField, 1, 7);

        TextField resaValue = new TextField();
        resaValue.setPromptText("No value given");
        gridCreateScene.add(resaValue, 1, 8);

        TextField stripEndValue = new TextField();
        stripEndValue.setPromptText("No value given");
        gridCreateScene.add(stripEndValue, 1, 9);

        TextField blastAllowanceValue = new TextField();
        blastAllowanceValue.setPromptText("No value given");
        gridCreateScene.add(blastAllowanceValue, 1, 10);

        TextField minSlopeValue = new TextField();
        minSlopeValue.setPromptText("No value given");
        gridCreateScene.add(minSlopeValue, 1, 11);

        TextField selectedRunwayTextField = new TextField();
        selectedRunwayTextField.setPromptText("No file(s) selected");
        gridAddRunways.add(selectedRunwayTextField, 1, 1);

        TextField threshold = new TextField();
        threshold.setPromptText("No value given");
        gridAddRunways.add(threshold, 1, 4);

        TextField tora = new TextField();
        tora.setPromptText("No value given");
        gridAddRunways.add(tora, 1, 5);

        TextField stopway = new TextField();
        stopway.setPromptText("No value given");
        gridAddRunways.add(stopway, 1, 6);

        TextField clearway = new TextField();
        clearway.setPromptText("No value given");
        gridAddRunways.add(clearway, 1, 7);

        //just text
        Text welcomeText = new Text("Welcome to the runway redeclaration tool");
        welcomeText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridCreateScene.add(welcomeText, 0, 0, 2, 1);

        Text loadAirport = new Text("Load Airport");
        loadAirport.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        gridCreateScene.add(loadAirport, 0, 2);

        Text createAirport = new Text("Create Airport");
        createAirport.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        gridCreateScene.add(createAirport, 0, 6);

        Text loadRunways = new Text("Add existing runways to the airport");
        loadRunways.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridAddRunways.add(loadRunways, 0, 0, 2, 1);

        Text createRunways = new Text("Create new runways");
        createRunways.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridAddRunways.add(createRunways, 0, 3, 2, 1);

        //all labels
        Label selectedFile = new Label("Selected File:");
        gridCreateScene.add(selectedFile, 0, 3);

        Label airportName = new Label("Airport Name:");
        gridCreateScene.add(airportName, 0, 7);

        Label resa = new Label("RESA:");
        gridCreateScene.add(resa, 0, 8);

        Label stripEnd = new Label("Strip End:");
        gridCreateScene.add(stripEnd, 0, 9);

        Label blastAllowance = new Label("Blast Allowance:");
        gridCreateScene.add(blastAllowance, 0, 10);

        Label minSlope = new Label("Minimum Slope:");
        gridCreateScene.add(minSlope, 0, 11);

        Label selectedRunway = new Label("Selected Runway(s):");
        gridAddRunways.add(selectedRunway, 0, 1);

        Label thresholdLabel = new Label("Threshold:");
        gridAddRunways.add(thresholdLabel, 0, 4);

        Label toraLabel = new Label("TORA:");
        gridAddRunways.add(toraLabel, 0, 5);

        Label stopwayLabel = new Label("Stopway:");
        gridAddRunways.add(stopwayLabel, 0, 6);

        Label clearwayLabel = new Label("Clearway:");
        gridAddRunways.add(clearwayLabel, 0, 7);

        //file choosers with filters
        FileChooser xmlFileChooser = new FileChooser();
        FileChooser airportFileChooser = new FileChooser();
        FileChooser.ExtensionFilter xmlExtensionFilter = new FileChooser.ExtensionFilter(
                "XML Files (*.xml)", "*.xml");
        xmlFileChooser.getExtensionFilters().add(xmlExtensionFilter);

        chooseRunwayFile.setOnAction(e -> {
            File selectedRunwayFile = xmlFileChooser.showOpenDialog(stage);
            selectedRunwayTextField.setText(selectedRunwayFile.getName());
            selectedRunwayTextField.setStyle("-fx-background-color: yellowgreen");
        });

        chooseAirportFile.setOnAction(e -> {
            File selectedAirportFile = airportFileChooser.showOpenDialog(stage);
            selectedAirportTextField.setText(selectedAirportFile.getName());
            selectedAirportTextField.setStyle("-fx-background-color: yellowgreen");
        });

        toCreate.setOnAction(e -> stage.setScene(sceneLoadOrCreate));
        createAirportButton.setOnAction(e -> stage.setScene(sceneAddRunways));
        loadAirportButton.setOnAction(e -> stage.setScene(sceneMain));
        back.setOnAction(e -> stage.setScene(sceneLoadOrCreate));
        finish.setOnAction(e -> stage.setScene(sceneMain));

        stage.setScene(sceneLoadOrCreate);
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());

        return stage;
    }

    public void updateCanvas(Pane pane, RunwayCanvas canvas, double scale, double tx, double ty, boolean rotate) {
        pane.getChildren().clear();
        pane.getChildren().add(canvas);
        canvas.render((1.0 + scale * 0.001), tx, ty, rotate);
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

}
