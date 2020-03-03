
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Window extends Application {

    private Map<String, RunwayCanvas> runways;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        stage.setTitle("Airport Display");
        Pane emptyPane = new Pane();
        emptyPane.setPrefSize(1100, 900);

        grid.add(emptyPane, 0, 0);

        grid.add(new Label("Select Runway:"), 1, 0);

        ArrayList<Runway> runwayList = new ArrayList();
        Runway r1 = new Runway(9, new RunwayData(306, 3902, 3902, 3902, 3595), new RunwayData(0, 3884, 3962, 3884, 3884));
        Runway r2 = new Runway(27, new RunwayData(0, 3660, 3660, 3660, 3660), new RunwayData(307, 3660, 3660, 3660, 3353));
        Runway r3 = new Runway(16, new RunwayData(0, 3660, 4060, 3860, 3660), new RunwayData(307, 3660, 3660, 3660, 3660));
        r1.addObstacleL(new ObstacleData(-50, 12));
        r1.addObstacleR(new ObstacleData(3646, 12));
        r2.addObstacleL(new ObstacleData(500, 25));
        r2.addObstacleR(new ObstacleData(2853, 25));
        r3.addObstacleL(new ObstacleData(500, 25));
        r3.addObstacleR(new ObstacleData(2853, 25));
        runwayList.add(r1);
        runwayList.add(r2);
        runwayList.add(r3);
        Airport ap = new Airport(runwayList);

        runways = new HashMap();
        List<String> runwayNames = new ArrayList();
        for (Runway runway : ap.getRunways()) {
            runwayNames.add(runway.getRunL().getName());
            runways.put(runway.getRunL().getName(),
                    new RunwayCanvas(1100, 900, runway.getRunL().getUpdatedRunway(), runway.getRunL().getName(), runway.getRunL().getObstacles(), runway.getRunL().getRunwaySpec().TORA));
            runwayNames.add(runway.getRunR().getName());
            runways.put(runway.getRunR().getName(),
                    new RunwayCanvas(1100, 900, runway.getRunR().getUpdatedRunway(), runway.getRunR().getName(), runway.getRunR().getObstacles(), runway.getRunR().getRunwaySpec().TORA));
        }

        RunwayCanvas defaultCanvas = new RunwayCanvas(1100, 900, null, "Unselected", null, 0);
        emptyPane.getChildren().add(defaultCanvas);
        defaultCanvas.render();

        ComboBox runwaySelectionBox = new ComboBox();
        runwaySelectionBox.getItems().addAll(runwayNames);

        runwaySelectionBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldVal, String newVal) {
                updateCanvas(emptyPane, runways.get(newVal));
            }
        });

        grid.add(runwaySelectionBox, 2, 0);

        stage.setScene(new Scene(grid, 1360, 980));
        stage.setResizable(false);
        stage.show();
    }

    public void updateCanvas(Pane pane, RunwayCanvas canvas) {
        pane.getChildren().clear();
        pane.getChildren().add(canvas);
        canvas.render();
    }

}
