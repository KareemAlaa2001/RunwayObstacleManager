import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.*;

public class FunctionsTests 
{ /*
	@Test
	public void boundaries()
	{
		boolean nullRun = false;
		boolean nullObst = false;
		try {
			Functions.reCalculate(null, new ObstacleData(100, 100));
		} catch (Exception e) {
			nullRun = true;
		}
		try {
			Functions.reCalculate(new RunwayData(100, 100, 100, 100), null);
		} catch (Exception e) {
			nullObst = true;
		}
	}

	// Testing User Story: As an analyst I want the system to calculate the new runway distances available when one obstacle is present,
	// given the obstacleÃ¢â‚¬â„¢s distances from each threshold, distance from the centreline and height
    @Test
    public void recalculateTowardObstacle()
    {
        try {
        	Airport ap = new Airport(new ArrayList<Runway>());
        	assertTrue(	BackendHelpers.runwayDataSame(Functions.reCalculate(new RunwayData(0, 3884, 3962, 3884, 3884), new ObstacleData(3646, 12)),
        				new RunwayData(0, 2986, 2986, 2986, 3346)));

        	assertTrue(	BackendHelpers.runwayDataSame(Functions.reCalculate(new RunwayData(307, 3660, 3660, 3660, 3353), new ObstacleData(2853, 25)),
        				new RunwayData(0, 1850, 1850, 1850, 2553)));

        	assertTrue(	BackendHelpers.runwayDataSame(Functions.reCalculate(new RunwayData(0, 3660, 3660, 3660, 3660), new ObstacleData(3203, 15)),
        				new RunwayData(0, 2393, 2393, 2393, 2903)));

        	assertTrue(	BackendHelpers.runwayDataSame(Functions.reCalculate(new RunwayData(306, 3902, 3902, 3902, 3595), new ObstacleData(3546, 20)),
        				new RunwayData(0, 2792, 2792, 2792, 3246)));
		} catch (Exception e) {}
    }

    @Test
    public void recalculateAwayFromObstacle()
    {
        try {
	    	Airport ap = new Airport(new ArrayList<Runway>());
	    	assertTrue(	BackendHelpers.runwayDataSame(Functions.reCalculate(new RunwayData(306, 3902, 3902, 3902, 3595), new ObstacleData(-50, 12)),
	    				new RunwayData(0, 3346, 3346, 3346, 2985)));

	    	assertTrue(	BackendHelpers.runwayDataSame(Functions.reCalculate(new RunwayData(0, 3660, 3660, 3660, 3660), new ObstacleData(500, 25)),
	    				new RunwayData(0, 2860, 2860, 2860, 1850)));

	    	assertTrue(	BackendHelpers.runwayDataSame(Functions.reCalculate(new RunwayData(307, 3660, 3660, 3660, 3353), new ObstacleData(150, 15)),
	    				new RunwayData(0, 2903, 2903, 2903, 2393)));

	    	assertTrue(	BackendHelpers.runwayDataSame(Functions.reCalculate(new RunwayData(0, 3884, 3962, 3884, 3884), new ObstacleData(50, 20)),
	    				new RunwayData(0, 3534, 3612, 3534, 2774)));
        } catch (Exception e) {}
    } */
}