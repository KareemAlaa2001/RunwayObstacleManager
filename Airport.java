import java.util.*;

public class Airport
{ // Default values below, can be set
	public static int RESA = 240;
	public static int StripEnd = 60;
	public static int BlastAllowance = 300;
	public static int MinSlope = 50;

	private List<Runway> runways;

	public Airport(int RESA, int StripEnd, int BlastAllowance, int MinSlope) throws Exception
	{
		if (RESA < 0 || StripEnd < 0 || BlastAllowance < 0 || MinSlope > 90 || MinSlope < 0) {
			throw new Exception("Invalid data given for airport");
		}
		this.RESA = RESA;
		this.StripEnd = StripEnd;
		this.BlastAllowance = BlastAllowance;
		this.MinSlope = MinSlope;
	}

	public Airport(List<Runway> runways) throws Exception
	{
		if (runways == null || runways.size() == 0) {
			throw new Exception("Error creating Airport from given runways");
		}
		this.runways = runways;
	}

	public RunwayOneWay getRunway(String runName) throws Exception
	{
		if (runName == null) {
			throw new Exception("No name give for runway");
		}
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
		throw new Exception("There is no runway in this airport with identifier '" + runName + "'");
	}

	public List<Runway> getRunways()
	{
		return runways;
	}

	public void addObstacle(ObstacleData OD, String runName) throws Exception
	{
		if (OD == null) {
			throw new Exception("No obstacleData given to add to runway " + runName);
		}
		try {
			getRunway(runName).addObstacle(OD);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
