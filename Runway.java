public class Runway
{	
	private int leftBearing;
	private RunwayOneWay RunL;
	private RunwayOneWay RunR;

	public Runway(int leftBearing, RunwayData left, RunwayData right)
	{
		this.leftBearing = leftBearing;
		RunL = new RunwayOneWay(leftBearing + "L", left);
		RunR = new RunwayOneWay((36 - leftBearing) + "R", right);
	}

	public void addObstacleL(ObstacleData ODL)
	{
		RunL.addObstacle(ODL);
		// UpdateUI
	}

	public void addObstacleR(ObstacleData ODR)
	{
		RunR.addObstacle(ODR);
		// UpdateUI
	}
}
