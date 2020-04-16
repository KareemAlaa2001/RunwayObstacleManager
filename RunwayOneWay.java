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
		dataReCalc = RD;
		obstacles = new ArrayList<ObstacleData>();
	}

	public void addObstacle(ObstacleData OD) throws Exception
	{
		Controller.addOutputText("Re-Calculating Runway " + name + " due to Obstacle " + OD.maxHeight + "m high, " + OD.position + "m from threshold.");
		dataReCalc = Functions.reCalculate(dataOriginal, OD);
		obstacles.add(OD);
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
	{ // Same as dataOriginal if no obstacles added
		return dataReCalc;
	}
    
    public List<ObstacleData> getObstacles() 
    {
        return obstacles;
    }
}
