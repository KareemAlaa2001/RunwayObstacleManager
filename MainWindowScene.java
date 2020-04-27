
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

public class MainWindowScene extends WindowScene {
    
    private Airport ap;
    private Map<String, RunwayCanvas> runways;
    private List<String> runwayNames;
    private ComboBox runwaySelectionBox;
    private double currentScroll;
    private double mouseDX, mouseDY;
    private RunwayCanvas currentCanvas;
    private double currentXOffset, currentYOffset;
    private TextArea outputTextArea, activityTextArea;

    public MainWindowScene(Launcher app, MainController control, Airport airp) {
//    public MainWindowScene(Launcher app) {
        super(app);


        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        gridPane.setPrefSize(app.getStage().getWidth(), app.getStage().getWidth());
        gridPane.setAlignment(Pos.TOP_LEFT);
        
        Pane emptyPane = new Pane();
        emptyPane.setPrefSize(1100, 900);
        
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

        runwaySelectionBox = new ComboBox();

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
                    Logger.getLogger(MainWindowScene.class.getName()).log(Level.SEVERE, null, ex);
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
//        toAirportScene.setOnAction(e -> app.setScene(app.loadCreateWindowScene));

        Button toRunwayScene = new Button("To runway scene");
//        toRunwayScene.setOnAction(e -> app.setScene(app.loadCreateWindowScene));

        tabPane.getTabs().add(outputTab);
        tabPane.getTabs().add(activityTab);

        gridPane.add(emptyPane, 0, 0, 1, 4);
        gridPane.add(new Label("Select Runway:"), 1, 0);
        gridPane.add(runwaySelectionBox, 2, 0);
        gridPane.add(rotateSelect, 1, 1, 2, 1);
        gridPane.add(obstacleHeightInput, 1, 2, 1, 1);
        gridPane.add(obstacleLocationInput, 2, 2, 1, 1);
        gridPane.add(addObstacleButton, 1, 3, 2, 1);
        gridPane.add(tabPane, 1, 4, 2, 2);
        gridPane.add(toRunwayScene, 2, 5);
        gridPane.add(toAirportScene, 2, 6);
        
        scene = new Scene(gridPane);

        this.setAirport(airp);

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
    
    public void setAirport(Airport ap) {
        this.ap = ap;
        
        runways = new HashMap();
        runwayNames = new ArrayList();
        for (Runway runway : ap.getRunways()) {
            runwayNames.add(runway.getName());
            RunwayCanvas runwayCanvas = new RunwayCanvas(1100, 900, runway);
            runways.put(runway.getName(), runwayCanvas);
        }
        
        runwaySelectionBox.getItems().clear();
        runwaySelectionBox.getItems().addAll(runwayNames);
    }
    
}
