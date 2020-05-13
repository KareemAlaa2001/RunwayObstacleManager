import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class MathsHelpersTests
{
    @Test
    public void calculatePositionTest()
    {
		Point result = MathsHelpers.calculatePosition(1, 1000, 60, new Point(10,10));
		assertTrue(result.x == 243 && result.y == 985);
		result = MathsHelpers.calculatePosition(8, 1000, 60, new Point(10,10));
		assertTrue(result.x == 1005 && result.y == 125);
		
		result = MathsHelpers.calculatePosition(10, 1000, 60, new Point(10,10));
		assertTrue(result.x == 985 && result.y == -223);
		result = MathsHelpers.calculatePosition(17, 1000, 60, new Point(10,10));
		assertTrue(result.x == 125 && result.y == -985);
		
		result = MathsHelpers.calculatePosition(19, 1000, 60, new Point(10,10));
		assertTrue(result.x == -223 && result.y == -965);
		result = MathsHelpers.calculatePosition(26, 1000, 60, new Point(10,10));
		assertTrue(result.x == -985 && result.y == -105);
		
		result = MathsHelpers.calculatePosition(28, 1000, 60, new Point(10,10));
		assertTrue(result.x == -965 && result.y == 243);
		result = MathsHelpers.calculatePosition(35, 1000, 60, new Point(10,10));
		assertTrue(result.x == -105 && result.y == 1005);
    }
    
    @Test
    public void calculateCentrelineDistanceTest()
    {
		int result1 = MathsHelpers.getDistanceFromCentreLine(new ObstacleData(new Point(1000, 900), 10, 0), new Point(0, 0), new Point(3000, 2500));
		int result2 = MathsHelpers.getDistanceFromCentreLine(new ObstacleData(new Point(-1000, 900), 10, 0), new Point(0, 0), new Point(-3000, 2500));
		int result3 = MathsHelpers.getDistanceFromCentreLine(new ObstacleData(new Point(1000, -900), 10, 0), new Point(0, 0), new Point(3000, -2500));
		int result4 = MathsHelpers.getDistanceFromCentreLine(new ObstacleData(new Point(-1000, -900), 10, 0), new Point(0, 0), new Point(-3000, -2500));

		assertTrue(result1  == 51);
		assertTrue(result2  == 51);
		assertTrue(result3  == 51);
		assertTrue(result4  == 51);
    }
}
