import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.*;

public class RunwayOneWayTests
{
    private boolean runwayDataEqual(RunwayData a, RunwayData b)
    {
        return  (a.threshold == b.threshold) && (a.takeoffThreshold == b.takeoffThreshold) && 
                (a.stopway == b.stopway) && (a.clearway == b.clearway) && (a.TORA == b.TORA) && 
                (a.TODA == b.TODA) && (a.ASDA == b.ASDA) && (a.LDA == b.LDA);
    }

    @Test
    public void createRunwayBoundary()
    {
        try {
            ObstacleData od = new ObstacleData(100, 20);
            RunwayOneWay rw = new RunwayOneWay("TEST", new RunwayData(100, 1000, 100, 100));
            assertTrue(rw.getName().equals("TEST"));
            assertTrue(runwayDataEqual(rw.getRunwaySpec(), new RunwayData(100, 1000, 100, 100)));
            assertTrue(runwayDataEqual(rw.getRunwaySpec(), rw.getUpdatedRunway()));
            rw.addObstacle(od);
            assertFalse(runwayDataEqual(rw.getRunwaySpec(), rw.getUpdatedRunway()));
            assertTrue(rw.getObstacles().get(0) == od);
        } catch (Exception e) {
            assertTrue(false);
        }
    }
}
