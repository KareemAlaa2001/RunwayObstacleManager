import java.lang.Math;

public class MathsHelpers
{
	public static int[] calculatePosition(int bearing, int distance, int distanceFromCentre, int[] start)
	{ // Nice wrapper for generating the position of an obstacle given the input that we previously got
		double angle = Math.toRadians((double)bearing * 10) + Math.atan(distanceFromCentre/distance);
		return calculatePositionInner(angle, distance, start);
	}

	public static int[] calculatePositionInner(double angle, int distance, int[] start)
	{ // Generates a new set of coordinates, could be converted to polar equations
		if (angle < 0.5 * Math.PI) {
			return new int[] { (int)Math.round(start[0] + Math.sin(angle) * distance), (int)Math.round(start[1] + Math.cos(angle) * distance) };
		} else if (angle < Math.PI) {
			angle -= 0.5 * Math.PI;
			return new int[] { (int)Math.round(start[0] + Math.sin(angle) * distance), (int)Math.round(start[1] - Math.cos(angle) * distance) };
		} else if (angle < 1.5 * Math.PI) {
			angle -= Math.PI;
			return new int[] { (int)Math.round(start[0] - Math.sin(angle) * distance), (int)Math.round(start[1] - Math.cos(angle) * distance) };
		} else {
			angle -= 1.5 * Math.PI;
			return new int[] { (int)Math.round(start[0] - Math.sin(angle) * distance), (int)Math.round(start[1] + Math.cos(angle) * distance) };
		}
	}

	public static int[] calculatePositionInner(int bearing, int distance, int[] start)
	{ // Generates a new set of coordinates, could be converted to polar equations
		double angle = Math.toRadians((double)bearing * 10);
		if (angle < 0.5 * Math.PI) {
			return new int[] { (int)Math.round(start[0] + Math.sin(angle) * distance), (int)Math.round(start[1] + Math.cos(angle) * distance) };
		} else if (angle < Math.PI) {
			angle -= 0.5 * Math.PI;
			return new int[] { (int)Math.round(start[0] + Math.sin(angle) * distance), (int)Math.round(start[1] - Math.cos(angle) * distance) };
		} else if (angle < 1.5 * Math.PI) {
			angle -= Math.PI;
			return new int[] { (int)Math.round(start[0] - Math.sin(angle) * distance), (int)Math.round(start[1] - Math.cos(angle) * distance) };
		} else {
			angle -= 1.5 * Math.PI;
			return new int[] { (int)Math.round(start[0] - Math.sin(angle) * distance), (int)Math.round(start[1] + Math.cos(angle) * distance) };
		}
	}

	public static int getDistanceFromCentreLine(ObstacleData od, int[] leftStart, int[] rightStart)
	{ // Scalene triangle using the area generated from Heron's formula
		double lineA = calculateDistance(leftStart, rightStart);
		double lineB = calculateDistance(leftStart, od.position);
		double lineC = calculateDistance(od.position, rightStart);
		double sPerimeter = 0.5 * (lineA + lineB + lineC);
		double area = Math.sqrt(sPerimeter * (sPerimeter - lineA) * (sPerimeter - lineB) * (sPerimeter - lineC));
		return (int)Math.round(2 * area * lineA);
	}

	public static int calculateDistance(ObstacleData od, int bearing, int TORA, int[] leftStart)
	{ // Basically just pythag
		return (int)Math.round(Math.sqrt(Math.pow(calculateDistance(leftStart, od.position), 2) - Math.pow(getDistanceFromCentreLine(od, leftStart, calculateOtherStart(TORA, bearing, leftStart)), 2)));
	}

	public static int[] calculateOtherStart(int TORA, int bearing, int[] start)
	{ // yes this is just a wrapper but hopes to make code more readable
		return calculatePositionInner(bearing, TORA, start);
	}

	private static double calculateDistance(int[] a, int[] b)
	{ // Distance between 2 points
		return Math.sqrt(Math.pow(b[0] - a[0], 2) + Math.pow(b[1] - a[1], 2));
	}
}
