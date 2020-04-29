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
		if (RESA < 0 || StripEnd < 0 || BlastAllowance < 0 || MinSlope < 0) {
			throw new IllegalArgumentException("Invalid data given for airport");
		}
		this.RESA = RESA;
		this.StripEnd = StripEnd;
		this.BlastAllowance = BlastAllowance;
		this.MinSlope = MinSlope;
	}

	public Airport(List<Runway> runways)
	{
		if (runways == null || runways.size() == 0) {
			throw new IllegalArgumentException("Error creating Airport from given runways");
		}
		this.runways = runways;
	}

	public void addRunways(List<Runway> newR)
	{
		if (newR == null) {
			throw new IllegalArgumentException("Error adding runways to airport");
		}

		if (runways == null) {
			this.runways = newR;
		} else {
			this.runways.addAll(newR);
		}
	}

	public void setRunways(List<Runway> newRunways) {
		if (newRunways == null)
			throw new IllegalArgumentException("Tried to set list of runways to null!");

		this.runways = newRunways;
	}

	public RunwayOneWay getRunway(String runName)
	{
		if (runName == null) {
			throw new IllegalArgumentException("No name given for runway");
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
		throw new IllegalArgumentException("There is no runway in this airport with identifier '" + runName + "'");
	}

	public Runway getRunwayFull(String name)
	{
		if (name == null) {
			throw new IllegalArgumentException("No name give for runway");
		}
		for (Runway run : runways) {
			if (run.getName().equals(name)) {
				return run;
			}
		}
		throw new IllegalArgumentException("There is no runway in this airport with identifier '" + name + "'");
	}

	public List<Runway> getRunways()
	{
		return runways;
	}

	public void addObstacle(ObstacleData OD, String runName, boolean left)
	{
		if (OD == null) {
			throw new IllegalArgumentException("No obstacleData given to add to runway " + runName);
		}
		try {
			if (left) {
				getRunwayFull(runName).addObstacleL(OD);
			} else {
				getRunwayFull(runName).addObstacleR(OD);			
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public void clearRunway(String runName) {
		getRunwayFull(runName).clear();
	}

	// TODO reimplement
	public void removeObstacle(ObstacleData OD, String runName, boolean left) {
		if (OD == null) throw new IllegalArgumentException("Obstacle you tried to remove is null!");
		getRunwayFull(runName).removeObstacle(OD);
	}
}
