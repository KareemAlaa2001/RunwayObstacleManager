
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Launcher extends Application {

    public MainWindowScene mainWindowScene;
    public LoadCreateWindowScene loadCreateWindowScene;
    public RunwayWindowScene runwayWindowScene;
    
    //private MainWindowController mainWindowController;
    
    private Stage stage;
    
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
        
        mainWindowScene = new MainWindowScene(this);
        loadCreateWindowScene = new LoadCreateWindowScene(this);
        runwayWindowScene = new RunwayWindowScene(this);
        
        //mainWindowController = new MainWindowController(mainWindowScene);
        MainWindowController.setScene(mainWindowScene);

        //Temporary
        mainWindowScene.setAirport(MainWindowController.initAirport());
        //
        
        setScene(loadCreateWindowScene);
        
        stage.show();
    }
    
    public void setScene(WindowScene scene) {
        stage.setScene(scene.getScene());
    }
    
    public Stage getStage() {
        return stage;
    }

}
