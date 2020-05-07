import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class BackendIntegrationTests
{
//    @Test
//    public void addObstacleToRunway()
//    {
//        Airport ap1 = null;
//        ArrayList<Runway> runwayList = new ArrayList<Runway>();
//        try {
//            runwayList.add(new Runway(9, new RunwayData(306, 3902, 3902, 3902, 3595), new RunwayData(0, 3884, 3962, 3884, 3884)));
//            ap1 = new Airport(runwayList);
//        } catch (Exception e2) {}
//
//        List<ObstacleData> ods = null;
//        try {
//            ap1.addObstacle(new ObstacleData(-1, 2), "09L/27R", true);
//            ods = ap1.getRunway("09L").getObstacles();
//        } catch (Exception e1) {}
//
//        ObstacleData od = null;
//        try {
//            od = ods.get(0);
//        } catch (Exception e) {
//            assertTrue(false);
//        }
//        assertEquals(-1, od.position);
//        assertEquals(2, od.maxHeight);
//    }
//
//    @Test
//    public void reCalculateFromAirport()
//    {
//        Airport ap1 = null;
//        Airport.RESA = 240;
//        Airport.StripEnd = 60;
//        Airport.MinSlope = 50;
//        Airport.BlastAllowance = 300;
//        ArrayList<Runway> runwayList = new ArrayList<Runway>();
//        try {
//            runwayList.add(new Runway(9, new RunwayData(306, 3902, 3902, 3902, 3595), new RunwayData(0, 3884, 3962, 3884, 3884)));
//            ap1 = new Airport(runwayList);
//            ap1.addObstacle(new ObstacleData(3646, 12), "09L/27R", false);
//            assertTrue(BackendHelpers.runwayDataSame(ap1.getRunway("27R").getUpdatedRunway(), new RunwayData(0, 2986, 2986, 2986, 3346)));
//            assertTrue(BackendHelpers.runwayDataSame(ap1.getRunway("09L").getUpdatedRunway(), new RunwayData(1222, 3040, 3040, 3040, 2679)));
//        } catch (Exception e2) { assertTrue(false); }
//    }
//
//    @Test
//    public void TestLateRunwayAdd()
//    {
//        List<Runway> runwayList = new ArrayList<Runway>();
//        runwayList.add(new Runway(9, new RunwayData(306, 3902, 3902, 3902, 3595), new RunwayData(0, 3884, 3962, 3884, 3884)));
//        Airport ap1 = new Airport(runwayList);
//
//        runwayList = new ArrayList<Runway>();
//        runwayList.add(new Runway(13, new RunwayData(306, 3200, 3200, 3200, 3200), new RunwayData(0, 3100, 3100, 3100, 3100)));
//
//        assertFalse(ap1.getRunwayFull("09L/27R") == null);
//        ap1.addRunways(runwayList);
//        assertFalse(ap1.getRunwayFull("13L/31R") == null);
//        assertFalse(ap1.getRunwayFull("09L/27R") == null);
//    }
}