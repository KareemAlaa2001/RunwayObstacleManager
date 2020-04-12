import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.*;

public class FunctionsTests 
{
	private List<Runway> generateTestRunways()
	{
    	ArrayList<Runway> runwayList = new ArrayList<Runway>();
        Runway r1 = null;
        Runway r2 = null;;
		try {
			r1 = new Runway(9, new RunwayData(306, 3902, 3902, 3902, 3595), new RunwayData(0, 3884, 3962, 3884, 3884));
			r2 = new Runway(27, new RunwayData(0, 3660, 3660, 3660, 3660), new RunwayData(307, 3660, 3660, 3660, 3353));
		} catch (Exception e) {}
		
        runwayList.add(r1);
        runwayList.add(r2);
        
		return runwayList;
	}

	private boolean runwayDataSame(RunwayData a, RunwayData b)
	{
		return (a.TORA == b.TORA) && (a.TODA == b.TODA) && (a.ASDA == b.ASDA) && (a.LDA == b.LDA);
	}
	
	// Testing User Story: As an analyst I want the system to calculate the new runway distances available when one obstacle is present, 
	// given the obstacle’s distances from each threshold, distance from the centreline and height
    @Test
    public void recalculateTowardObstacle()
    {
        try {
        	Airport ap1 = new Airport(generateTestRunways());
			ap1.addObstacle(new ObstacleData(3646, 12), "09L/27R", false);
	        ap1.addObstacle(new ObstacleData(2853, 25), "27L/09R", false);
	
	        Airport ap2 = new Airport(generateTestRunways());
	        ap2.addObstacle(new ObstacleData(3203, 15), "27L/09R", true);
	        ap2.addObstacle(new ObstacleData(3546, 20), "09L/27R", true);
	
	        assertTrue(runwayDataSame(ap1.getRunway("27R").getUpdatedRunway(), new RunwayData(0, 2986, 2986, 2986, 3346)));
	        assertTrue(runwayDataSame(ap1.getRunway("09R").getUpdatedRunway(), new RunwayData(0, 1850, 1850, 1850, 2553)));
	
	        assertTrue(runwayDataSame(ap2.getRunway("27L").getUpdatedRunway(), new RunwayData(0, 2393, 2393, 2393, 2903)));
	        assertTrue(runwayDataSame(ap2.getRunway("09L").getUpdatedRunway(), new RunwayData(0, 2792, 2792, 2792, 3246)));
		} catch (Exception e) {}
    }

    @Test
    public void recalculateAwayFromObstacle()
    {
        try {
	    	Airport ap1 = new Airport(generateTestRunways());
	        ap1.addObstacle(new ObstacleData(-50, 12), "09L/27R", true);
	        ap1.addObstacle(new ObstacleData(500, 25), "27L/09R", true);
	        
	        Airport ap2 = new Airport(generateTestRunways());
	        ap2.addObstacle(new ObstacleData(150, 15), "27L/09R", false);
	        ap2.addObstacle(new ObstacleData(50, 20), "09L/27R", false);
	
	        assertTrue(runwayDataSame(ap1.getRunway("09L").getUpdatedRunway(), new RunwayData(0, 3346, 3346, 3346, 2985)));
	        assertTrue(runwayDataSame(ap1.getRunway("27L").getUpdatedRunway(), new RunwayData(0, 2860, 2860, 2860, 1850)));
	
	        assertTrue(runwayDataSame(ap2.getRunway("09R").getUpdatedRunway(), new RunwayData(0, 2903, 2903, 2903, 2393)));
	        assertTrue(runwayDataSame(ap2.getRunway("27R").getUpdatedRunway(), new RunwayData(0, 3534, 3612, 3534, 2774)));
        } catch (Exception e) {}
    }
}