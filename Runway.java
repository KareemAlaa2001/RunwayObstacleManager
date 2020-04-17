public class Runway
{
	private String name;
	private int gradedArea; // distance from centreline in m, like radius it is only measured on one side and is symmetrical
	private int leftBearing;
	private RunwayOneWay RunL;
	private RunwayOneWay RunR;

	public Runway(int leftBearing, RunwayData left, RunwayData right) throws Exception
	{
		if (leftBearing < 0 || left == null || right == null) {
			throw new Exception("Incorrect input to create a runway");
		}
		this.leftBearing = leftBearing;
		this.gradedArea = 400;

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
		Controller.addActivityText("Created runway " + name + ", without any graded area");
	}

	public Runway(int leftBearing, int gradedArea, RunwayData left, RunwayData right) throws Exception
	{
		if (leftBearing < 0 || left == null || right == null || gradedArea < 0) {
			throw new Exception("Incorrect input to create a runway");
		}
		this.gradedArea = gradedArea;
		this.leftBearing = leftBearing;

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
		Controller.addActivityText("Created runway " + name);
	}

	public String getName()
	{
		return name;
	}

	public int getGradedArea()
	{
		return gradedArea;
	}

	public void addObstacleL(ObstacleData ODL) throws Exception
	{ // Should not be used by any class other than Airport
		RunL.addObstacle(ODL); // Should check if there is already an obstacle with these parameters added to the runway
		try {
			RunR.addObstacle(new ObstacleData(RunR.getRunwaySpec().TORA - (ODL.position + RunL.getRunwaySpec().threshold), ODL.maxHeight));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		Controller.addActivityText("Added " + ODL.maxHeight + "m tall obstacle to runway " + name + ", " + ODL.position + "m from the " + RunL.getName() + " threshold.");
	}

	public void addObstacleR(ObstacleData ODR) throws Exception
	{ // Should not be used by any class other than Airport
		RunR.addObstacle(ODR); // Should check if there is already an obstacle with these parameters added to the runway
		try {
			RunL.addObstacle(new ObstacleData(RunL.getRunwaySpec().TORA - (ODR.position + RunR.getRunwaySpec().threshold), ODR.maxHeight));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		Controller.addActivityText("Added obstacle to runway " + name + ", " + ODR.position + "m from the " + RunR.getName() + " threshold.");
	}

	public RunwayOneWay getRunL()
	{
		return RunL;
	}

	public RunwayOneWay getRunR()
	{
		return RunR;
	}
}
