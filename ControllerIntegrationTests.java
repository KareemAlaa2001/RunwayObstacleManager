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
            runwayList.add(new Runway(9, new RunwayData(306, 3902, 3902, 3902, 3595), new RunwayData(0, 3884, 3962, 3884, 3884)));
            ap1 = new Airport(runwayList);
        } catch (Exception e2) {}

        List<ObstacleData> ods = null;
        try {
            ap1.addObstacle(new ObstacleData(-1, 2), "09L/27R", true);
            ods = ap1.getRunway("09L").getObstacles();
        } catch (Exception e1) {}
        
        ObstacleData od = null;
        try {
            od = ods.get(0);
        } catch (Exception e) {
            assertTrue(false);
        }
        assertEquals(-1, od.position);
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
        Airport ap = InputScreenController.initialiseAirport(240, 60, 50, 300);
        ArrayList<Runway> runwayList = new ArrayList<Runway>();
        assertFalse(ap == null);
        try {
            runwayList.add(new Runway(9, InputScreenController.initRunwayData(306, 3902, 0, 0), InputScreenController.initRunwayData(0, 3884, 0, 78)));
            ap = InputScreenController.addRunwaysToAirport(ap, runwayList);
            ap.addObstacle(new ObstacleData(3646, 12), "09L/27R", false);
            assertTrue(ap.getRunway("27R").getUpdatedRunway() != ap.getRunway("27R").getRunwaySpec());
            assertTrue(ap.getRunway("09L").getUpdatedRunway() != ap.getRunway("09L").getRunwaySpec());
        } catch (Exception e2) { assertTrue(false); }
    }

    @Test
    public void recalculateWithAddedRunways()
    {
        Airport ap = InputScreenController.initialiseAirport(240, 60, 50, 300);
        ArrayList<Runway> runwayList = new ArrayList<Runway>();
        assertFalse(ap == null);
        try {
            runwayList.add(new Runway(9, new RunwayData(306, 3902, 3902, 3902, 3595), new RunwayData(0, 3884, 3962, 3884, 3884)));
            ap = InputScreenController.addRunwaysToAirport(ap, runwayList);
            ap.addObstacle(new ObstacleData(3646, 12), "09L/27R", false);
            assertTrue(ap.getRunway("27R").getUpdatedRunway() != ap.getRunway("27R").getRunwaySpec());
            assertTrue(ap.getRunway("09L").getUpdatedRunway() != ap.getRunway("09L").getRunwaySpec());
        } catch (Exception e2) { assertTrue(false); }
    }
}