import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.*;

public class AirportTests
{ /*
    @Test
    public void createAirportFromRunways()
    {
        boolean nulll = false;
        boolean none = false;
        try {
            Airport a = new Airport(null);
        } catch (Exception e) {
            nulll = true;
        }
        try {
            Airport a = new Airport(new ArrayList<Runway>());
        } catch (Exception e) {
            none = true;
        }
        assertTrue(nulll);
        assertTrue(none);
    }

    @Test
    public void createAirportBoundary()
    {
        try {
            Airport ap = new Airport(0, 0, 0, 0);
            assertTrue(ap.RESA == 0);
            assertTrue(ap.StripEnd == 0);
            assertTrue(ap.BlastAllowance == 0);
            assertTrue(ap.MinSlope == 0);
        } catch (Exception e) {}
        
        boolean resa = false;
        boolean stripend = false;
        boolean blastallowance = false;
        boolean minslope = false;

        try {
            Airport ap = new Airport(-1, 2, 3, 4);
        } catch(Exception e) {
            resa = true;
        }
        try {
            Airport ap = new Airport(1, -1, 3, 4);
        } catch(Exception e) {
            stripend = true;
        }
        try {
            Airport ap = new Airport(1, 2, -1, 4);
        } catch(Exception e) {
            blastallowance = true;
        }
        try {
            Airport ap = new Airport(1, 2, 3, -1);
        } catch(Exception e) {
            minslope = true;
        }
        assertTrue(resa);
        assertTrue(stripend);
        assertTrue(blastallowance);
        assertTrue(minslope);
    }

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
    public void addRetreiveRunwaysFull()
    {
        Airport ap1 = null;
        ArrayList<Runway> runwayList = new ArrayList<Runway>();
        try {
            runwayList.add(new Runway(9, new RunwayData(306, 3902, 3902, 3902, 3595), new RunwayData(0, 3884, 3962, 3884, 3884)));
            ap1 = new Airport(runwayList);
        } catch (Exception e) {}

        try {
            RunwayOneWay rw1 = null;
            RunwayOneWay rw2 = null;
            rw1 = ap1.getRunway("09L");
            assertFalse(rw1 == null);

            rw2 = ap1.getRunway("27R");
            assertFalse(rw2 == null);

            Runway rwO = ap1.getRunwayFull("09L/27R");
            assertTrue(rw1 == rwO.getRunL());
            assertTrue(rw2 == rwO.getRunR());
        } catch (Exception e) {}
    } */
}
