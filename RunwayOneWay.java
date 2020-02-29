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
		dataReCalc = Functions.reCalculate(RunwayData runway, ObstacleData obstacle);
	}

	public RunwayData getRunwaySpec()
	{ // Returns the runway data as specified without obstacles
		return dataOriginal;
	}

	public RunwayData getUpdatedRunway()
	{ // Can return null if no obstacle has been added
		return dataReCalc;
	}
}
