import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Window extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        stage.setTitle("Airport Display");
        //RunwayCanvas canvas = new RunwayCanvas(1000, 1000);
        //canvas.render();
        //root.getChildren().add(canvas);

        Canvas c = new Canvas(700, 700);
        grid.add(c, 0, 0);

        grid.add(new Label("Select Runway:"), 1, 0);

        ObservableList<String> options = FXCollections.observableArrayList(
                "Option 1",
                "Option 2",
                "Option 3"
        );
        ComboBox comboBox = new ComboBox(options);
        grid.add(comboBox, 2, 0);


        stage.setScene(new Scene(grid));
        stage.show();
    }

}