
import javafx.stage.Stage;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;

public class InputScreenController
{
	private static LoadCreateWindowScene loadCreateWindowScene = null;
	private static RunwayWindowScene runwayWindowScene = null;

	public static void goToRunwayScreen(Airport ap) {
		if (runwayWindowScene == null)
			runwayWindowScene = new RunwayWindowScene(ap);

		runwayWindowScene.setAirport(ap);
		Launcher.setScene(runwayWindowScene);
	}

	public static void goToLoadCreateWindowScene() {
		if (loadCreateWindowScene == null)
			loadCreateWindowScene = new LoadCreateWindowScene();

		Launcher.setScene(loadCreateWindowScene);
	}

	public static void goToMainScene(Airport ap) {
		MainWindowController.goToMainScreen(ap);
	}

	public static Stage getAppStage() {
		return MainController.getAppStage();
	}

	public static Airport initialiseAirport(int RESA, int StripEnd, int BlastAllowance, int MinSlope)
	{
		return new Airport(RESA, StripEnd, BlastAllowance, MinSlope);
	}

	public static Runway initRunway(int bearing, RunwayData left, RunwayData right)
	{
		return new Runway(bearing, left, right);
	}

	public static RunwayData initRunwayData(int threshold, int TORA, int stopway, int clearway)
	{
		return new RunwayData(threshold, TORA, stopway, clearway);
	}

	public static Airport addRunwaysToAirport(Airport airport, List<Runway> runways) throws Exception
	{
		List<int[]> places = new ArrayList<int[]>();
		for (Runway run : runways) {
			places.add(new int[] {0, 0});
		}
		airport.addRunways(runways, places);
		return airport;
	}

	public static Runway importRunway(String filePath) throws JAXBException, FileNotFoundException {
		return XMLLoader.importRunway(filePath);
	}
}
