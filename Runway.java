import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "runway")
@XmlType(propOrder = { "name", "gradedArea", "leftBearing", "rightBearing", "runL", "runR"})
public class Runway
{
	private String name;
	private int gradedArea; // distance from centreline in m, like radius it is only measured on one side and is symmetrical
	private int leftBearing;
	private int rightBearing;
	private RunwayOneWay RunL;
	private RunwayOneWay RunR;

	private Runway() { }

	public Runway(int leftBearing, RunwayData left, RunwayData right) throws IllegalArgumentException
	{
		if (leftBearing < 0 || left == null || right == null) {
			throw new IllegalArgumentException("Incorrect input to create a runway");
		}

		if (leftBearing < 10) {
			RunL = new RunwayOneWay("0" + leftBearing + "L", left);
			RunR = new RunwayOneWay(((leftBearing + 18) % 36) + "R", right);
		} else if (leftBearing < 28 && leftBearing >= 18) {
			RunL = new RunwayOneWay(leftBearing + "L", left);
			RunR = new RunwayOneWay("0" + ((leftBearing + 18) % 36) + "R", right);
		} else {
			RunL = new RunwayOneWay(leftBearing + "L", left);
			RunR = new RunwayOneWay(((leftBearing + 18) % 36) + "R", right);
		}
		this.name = RunL.getName() + "/" + RunR.getName();
		this.leftBearing = leftBearing;
		this.rightBearing = ((leftBearing + 18) % 36);
		this.gradedArea = 400;
		MainWindowController.addActivityText("Created runway " + name + ", without any graded area");
	}

	public Runway(int leftBearing, int gradedArea, RunwayData left, RunwayData right) throws IllegalArgumentException
	{
		if (leftBearing < 0 || left == null || right == null || gradedArea < 0) {
			throw new IllegalArgumentException("Incorrect input to create a runway");
		}

		if (leftBearing < 10) {
			RunL = new RunwayOneWay("0" + leftBearing + "L", left);
			RunR = new RunwayOneWay(((leftBearing + 18) % 36) + "R", right);
		} else if (leftBearing < 28 && leftBearing > 18) {
			RunL = new RunwayOneWay(leftBearing + "L", left);
			RunR = new RunwayOneWay("0" + ((leftBearing + 18) % 36) + "R", right);
		} else {
			RunL = new RunwayOneWay(leftBearing + "L", left);
			RunR = new RunwayOneWay(((leftBearing + 18) % 36) + "R", right);
		}
		this.name = RunL.getName() + "/" + RunR.getName();
		this.gradedArea = gradedArea;
		this.leftBearing = leftBearing;
		this.rightBearing = ((leftBearing + 18) % 36);
		MainWindowController.addActivityText("Created runway " + name);
	}

	public void checkObstacle(ObstacleData od, Point leftStart)
	{
		Point endpos = MathsHelpers.calculateOtherStart(RunL.getRunwaySpec().TORA, leftBearing, leftStart);
		int distanceFromCentreLine = MathsHelpers.getDistanceFromCentreLine(od, leftStart, endpos);

		if (distanceFromCentreLine <= 60) {
			MainWindowController.addActivityText("Added obstacle " + od.maxHeight + "m high near runway " + name);
			RunL.addObstacle(distanceFromCentreLine, od, leftBearing, leftStart);
			RunR.addObstacle(-distanceFromCentreLine, od, rightBearing, MathsHelpers.calculateOtherStart(RunL.getRunwaySpec().TORA, leftBearing, leftStart));
		}
	}

	public void removeObstacle(ObstacleData OD, Point leftStart, Point rightStart)
	{
		int distanceFromCentreLine = MathsHelpers.getDistanceFromCentreLine(OD, leftStart, rightStart);
		RunL.removeObstacle(OD, distanceFromCentreLine, leftBearing, leftStart);
		RunR.removeObstacle(OD, -distanceFromCentreLine, rightBearing, rightStart);
		MainWindowController.addActivityText("Removed obstacle " + OD.maxHeight + "m high from near runway " + name);
	}

	public List<ObstacleData> clear()
	{
		List<ObstacleData> obs = RunL.clear();
		obs.addAll(RunR.clear());
		MainWindowController.addActivityText("Removed all obstacles near runway " + name);
		return obs;
	}

	@XmlElement(name = "name")
	public String getName()
	{
		return name;
	}

	@XmlElement(name = "gradedArea")
	public int getGradedArea()
	{
		return gradedArea;
	}

	@XmlElement(name = "leftBearing")
	public int getLeftBearing() {
		return this.leftBearing;
	}


	@XmlElement(name = "runL")
	public RunwayOneWay getRunL()
	{
		return RunL;
	}

	@XmlElement(name = "runR")
	public RunwayOneWay getRunR()
	{
		return RunR;
	}

	@XmlElement(name = "rightBearing")
	public int getRightBearing() {
		return rightBearing;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setGradedArea(int gradedArea) {
		this.gradedArea = gradedArea;
	}

	public void setLeftBearing(int leftBearing) {
		this.leftBearing = leftBearing;
	}

	public void setRightBearing(int rightBearing) {
		this.rightBearing = rightBearing;
	}

	public void setRunL(RunwayOneWay runL) {
		this.RunL = runL;
	}

	public void setRunR(RunwayOneWay runR) {
		this.RunR = runR;
	}
}
