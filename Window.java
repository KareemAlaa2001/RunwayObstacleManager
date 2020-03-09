
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Window {

    private Airport ap;
    private Map<String, RunwayCanvas> runways;
    private double currentScroll;
    private RunwayCanvas currentCanvas;
    private double currentXOffset, currentYOffset;

    public Window(Airport ap) {
        this.ap = ap;
    }

    public Stage getStage(Stage stage) throws Exception {
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
            RunwayCanvas runwayCanvas = new RunwayCanvas(1100, 900, runway.getRunL(),
                    runway.getRunR().getRunwaySpec().clearway,
                    runway.getRunR().getRunwaySpec().stopway,
                    runway.getGradedArea());
            runways.put(runway.getRunL().getName(),
                    runwayCanvas);
            runwayNames.add(runway.getRunR().getName());
            runways.put(runway.getRunR().getName(),
                    new RunwayCanvas(1100, 900, runway.getRunR(),
                            runway.getRunL().getRunwaySpec().clearway,
                            runway.getRunL().getRunwaySpec().stopway,
                            runway.getGradedArea()));
        }

        RunwayCanvas defaultCanvas = new RunwayCanvas(1100, 900);
        emptyPane.getChildren().add(defaultCanvas);
        defaultCanvas.render(1.0, 0, 0);
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
                    currentYOffset = Math.max(0.0, Math.min(emptyPane.getWidth() * (1.0 + currentScroll * 0.001) - emptyPane.getHeight() / 2, currentYOffset));
                    updateCanvas(emptyPane, currentCanvas, currentScroll, currentXOffset, currentYOffset);
                }
            }
        });

        ComboBox runwaySelectionBox = new ComboBox();
        runwaySelectionBox.getItems().addAll(runwayNames);

        runwaySelectionBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldVal, String newVal) {
                currentScroll = 0.0;
                currentXOffset = 0.0;
                currentYOffset = 325.0;
                currentCanvas = runways.get(newVal);
                updateCanvas(emptyPane, runways.get(newVal), currentScroll, currentXOffset, currentYOffset);
            }
        });

        grid.add(runwaySelectionBox, 2, 0);

        stage.setScene(new Scene(grid, 1360, 980));
        stage.setResizable(false);
        return stage;
    }

    public void updateCanvas(Pane pane, RunwayCanvas canvas, double scale, double tx, double ty) {
        pane.getChildren().clear();
        pane.getChildren().add(canvas);
        canvas.render((1.0 + scale * 0.001), tx, ty);
    }

}
