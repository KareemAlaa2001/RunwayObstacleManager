import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Launcher extends Application {
    
    private static Stage stage;
    private MainController control;

    public static void main(String[] args) {

        launch(args);
    }

    public void start(Stage stage) throws Exception {
        stage.setTitle("Airport Display");
        stage.setResizable(false);
        stage.setMaximized(true);
        
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());
        
        this.stage = stage;

        control = new MainController(this);

        stage.show();
    }
    
    public static void setScene(WindowScene scene) {
        stage.setScene(scene.getScene());
    }
    
    public static Stage getStage() {
        return stage;
    }

}
