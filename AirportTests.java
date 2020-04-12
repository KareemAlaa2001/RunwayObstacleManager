import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.*;

public class AirportTests
{
    @Test
    public void addRetreiveRunways()
    {
    	Airport ap1 = null;
        ArrayList<Runway> runwayList = new ArrayList<Runway>();
        try {
            runwayList.add(new Runway(9, new RunwayData(306, 3902, 3902, 3902, 3595), new RunwayData(0, 3884, 3962, 3884, 3884)));
            ap1 = new Airport(runwayList);
        } catch (Exception e) {}

        RunwayOneWay rw = null;
        try {
            rw = ap1.getRunway("09L");
            assertFalse(rw == null);
            rw = null;
            rw = ap1.getRunway("27R");
            assertFalse(rw == null);
        } catch (Exception e) {}
    }

    @Test
    public void addObstacleToRunway()
    {
    	Airport ap1 = null;
        ArrayList<Runway> runwayList = new ArrayList<Runway>();
        try {
			runwayList.add(new Runway(9, new RunwayData(306, 3902, 3902, 3902, 3595), new RunwayData(0, 3884, 3962, 3884, 3884)));
	        ap1 = new Airport(runwayList);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

        List<ObstacleData> ods = null;
        try {
			ap1.addObstacle(new ObstacleData(-1, 2), "09L/27R", true);
			ods = ap1.getRunway("09L").getObstacles();
		} catch (Exception e1) {}
        
        ObstacleData od = null;
        try {
            od = ods.get(0);
        } catch (Exception e) {
            od = new ObstacleData(10, 10);
        }
        assertEquals(-1, od.position);
        assertEquals(2, od.maxHeight);
    }
}
