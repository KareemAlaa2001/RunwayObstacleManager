import java.util.*;

public class Airport
{
	private List<Runway> runways;

	public Airport(List<Runway> runways)
	{
		this.runways = runways;
	}

	public List<Runway> getRunways()
	{
		return runways;
	}

	public void addObstacle(ObstacleData OD, String runName)
	{
		boolean left = runName.charAt(2) == 'L';
		
		if (left) {
			for (int i = 0; i < runways.size(); i++) {
				if (runways.get(i).getRunL().getName().equals(runName)) {
					runways.get(i).addObstacleL(OD);
				}
			}
		} else {
			for (int i = 0; i < runways.size(); i++) {
				if (runways.get(i).getRunR().getName().equals(runName)) {
					runways.get(i).addObstacleR(OD);
				}
			}
		}
	}
}
