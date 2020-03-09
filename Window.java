
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

public class Window
{
    private Airport ap;
    private Map<String, RunwayCanvas> runways;

    public Window(Airport ap)
    {
        this.ap = ap; 
    }

    public Stage getStage(Stage stage) throws Exception
    {
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


        runways = new HashMap();
        List<String> runwayNames = new ArrayList();
        for (Runway runway : ap.getRunways()) {
            runwayNames.add(runway.getRunL().getName());
            runways.put(runway.getRunL().getName(),
                    new RunwayCanvas(1100, 900, runway.getRunL(),
                            runway.getRunR().getRunwaySpec().clearway,
                            runway.getRunR().getRunwaySpec().stopway,
                            runway.getGradedArea()));
            runwayNames.add(runway.getRunR().getName());
            runways.put(runway.getRunR().getName(),
                    new RunwayCanvas(1100, 900, runway.getRunR(),
                            runway.getRunL().getRunwaySpec().clearway,
                            runway.getRunL().getRunwaySpec().stopway,
                            runway.getGradedArea()));
        }

        RunwayCanvas defaultCanvas = new RunwayCanvas(1100, 900);
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
        return stage;
    }

    public void updateCanvas(Pane pane, RunwayCanvas canvas)
    {
        pane.getChildren().clear();
        pane.getChildren().add(canvas);
        canvas.render();
    }

}
