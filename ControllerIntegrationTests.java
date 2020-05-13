import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ControllerIntegrationTests
{
    @Test
    public void addObstacleToRunway()
    {
        Airport ap1 = null;
        ArrayList<Runway> runwayList = new ArrayList<Runway>();
        try {
        	ap1 = new Airport(240, 60, 300, 50);
        	ap1.addRunway(new Runway(9, new RunwayData(306, 3902, 3902, 3902, 3595), new RunwayData(0, 3884, 3962, 3884, 3884)));
        } catch (Exception e2) {}

        List<ObstacleData> ods = null;
        
        try {
        	ap1.addObstacle(2, -1, "09L/27R", true);
            ods = ap1.getRunway("09L").getImpactfulObstacles();
        } catch (Exception e1) {}

        ObstacleData od = null;
        try {
            od = ods.get(0);
        } catch (Exception e) {
            assertTrue(false);
        }
        assertEquals(2, od.maxHeight);
    }

    @Test
    public void initialiseAirport()
    {
        Airport ap = InputScreenController.initialiseAirport(240, 60, 300, 50);
        ArrayList<Runway> runwayList = new ArrayList<Runway>();
        assertFalse(ap == null);
        assertTrue(ap.RESA == 240);
        assertTrue(ap.StripEnd == 60);
        assertTrue(ap.BlastAllowance == 300);
        assertTrue(ap.MinSlope == 50);
    }

    @Test
    public void recalculateWithGeneratedRunwayData()
    {
        Airport ap = InputScreenController.initialiseAirport(240, 60, 300, 50);
        ArrayList<Runway> runwayList = new ArrayList<Runway>();
        assertFalse(ap == null);
        try {
            runwayList.add(new Runway(2, InputScreenController.initRunwayData(306, 3902, 0, 0), InputScreenController.initRunwayData(0, 3884, 0, 78)));
            ap = InputScreenController.addRunwaysToAirport(ap, runwayList);
            ap.addObstacle(12, 3000, "02L/20R", false);
            assertFalse(BackendHelpers.runwayDataSame(ap.getRunway("20R").getUpdatedRunway(), ap.getRunway("20R").getRunwaySpec()));
            assertFalse(BackendHelpers.runwayDataSame(ap.getRunway("02L").getUpdatedRunway(), ap.getRunway("02L").getRunwaySpec()));
        } catch (Exception e2) {
	    	System.out.println(e2.getMessage());
	    	assertTrue(false);
		}
    }

    @Test
    public void recalculateWithAddedRunways()
    {
        Airport ap = InputScreenController.initialiseAirport(240, 60, 300, 50);
        ArrayList<Runway> runwayList = new ArrayList<Runway>();
        assertFalse(ap == null);
        try {
            runwayList.add(new Runway(2, new RunwayData(306, 3902, 3902, 3902, 3595), new RunwayData(0, 3884, 3962, 3884, 3884)));
            ap = InputScreenController.addRunwaysToAirport(ap, runwayList);
            ap.addObstacle(12, 2000, "02L/20R", false);
            assertFalse(BackendHelpers.runwayDataSame(ap.getRunway("20R").getUpdatedRunway(), ap.getRunway("20R").getRunwaySpec()));
            assertFalse(BackendHelpers.runwayDataSame(ap.getRunway("02L").getUpdatedRunway(), ap.getRunway("02L").getRunwaySpec()));
        } catch (Exception e2) {
        	System.out.println(e2.getMessage());
        	assertTrue(false);
    	}
    }
}