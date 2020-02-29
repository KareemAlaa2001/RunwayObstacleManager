import java.util.*;

public class RunwayOneWay
{
	private String name;
	private RunwayData dataOriginal;
	private RunwayData dataReCalc;
	private List<ObstacleData> obstacles;

	public RunwayOneWay(String name, RunwayData RD)
	{
		this.name = name;
		this.dataOriginal = RD;
		dataReCalc = null;
		obstacles = new ArrayList<ObstacleData>();
	}

	public void addObstacle(ObstacleData OD)
	{
		obstacles.add(OD);
		dataReCalc = Functions.reCalculate(dataOriginal, obstacles);
	}

	public String getName()
	{
		return name;
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
