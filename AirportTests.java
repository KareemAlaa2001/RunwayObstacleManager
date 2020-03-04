import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.*;

public class AirportTests
{
	@Test
    public void addRetreiveRunways()
    {
    	ArrayList<Runway> runwayList = new ArrayList<Runway>();
        Runway r1 = new Runway(9, new RunwayData(306, 3902, 3902, 3902, 3595), new RunwayData(0, 3884, 3962, 3884, 3884));
        Airport ap1 = new Airport(runwayList);

        RunwayOneWay rw = null;
        rw = ap1.getRunway("09L");
        AssertFalse(rw == null);

        RunwayOneWay rw = null;
        rw = ap1.getRunway("27R");
        AssertFalse(rw == null);
    }

	@Test
    public void addObstacleToRunway()
    {
    	ArrayList<Runway> runwayList = new ArrayList<Runway>();
        Runway r1 = new Runway(9, new RunwayData(306, 3902, 3902, 3902, 3595), new RunwayData(0, 3884, 3962, 3884, 3884));
        Airport ap1 = new Airport(runwayList);

        ap1.addObstacle(new ObstacleData(-1, 2), "09L");
        List<ObstacleData> ods = ap1.getRunway("09L").getObstacles();
        ObstacleData od;
        try {
            od = ods.get(0);
        } catch (Exception e) {
            od = new ObstacleData(10, 10);
        }

        AssertEquals(od.position == -1);
        AssertEquals(od.maxHeight == 2);
    }
}
