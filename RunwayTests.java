import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class RunwayTests
{
    @Test
    public void createRunwayBoundary()
    {
        try {
            Runway run = new Runway(0, 0, new RunwayData(0, 0, 0, 0), new RunwayData(0, 0, 0, 0));
            assertTrue(run.getName().equals("00L/18R"));
            assertTrue(run.getGradedArea() == 0);
            run = new Runway(0, new RunwayData(0, 0, 0, 0), new RunwayData(0, 0, 0, 0));
            assertTrue(run.getName().equals("00L/18R"));
            assertTrue(run.getGradedArea() == 400);
        } catch (Exception e) {}

        boolean bearing = false;
        boolean leftNull = false;
        boolean rightNull = false;
        boolean graded = false;

        try {
            Runway run = new Runway(-1, 0, new RunwayData(0, 0, 0, 0), new RunwayData(0, 0, 0, 0));
        } catch(Exception e) {
            try {
                Runway run = new Runway(-1, new RunwayData(0, 0, 0, 0), new RunwayData(0, 0, 0, 0));
            } catch(Exception ex) {
                bearing = true;
            }
        }
        try {
            Runway run = new Runway(0, 0, null, new RunwayData(0, 0, 0, 0));
        } catch(Exception e) {
            try {
                Runway run = new Runway(0, null, new RunwayData(0, 0, 0, 0));
            } catch(Exception ex) {
                leftNull = true;
            }
        }
        try {
            Runway run = new Runway(0, 0, new RunwayData(0, 0, 0, 0), null);
        } catch(Exception e) {
            try {
                Runway run = new Runway(0, new RunwayData(0, 0, 0, 0), null);
            } catch(Exception ex) {
                rightNull = true;
            }
        }
        try {
            Runway run = new Runway(0, -1, new RunwayData(0, 0, 0, 0), new RunwayData(0, 0, 0, 0));
        } catch(Exception e) {
            graded = true;
        }
        assertTrue(bearing);
        assertTrue(leftNull);
        assertTrue(rightNull);
        assertTrue(graded);
    }

    @Test
    public void testRunwayNamingConventions()
    {
        Runway rw = null;
        Runway rw2 = null;
        Runway rw3 = null;

        try {
            rw = new Runway(33, new RunwayData(306, 3902, 3902, 3902, 3595), new RunwayData(0, 3884, 3962, 3884, 3884));
        } catch (Exception e) {}

        assertTrue(rw.getRunL().getName().equals("33L"));
        assertTrue(rw.getRunR().getName().equals("15R"));
        assertTrue(rw.getName().equals("33L/15R"));

        try {
            rw2 = new Runway(1, new RunwayData(306, 3902, 3902, 3902, 3595), new RunwayData(0, 3884, 3962, 3884, 3884));
        } catch (Exception e) {}

        assertTrue(rw2.getRunL().getName().equals("01L"));
        assertTrue(rw2.getRunR().getName().equals("19R"));
        assertTrue(rw2.getName().equals("01L/19R"));

        try {
            rw3 = new Runway(13, new RunwayData(306, 3902, 3902, 3902, 3595), new RunwayData(0, 3884, 3962, 3884, 3884));
        } catch (Exception e) {}

        assertTrue(rw3.getRunL().getName().equals("13L"));
        assertTrue(rw3.getRunR().getName().equals("31R"));
        assertTrue(rw3.getName().equals("13L/31R"));
    }

    @Test
    public void testAddingObstacles()
    {
        try {
            Runway rw = null;
            rw = new Runway(0, new RunwayData(306, 3902, 3902, 3902, 3595), new RunwayData(0, 3884, 3962, 3884, 3884));
            ObstacleData l = new ObstacleData(new Point(406, 0), 45, 0);
            ObstacleData r = new ObstacleData(new Point(286, 0), 13, 0);
            rw.checkObstacle(l, new Point(0, 0));
            rw.checkObstacle(r, new Point(0, 0));

            assertTrue(BackendHelpers.obstacleEqual(rw.getRunL().getImpactfulObstacles().get(0), l));
            assertTrue(rw.getRunL().getImpactfulObstacles().size() == 2);
            assertTrue(BackendHelpers.obstacleEqual(rw.getRunR().getImpactfulObstacles().get(1), r));
            assertTrue(rw.getRunR().getImpactfulObstacles().size() == 2);
        } catch (Exception e) {}
    }
}