import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.*;

public class RunwayOneWayTests
{
    @Test
    public void createRunwayBoundary()
    {
        try {
            ObstacleData od = new ObstacleData(new Point(500, 1), 20, 0);
            RunwayOneWay rw = new RunwayOneWay("TEST", new RunwayData(100, 4000, 100, 100));
            assertTrue(rw.getName().equals("TEST"));
            assertTrue(BackendHelpers.runwayDataEqual(rw.getRunwaySpec(), new RunwayData(100, 4000, 100, 100)));
            assertTrue(BackendHelpers.runwayDataEqual(rw.getRunwaySpec(), rw.getUpdatedRunway()));
            rw.addObstacle(0, od, 9, new  Point(0, 0));
            assertFalse(BackendHelpers.runwayDataEqual(rw.getRunwaySpec(), rw.getUpdatedRunway()));
            assertTrue(rw.getImpactfulObstacles().get(0) == od);
        } catch (Exception e) {
            assertTrue(false);
        }
    }
}
