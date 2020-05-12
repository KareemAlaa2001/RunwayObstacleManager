import java.util.*;
import javax.xml.bind.annotation.*;
import java.awt.Point;

@XmlRootElement
@XmlType(propOrder = { "resa", "stripEnd", "blastAllowance", "minSlope", "runways","runwayPositions","obstacles"})
public class Airport
{ // Default values below, can be set
	public static int RESA = 240;
	public static int StripEnd = 60;
	public static int BlastAllowance = 300;
	public static int MinSlope = 50;

	// Runway designations, position of start of left runway
	private Map<String, Point> runwayPositions;

	private List<ObstacleData> obstacles;
	private List<Runway> runways;

	private Airport() {
		runways = new ArrayList<>();
		obstacles = new ArrayList<>();
		runwayPositions = new HashMap<>();
	}

	public Airport(int RESA, int StripEnd, int BlastAllowance, int MinSlope)
	{
		if (RESA < 0 || StripEnd < 0 || BlastAllowance < 0 || MinSlope < 0) {
			throw new IllegalArgumentException("Invalid data given for airport");
		}
		runways = new ArrayList<>();
		this.RESA = RESA;
		this.StripEnd = StripEnd;
		this.BlastAllowance = BlastAllowance;
		this.MinSlope = MinSlope;
		this.obstacles = new ArrayList<ObstacleData>();
		runwayPositions = new HashMap<String, Point>();
	}

	@XmlElementWrapper(name = "runways")
	@XmlElement(name = "runway")
	public List<Runway> getRunways() {
		return runways;
	}

	@XmlElement(name = "resa")
	public int getResa() {
		return this.RESA;
	}

	@XmlElement(name = "stripEnd")
	public int getStripEnd() {
		return this.StripEnd;
	}

	@XmlElement(name = "blastAllowance")
	public int getBlastAllowance() {
		return this.BlastAllowance;
	}

	@XmlElement(name = "minSlope")
	public int getMinSlope() {
		return this.MinSlope;
	}

	@XmlElementWrapper(name = "runwayPositions")
	@XmlElement(name = "runwayPosition")
	public Map<String,Point> getRunwayPositions() {
	    return this.runwayPositions;
    }

    @XmlElementWrapper(name = "obstacles")
    @XmlElement(name = "obstacle")
    public List<ObstacleData> getObstacles() {
        return this.obstacles;
    }

	public void setRunways(List<Runway> runways) {
		this.runways = runways;
	}

	public void setRunwayPositions(Map<String,Point> positions) {
	    this.runwayPositions = positions;
    }

    public void setObstacles(List<ObstacleData> obstacles) {
        this.obstacles = obstacles;
    }


    public void addRunway(Runway run)
	{
		if (run == null) {
			throw new IllegalArgumentException("Error adding runways to airport");
		}
		runwayPositions.put(run.getName(), new Point(10000 * runways.size(), 10000 * runways.size()));
		
		this.runways.add(run);
	}

	public void addRunway(Runway oldRun, int intersectionPoint, Runway toAdd, int newRunIntersection)
	{ // intersectionPoint: 	distance from start of left threshold in oldRun that an intersection occurs
	  // newRunIntersection:	distance from start of left runway in toAdd that an intersection occurs
		Point runStart = runwayPositions.get(oldRun.getName());
		Point otherRunStart = MathsHelpers.calculatePositionFromIntersection(runStart, oldRun, intersectionPoint, toAdd, newRunIntersection);

		runwayPositions.put(toAdd.getName(), otherRunStart);
		this.runways.add(toAdd);
	}

