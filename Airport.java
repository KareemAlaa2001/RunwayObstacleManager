import java.util.*;

public class Airport
{ // Default values below, can be set
	public static int RESA = 240;
	public static int StripEnd = 60;
	public static int BlastAllowance = 300;
	public static int MinSlope = 50;

	private List<Runway> runways;

	public Airport(int RESA, int StripEnd, int BlastAllowance, int MinSlope)
	{
		this.RESA = RESA;
		this.StripEnd = StripEnd;
		this.BlastAllowance = BlastAllowance;
		this.MinSlope = MinSlope;
	}

	public Airport(List<Runway> runways)
	{
		this.runways = runways;
	}

	public RunwayOneWay getRunway(String runName)
	{
		boolean left = runName.charAt(2) == 'L';
		
		if (left) {
			for (int i = 0; i < runways.size(); i++) {
				if (runways.get(i).getRunL().getName().equals(runName)) {
					return runways.get(i).getRunL();
				}
			}
		} else {
			for (int i = 0; i < runways.size(); i++) {
				if (runways.get(i).getRunR().getName().equals(runName)) {
					return runways.get(i).getRunR();
				}
			}
		}
		return null; // runways does not contain runName
	}

	public List<Runway> getRunways()
	{
		return runways;
	}

	public void addObstacle(ObstacleData OD, String runName)
	{
		getRunway(runName).addObstacle(OD);
	}
}
