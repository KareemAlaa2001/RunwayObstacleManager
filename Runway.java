public class Runway
{
	private String name;
	private int gradedArea; // distance from centreline in m, like radius it is only measured on one side and is symmetrical
	private int leftBearing;
	private RunwayOneWay RunL;
	private RunwayOneWay RunR;

	public Runway(int leftBearing, RunwayData left, RunwayData right)
	{
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

	public Runway(int leftBearing, int gradedArea, RunwayData left, RunwayData right)
	{
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

	public void addObstacleL(ObstacleData ODL)
	{ // This should be the only point at which an obstacle should be added to a runway, RunwayOneWay.addObstacle() is only for internal use
		RunL.addObstacle(ODL);
		RunR.addObstacle(new ObstacleData(RunR.getRunwaySpec().TORA - (ODL.position + RunL.getRunwaySpec().threshold), ODL.maxHeight));
		Controller.addActivityText("Added obstacle to runway " + name + ", " + ODL.position + "m from the " + RunL.getName() + " threshold.");
	}

	public void addObstacleR(ObstacleData ODR)
	{ // This should be the only point at which an obstacle should be added to a runway, RunwayOneWay.addObstacle() is only for internal use
		RunR.addObstacle(ODR);
		RunL.addObstacle(new ObstacleData(RunL.getRunwaySpec().TORA - (ODR.position + RunR.getRunwaySpec().threshold), ODR.maxHeight));
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
