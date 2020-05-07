import java.util.*;

public class RunwayOneWay
{
	private String name;
	private RunwayData dataOriginal;
	private RunwayData dataReCalc;
	private List<ObstacleData> impactfulObstacles;
	private List<ObstacleData> otherObstacles;

	public RunwayOneWay(String name, RunwayData RD)
	{
		this.name = name;
		this.dataOriginal = RD;
		dataReCalc = RD;
		otherObstacles = new ArrayList<ObstacleData>();
		impactfulObstacles = new ArrayList<ObstacleData>();
	}

	public void addObstacle(int distanceFromCentreLine, ObstacleData OD, int bearing, int[] start)
	{
		MainWindowController.addOutputText("Re-Calculating Runway " + name + " due to Obstacle " + OD.maxHeight + "m high, " + OD.position + "m from threshold " + distanceFromCentreLine + "m from the centreline.");
		RunwayData newData = Functions.reCalculate(dataOriginal, MathsHelpers.calculateDistance(OD, bearing, dataOriginal.TORA, start), OD.maxHeight);
		
		if (newData == null) {
			MainWindowController.addOutputText("Re-Calculating Runway " + name + " causes definate closure, new values are too extreme");
			MainWindowController.addOutputText("");
		} else {
			if (impactfulObstacles.size() == 0) {
				impactfulObstacles.add(OD);
				dataReCalc = newData;
			} else if (dataCausedChange(newData)) {
				setNewData(newData);
			} else {
				otherObstacles.add(OD);
			}
		}
	}

	// TODO properly reimplement this for sprint 3
	public void removeObstacle(ObstacleData OD, int distanceFromCentreLine, int bearing, int[] start) {
		if (OD == null) throw new IllegalArgumentException("Trying to remove a null obstacle!");
		if (otherObstacles.contains(OD) || impactfulObstacles.contains(OD)) {
			MainWindowController.addOutputText("Re-calculating runway " + name + " due to the removal of the obstacle " + OD.maxHeight + "m high, " + OD.position + "m from threshold.");
		} else {
			return;
		}
		dataReCalc = dataOriginal;

		otherObstacles.remove(OD);
		impactfulObstacles.remove(OD);

		List<ObstacleData> allObstacles = clear();
		for (ObstacleData ob : allObstacles) {
			addObstacle(distanceFromCentreLine, ob, bearing, start);
		}
	}

	public List<ObstacleData> clear() {
		List<ObstacleData> ret = new ArrayList<ObstacleData>();
		ret.addAll(impactfulObstacles);
		ret.addAll(otherObstacles);

		dataReCalc = dataOriginal;
		impactfulObstacles.clear();
		otherObstacles.clear();

		return ret;
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
        return otherObstacles;
    }

    private void setNewData(RunwayData newData)
    {
    	if (newData.threshold > dataReCalc.threshold) {
    		dataReCalc.threshold = newData.threshold;
    	} if (newData.takeoffThreshold > dataReCalc.takeoffThreshold) {
    		dataReCalc.takeoffThreshold = newData.takeoffThreshold;
    	} if (newData.TORA < dataReCalc.TORA) {
    		dataReCalc.TORA = newData.TORA;
    	} if (newData.TODA < dataReCalc.TODA) {
    		dataReCalc.TODA = newData.TODA;
    	} if (newData.ASDA < dataReCalc.ASDA) {
    		dataReCalc.ASDA = newData.ASDA;
    	} if (newData.LDA < dataReCalc.LDA) {
    		dataReCalc.LDA = newData.LDA;
    	}
    	dataReCalc.stopway = newData.stopway;
    	dataReCalc.clearway = newData.clearway;
    }

    private boolean dataCausedChange(RunwayData newData)
    {
    	return 	newData.threshold > dataReCalc.threshold || newData.takeoffThreshold > dataReCalc.takeoffThreshold ||
    			newData.TORA < dataReCalc.TORA || newData.TODA < dataReCalc.TODA ||
    			newData.ASDA < dataReCalc.ASDA || newData.LDA < dataReCalc.LDA;
    }
}
