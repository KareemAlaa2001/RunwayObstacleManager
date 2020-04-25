
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

    private Airport initAirport()
    { // Temporary, will be removed once user input has been succewssfully implemented
        try {
            List<Runway> runwayList = new ArrayList();

            Runway r1 = new Runway(9, new RunwayData(306, 3902, 3902, 3902, 3595), new RunwayData(0, 3884, 3962, 3884, 3884));
            Runway r2 = new Runway(27, new RunwayData(0, 3660, 3660, 3660, 3660), new RunwayData(307, 3660, 3660, 3660, 3353));
            Runway r3 = new Runway(16, new RunwayData(0, 3660, 4060, 3860, 3660), new RunwayData(307, 3660, 3660, 3660, 3660));

            //r1.addObstacleL(new ObstacleData(-50, 12));
            //r1.addObstacleR(new ObstacleData(3646, 12));
            //r2.addObstacleL(new ObstacleData(500, 25));
            //r2.addObstacleR(new ObstacleData(2853, 25));
            //r3.addObstacleL(new ObstacleData(500, 21));
            //r3.addObstacleR(new ObstacleData(2853, 21));
            //r3.addObstacleL(new ObstacleData(2853, 25));
            //r3.addObstacleR(new ObstacleData(500, 25));
            //r3.addObstacleL(new ObstacleData(300, 25));
            //r3.addObstacleR(new ObstacleData(3053, 25));
            runwayList.add(r1);
            runwayList.add(r2);
            runwayList.add(r3);

            return new Airport(runwayList);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
    
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
        mainWindowScene.setAirport(initAirport());
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
