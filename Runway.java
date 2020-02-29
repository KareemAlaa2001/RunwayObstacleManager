public class Runway
{	
	private int leftBearing;
	private RunwayOneWay RunL;
	private RunwayOneWay RunR;

	public Runway(int leftBearing, RunwayData left, RunwayData right)
	{
		this.leftBearing = leftBearing;
		
		if (leftBearing < 10) {
			RunL = new RunwayOneWay("0" + leftBearing + "L", left);
			RunR = new RunwayOneWay(((leftBearing + 18) % 36) + "R", right);
		} else if (leftBearing < 27 && leftBearing > 18) {
			RunL = new RunwayOneWay(leftBearing + "L", left);
			RunR = new RunwayOneWay("0" + ((leftBearing + 18) % 36) + "R", right);
		} else {
			RunL = new RunwayOneWay(leftBearing + "L", left);
			RunR = new RunwayOneWay(((leftBearing + 18) % 36) + "R", right);
		}
	}

	public void addObstacleL(ObstacleData ODL)
	{
		RunL.addObstacle(ODL);
	}

	public void addObstacleR(ObstacleData ODR)
	{
		RunR.addObstacle(ODR);
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
