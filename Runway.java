import java.util.List;

public class Runway
{
	private String name;
	private int gradedArea; // distance from centreline in m, like radius it is only measured on one side and is symmetrical
	private int leftBearing;
	private int rightBearing;
	private RunwayOneWay RunL;
	private RunwayOneWay RunR;

	public Runway(int leftBearing, RunwayData left, RunwayData right) throws IllegalArgumentException
	{
		if (leftBearing < 0 || left == null || right == null) {
			throw new IllegalArgumentException("Incorrect input to create a runway");
		}

		if (leftBearing < 10) {
			RunL = new RunwayOneWay("0" + leftBearing + "L", left);
			RunR = new RunwayOneWay(((leftBearing + 18) % 36) + "R", right);
		} else if (leftBearing < 28 && leftBearing >= 18) {
			RunL = new RunwayOneWay(leftBearing + "L", left);
			RunR = new RunwayOneWay("0" + ((leftBearing + 18) % 36) + "R", right);
		} else {
			RunL = new RunwayOneWay(leftBearing + "L", left);
			RunR = new RunwayOneWay(((leftBearing + 18) % 36) + "R", right);
		}
		this.name = RunL.getName() + "/" + RunR.getName();
		this.leftBearing = leftBearing;
		this.rightBearing = ((leftBearing + 18) % 36);
		this.gradedArea = 400;
		MainWindowController.addActivityText("Created runway " + name + ", without any graded area");
	}

	public Runway(int leftBearing, int gradedArea, RunwayData left, RunwayData right) throws IllegalArgumentException
	{
		if (leftBearing < 0 || left == null || right == null || gradedArea < 0) {
			throw new IllegalArgumentException("Incorrect input to create a runway");
		}

		if (leftBearing < 10) {
			RunL = new RunwayOneWay("0" + leftBearing + "L", left);
			RunR = new RunwayOneWay(((leftBearing + 18) % 36) + "R", right);
		} else if (leftBearing < 28 && leftBearing > 18) {
			RunL = new RunwayOneWay(leftBearing + "L", left);
			RunR = new RunwayOneWay("0" + ((leftBearing + 18) % 36) + "R", right);
		} else {
			RunL = new RunwayOneWay(leftBearing + "L", left);
			RunR = new RunwayOneWay(((leftBearing + 18) % 36) + "R", right);
		}
		this.name = RunL.getName() + "/" + RunR.getName();
		this.gradedArea = gradedArea;
		this.leftBearing = leftBearing;
		this.rightBearing = ((leftBearing + 18) % 36);
		MainWindowController.addActivityText("Created runway " + name);
	}

	public void checkObstacle(ObstacleData od, int[] leftStart)
	{
		int distanceFromCentreLine = MathsHelpers.getDistanceFromCentreLine(od, leftStart, MathsHelpers.calculateOtherStart(RunL.getRunwaySpec().TORA, leftBearing, leftStart));
		if (distanceFromCentreLine < 60) {
			RunL.addObstacle(distanceFromCentreLine, od, leftBearing, leftStart);
			RunR.addObstacle(-distanceFromCentreLine, od, rightBearing, MathsHelpers.calculateOtherStart(RunL.getRunwaySpec().TORA, leftBearing, leftStart));
		}
	}

	public String getName()
	{
		return name;
	}

	public int getGradedArea()
	{
		return gradedArea;
	}

	public void removeObstacle(ObstacleData OD, int[] leftStart, int[] rightStart)
	{
		int distanceFromCentreLine = MathsHelpers.getDistanceFromCentreLine(OD, leftStart, rightStart);
		RunL.removeObstacle(OD, distanceFromCentreLine, leftBearing, leftStart);
		RunR.removeObstacle(OD, -distanceFromCentreLine, rightBearing, rightStart);
		MainWindowController.addActivityText("Removed obstacle at " + OD.position + ", "  + OD.maxHeight + "m high from " + name);
	}

	public List<ObstacleData> clear()
	{
		List<ObstacleData> obs = RunL.clear();
		obs.addAll(RunR.clear());
		MainWindowController.addActivityText("Removed all obstacles from runway " + name);
		return obs;
	}
	/*
	public void addObstacleL(ObstacleData ODL) throws Exception
	{ // Should not be used by any class other than Airport
		RunL.addObstacle(ODL); // Should check if there is already an obstacle with these parameters added to the runway
		try {
			RunR.addObstacle(new ObstacleData(RunR.getRunwaySpec().TORA - (ODL.position + RunL.getRunwaySpec().threshold), ODL.maxHeight));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		MainWindowController.addActivityText("Added " + ODL.maxHeight + "m tall obstacle to runway " + name + ", " + ODL.position + "m from the " + RunL.getName() + " threshold.");
	}

	public void addObstacleR(ObstacleData ODR) throws Exception
	{ // Should not be used by any class other than Airport
		RunR.addObstacle(ODR); // Should check if there is already an obstacle with these parameters added to the runway
		try {
			RunL.addObstacle(new ObstacleData(RunL.getRunwaySpec().TORA - (ODR.position + RunR.getRunwaySpec().threshold), ODR.maxHeight));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		MainWindowController.addActivityText("Added obstacle to runway " + name + ", " + ODR.position + "m from the " + RunR.getName() + " threshold.");
	}
	*/
	public RunwayOneWay getRunL()
	{
		return RunL;
	}

	public RunwayOneWay getRunR()
	{
		return RunR;
	}

	public int getLeftBearing()
	{
		return leftBearing;
	}

	public int getRightBearing()
	{
		return rightBearing;
	}
}
