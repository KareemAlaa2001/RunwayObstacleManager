public class Runway
{	
	private int leftBearing;
	private RunwayOneWay RunL;
	private RunwayOneWay RunR;

	public Runway(int leftBearing, RunwayData left, RunwayData right)
	{
		this.leftBearing = leftBearing;
		RunL = new RunwayOneWay(leftBearing + "L", left);
		RunR = new RunwayOneWay(((leftBearing + 18) % 36) + "R", right);
	}

	public void addObstacleL(ObstacleData ODL)
	{
		RunL.addObstacle(ODL);
	}

	public void addObstacleR(ObstacleData ODR)
	{
		RunR.addObstacle(ODR);
	}

	public void getRunL()
	{
		return RunL;
	}

	public void getRunR()
	{
		return RunR;
	}
}