	public Map<Runway, Point> getIntersectingRunways(Runway run)
	{
		Map<Runway, Point> returnVals = new HashMap<Runway, Point>();
		Point relativePoint = runwayPositions.get(run.getName());
		returnVals.put(run, new Point(0, 0));

		for (Runway runway : runways) {
			if (runway != run && MathsHelpers.linesintersect(	runwayPositions.get(run.getName()), 	run.getLeftBearing(), 		run.getRunL().getRunwaySpec().TORA,
																runwayPositions.get(runway.getName()), 	runway.getLeftBearing(),	runway.getRunL().getRunwaySpec().TORA)) {

				Point properPoint = runwayPositions.get(runway.getName());
				returnVals.put(run, new Point(properPoint.x - relativePoint.x, properPoint.y - relativePoint.y));
			}
		}
		return returnVals;
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

	public Point getRunwayPos(String runName)
	{
		return runwayPositions.get(runName);
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

	// runName is taken to be the full name,  "09L/27R"
	public void addObstacle(int height, int runwayPosition, String runName, boolean left)
	{
		if (runName == null) {
			throw new IllegalArgumentException("No runway specified to add obstacle to");
		}

		if (left) {
			addObstacle(new ObstacleData(MathsHelpers.calculatePosition(getRunwayFull(runName).getLeftBearing(), runwayPosition, 0, runwayPositions.get(runName)), height));
		} else {
			addObstacle(new ObstacleData(MathsHelpers.calculatePosition(getRunwayFull(runName).getRightBearing(), runwayPosition, 0, MathsHelpers.calculatePositionInner(getRunwayFull(runName).getLeftBearing(), getRunwayFull(runName).getRunR().getRunwaySpec().TORA, runwayPositions.get(runName))), height));
		}
	}

	// runName is taken to be the full name,  "09L/27R"
	public void addObstacle(int height, int runwayPosition, int distanceFromCentreLine, String runName, boolean left)
	{
		if (runName == null) {
			throw new IllegalArgumentException("No runway specified to add obstacle to");
		}
		
		// System.out.println("Obstacle Position: " + toAdd.position.x + ", " + toAdd.position.y + "\nRunway Position: " + runwayPositions.get(runName).x + ", " + runwayPositions.get(runName).y);
		if (left) {
			addObstacle(new ObstacleData(MathsHelpers.calculatePosition(getRunwayFull(runName).getLeftBearing(), runwayPosition, distanceFromCentreLine, runwayPositions.get(runName)), height));
		} else {
			addObstacle(new ObstacleData(MathsHelpers.calculatePosition(getRunwayFull(runName).getRightBearing(), runwayPosition, distanceFromCentreLine, MathsHelpers.calculatePositionInner(getRunwayFull(runName).getLeftBearing(), getRunwayFull(runName).getRunR().getRunwaySpec().TORA, runwayPositions.get(runName))), height));
		}
	}

	public void clearRunway(String runName)
	{
		List<ObstacleData> obstaclesToRemove = getRunwayFull(runName).clear();
		for (Runway run : runways) {
			for (ObstacleData ob : obstaclesToRemove) {
				run.removeObstacle(ob, runwayPositions.get(run.getName()), MathsHelpers.calculateOtherStart(run.getRunL().getRunwaySpec().TORA, run.getLeftBearing(), runwayPositions.get(run.getName())));
			}
		}
		obstacles.removeAll(obstaclesToRemove);
	}

	public void removeObstacle(ObstacleData OD)
	{
		if (OD == null) throw new IllegalArgumentException("Obstacle you tried to remove is null");
		obstacles.remove(OD);
		for (Runway run : runways) {
			run.removeObstacle(OD, runwayPositions.get(run.getName()), MathsHelpers.calculateOtherStart(run.getRunL().getRunwaySpec().TORA, run.getLeftBearing(), runwayPositions.get(run.getName())));
		}
	}

	private void addObstacle(ObstacleData OD)
	{
		obstacles.add(OD);
		for (Runway run : runways) {
			run.checkObstacle(OD, runwayPositions.get(run.getName()));
		}
	}

	public List<ObstacleData> getRunwayObstacles(Runway run) {
		if (!this.getRunways().contains(run)) throw new IllegalArgumentException("Passed a runway which is not part of this airport!");
		else {
			List<ObstacleData> runObs = new ArrayList<>();
			this.getObstacles().forEach(obs -> {
				if (run.getRunL().getImpactfulObstacles().contains(obs)) runObs.add(obs);
			});

			return runObs;
		}
	}

	public static Runway findRunwayByName(String runName, List<Runway> runways) {
		for (Runway run: runways) {
			if (run.getName().equals(runName)) return run;
		}

		throw new IllegalArgumentException("Runway name doesn't match that of any runways in the passed list!");
	}
}
