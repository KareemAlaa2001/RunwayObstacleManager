import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class BackendIntegrationTests
{
    @Test
    public void addObstacleToRunway()
    {
        Airport ap1 = null;
        ArrayList<Runway> runwayList = new ArrayList<Runway>();
        
    	ap1 = new Airport(240, 60, 300, 50);
    	ap1.addRunway(new Runway(9, new RunwayData(306, 3902, 3902, 3902, 3595), new RunwayData(0, 3884, 3962, 3884, 3884)));

        List<ObstacleData> ods = null;
        try {
            ap1.addObstacle(2, 1, "09L/27R", true);
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
    public void reCalculateFromAirport()
    {
        try {
        	Airport ap1 = new Airport(240, 60, 300, 50);
        	ap1.addRunway(new Runway(9, new RunwayData(306, 3902, 3902, 3902, 3595), new RunwayData(0, 3884, 3962, 3884, 3884)));
            ap1.addObstacle(12, 3646, "09L/27R", false);
            assertFalse(BackendHelpers.runwayDataSame(ap1.getRunway("27R").getUpdatedRunway(), new RunwayData(0, 2986, 2986, 2986, 3346)));
            assertFalse(BackendHelpers.runwayDataSame(ap1.getRunway("09L").getUpdatedRunway(), new RunwayData(1222, 3040, 3040, 3040, 2679)));
        } catch (Exception e2) { assertTrue(false); }
    }

    @Test
    public void TestLateRunwayAdd()
    {
        Airport ap1 = new Airport(240, 60, 300, 50);
    	ap1.addRunway(new Runway(9, new RunwayData(306, 3902, 3902, 3902, 3595), new RunwayData(0, 3884, 3962, 3884, 3884)));

        assertFalse(ap1.getRunwayFull("09L/27R") == null);
        ap1.addRunway(new Runway(13, new RunwayData(306, 3200, 3200, 3200, 3200), new RunwayData(0, 3100, 3100, 3100, 3100)));
        assertFalse(ap1.getRunwayFull("13L/31R") == null);
        assertFalse(ap1.getRunwayFull("09L/27R") == null);
    }
}