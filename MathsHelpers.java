import java.lang.Math;


public class MathsHelpers
{
	public static void main(String[] args)
	{ // For debugging, will be turned into unit test
		/*
		Point result = calculatePosition(1, 1000, 60, new Point(10,10));
		System.out.println("Out: " + result.x + ", " + result.y);
		*/

		int result = getDistanceFromCentreLine(new ObstacleData(new Point(1000, 900), 10), new Point(0, 0), new Point(3000, 2500));
		System.out.println(result);
		
	}

	public static Point calculatePosition(int bearing, int distance, int distanceFromCentre, Point start)
	{ // Nice wrapper for generating the position of an obstacle given the input that we previously got
		double angle = Math.toRadians((double)bearing * 10) + Math.atan((double)distanceFromCentre/(double)distance);
		return calculatePositionInner(angle, getHypotenuse(distance, distanceFromCentre), start);
	}

	public static Point calculatePositionInner(double angle, int distance, Point start)
	{ // Generates a new set of coordinates, could be converted to polar equations

		if (angle <= 0.5 * Math.PI) {
			return new Point((int)Math.round(start.x + Math.sin(angle) * distance), (int)Math.round(start.y + Math.cos(angle) * distance));
		} else if (angle <= Math.PI) {
			angle -= 0.5 * Math.PI;
			return new Point((int)Math.round(start.x + Math.cos(angle) * distance), (int)Math.round(start.y - Math.sin(angle) * distance));
		} else if (angle <= 1.5 * Math.PI) {
			angle -= Math.PI;
			return new Point((int)Math.round(start.x - Math.sin(angle) * distance), (int)Math.round(start.y - Math.cos(angle) * distance));
		} else {
			angle -= 1.5 * Math.PI;
			return new Point((int)Math.round(start.x - Math.cos(angle) * distance), (int)Math.round(start.y + Math.sin(angle) * distance));
		}
	}

	public static Point calculatePositionInner(int bearing, int distance, Point start)
	{ // Epic wrapper lmao
		return calculatePositionInner(Math.toRadians((double)bearing * 10), distance, start);
	}

	public static int getDistanceFromCentreLine(ObstacleData od, Point leftStart, Point rightStart)
	{ // Scalene triangle using the area generated from Heron's formula
		// System.out.println("Start: " + leftStart.x + ", " + leftStart.y + " End: " + rightStart.x + ", " + rightStart.y); // Important debugging line do not delete
		double lineA = calculateDistance(leftStart, rightStart);
		double lineB = calculateDistance(leftStart, od.position);
		double lineC = calculateDistance(od.position, rightStart);
		double sPerimeter = 0.5 * (lineA + lineB + lineC);
		double area = Math.sqrt(sPerimeter * (sPerimeter - lineA) * (sPerimeter - lineB) * (sPerimeter - lineC));
		return (int)Math.round((2 * area) / lineA);
	}

	public static Boolean linesintersect(Point startA, int bearingA, int distanceA, Point startB, int bearingB, int distanceB)
	{
		Point endA = calculatePositionInner(bearingA, distanceA, startA);
		Point endB = calculatePositionInner(bearingB, distanceB, startB);

		return intersect(startA, endA, startB, endB);
	}

	private static Boolean onLine(Point startA, Point endA, Point p)
	{
		return p.y == ((endA.y - startA.y) / (endA.x - startA.x)) * p.x;
	}

	// https://www.tutorialspoint.com/Check-if-two-line-segments-intersect
	private static Boolean intersect(Point startA, Point endA, Point startB, Point endB)
	{
	   int dir1 = direction(startA, endA, startB);
	   int dir2 = direction(startA, endA, endB);
	   int dir3 = direction(startB, endB, startA);
	   int dir4 = direction(startB, endB, endA);

	   if (dir1 != dir2 && dir3 != dir4) {
	      return true;
	   } if (dir1 == 0 && onLine(startA, endA, startB))  {
	      return true;
	   } if (dir2 == 0 && onLine(startA, endA, endB)) {
	      return true;
	   } if (dir3 == 0 && onLine(startB, endB, startA)) {
	      return true;
	   } if (dir4 == 0 && onLine(startB, endB, endA)) {
	      return true;
	   }
	   return false;
	}

	// https://www.tutorialspoint.com/Check-if-two-line-segments-intersect
	// Magic number return:  0 -> colinear, 1-> anti-clockwise, 2 -> clockwise
	private static int direction(Point a, Point b, Point c)
	{
		double val = (b.y - a.y) * (c.x - b.x) - (b.x - a.x) * (c.y - b.y);
		if (val == 0) {
		    return 0;
		} else if (val < 0) {
		    return 1;
		} else {
			return 2;
		}
	}

	public static int calculateDistance(ObstacleData od, int bearing, int TORA, Point leftStart)
	{ // Basically just pythag
		return (int)Math.round(Math.sqrt(Math.pow(calculateDistance(leftStart, od.position), 2) - Math.pow(getDistanceFromCentreLine(od, leftStart, calculateOtherStart(TORA, bearing, leftStart)), 2)));
	}

	public static Point calculateOtherStart(int TORA, int bearing, Point start)
	{ // yes this is just a wrapper but hopes to make code more readable
		return calculatePositionInner(bearing, TORA, start);
	}

	private static double calculateDistance(Point a, Point b)
	{ // Distance between 2 points
		return Math.sqrt(Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2));
	}

	private static int getHypotenuse(int a, int b)
	{
		return (int)Math.round(Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2)));
	}

	public static Point calculatePositionFromIntersection(Point runAStart, Runway runA, int runAIntersection, Runway runB, int runBIntersection)
	{
		Point intersectionPoint = calculateOtherStart(runAIntersection, runA.getLeftBearing(), runAStart);
		return calculateOtherStart(runBIntersection, runB.getRightBearing(), intersectionPoint);
	}
}
