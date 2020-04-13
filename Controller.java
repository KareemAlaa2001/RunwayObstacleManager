import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;



public class Controller extends Application
{
    private static Window window = null;

    private static Airport initAirport() {
        try {
            List<Runway> runwayList = new ArrayList();
            
        	Runway r1 = new Runway(9, new RunwayData(306, 3902, 3902, 3902, 3595), new RunwayData(0, 3884, 3962, 3884, 3884));
        	Runway r2 = new Runway(27, new RunwayData(0, 3660, 3660, 3660, 3660), new RunwayData(307, 3660, 3660, 3660, 3353));
        	Runway r3 = new Runway(16, new RunwayData(0, 3660, 4060, 3860, 3660), new RunwayData(307, 3660, 3660, 3660, 3660));
        	
	        r1.addObstacleL(new ObstacleData(-50, 12));
	        //r1.addObstacleR(new ObstacleData(3646, 12));

	        r2.addObstacleL(new ObstacleData(500, 25));
	        //r2.addObstacleR(new ObstacleData(2853, 25));

	        r3.addObstacleL(new ObstacleData(500, 21));
	        //r3.addObstacleR(new ObstacleData(2853, 21));

	        r3.addObstacleL(new ObstacleData(2853, 25));
	        //r3.addObstacleR(new ObstacleData(500, 25));

	        r3.addObstacleL(new ObstacleData(300, 25));
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

    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage stage) throws Exception
    {
        window = new Window(initAirport());
        System.out.println(window.toString());
        stage = window.getStage(stage);
        stage.show();
    }

    public static void addOutputText(String string)
    {
        if (window == null) {
            System.err.println("Attempted to add output text to null window");
            return;
        }
        window.addOutputText(string);
    }

    public static void addActivityText(String string)
    {
        if (window == null) {
            System.err.println("Attempted to add activity text to null window");
            return;
        }
        window.addActivityText(string);
    }

    public static void clearOutput()
    {
        if (window == null) {
            System.err.println("Attempted to clear output text of a null window");
            return;
        }
        window.clearOutput();
    }

    public static void clearActivity()
    {
        if (window == null) {
            System.err.println("Attempted to clear activity text of a null window");
            return;
        }
        window.clearActivity();
    }
}
