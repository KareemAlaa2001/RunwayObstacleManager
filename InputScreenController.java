
import java.util.List;

public class InputScreenController
{
	public static Airport initialiseAirport(int RESA, int StripEnd, int BlastAllowance, int MinSlope)
	{
		return new Airport(RESA, StripEnd, BlastAllowance, MinSlope);
	}

	public static Runway initRunway(int bearing, RunwayData left, RunwayData right)
	{
		return new Runway(bearing, left, right);
	}

	public static RunwayData initRunwayData(int threshold, int TORA, int stopway, int clearway)
	{
		return new RunwayData(threshold, TORA, stopway, clearway);
	}

	public static Airport addRunwaysToAirport(Airport airport, List<Runway> runways) throws Exception
	{
		airport.addRunways(runways);
		return airport;
	}
}
