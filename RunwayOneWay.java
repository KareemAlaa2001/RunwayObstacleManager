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
		MainWindowController.addOutputText("Re-Calculating Runway " + name + " due to Obstacle " + OD.maxHeight + "m high, " + OD.position + "m from threshold.");
		RunwayData newData = Functions.reCalculate(dataOriginal, OD);
		
		if (newData == null) {
			MainWindowController.addOutputText("Re-Calculating Runway " + name + " causes definate closure, new values are too extreme");
			MainWindowController.addOutputText("");
		} else {
			dataReCalc = newData;
			obstacles.add(OD);
		}
	}

	// TODO properly reimplement this for sprint 3
	public void removeObstacle(ObstacleData OD) {
		if (OD == null) throw new IllegalArgumentException("Trying to remove a null obstacle!");
		if (!obstacles.contains(OD)) throw new IllegalArgumentException("The obstacle you are trying to remove is not in this runwayOneWay!");
		MainWindowController.addOutputText("Re-calculating runway " + name + " due to the removal of the obstacle " + OD.maxHeight + "m high, " + OD.position + "m from threshold.");
		dataReCalc = dataOriginal;
		obstacles.remove(OD);
	}

	public void clear() {
		dataReCalc = dataOriginal;
		obstacles.clear();
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
