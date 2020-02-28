public class RunwayOneWay
{
	private String name;
	private RunwayData dataOriginal;
	private RunwayData dataReCalc;

	public Runway(String name, RunwayData RD)
	{
		this.name = name;
		this.dataOriginal = RD;
		dataReCalc = null;
	}

	public void addObstacle(ObstacleData OD)
	{
		// Functions.AccountForObstacle();
	}
}
