public class Functions 
{
	private static final int RESA = 240;
	private static final int stripEnd = 60;
	private static final int BlastAllowance = 300;

	static public RunwayData reCalculate(RunwayData runway, ObstacleData obstacle)
	{ // Method known to not work
		int slope = obstacle.maxHeight * 50;
		
		if (obstacle.position > runway.threshold) { // Takeoff / Landing toward obstacle
			System.out.println((obstacle.position - runway.threshold));
			return new RunwayData(	runway.threshold,
									(obstacle.position - runway.threshold) + runway.threshold - slope - stripEnd,
									(obstacle.position - runway.threshold) + runway.threshold - slope - stripEnd,
									(obstacle.position - runway.threshold) + runway.threshold - slope - stripEnd,
									(obstacle.position - runway.threshold) - RESA - stripEnd);
		} else {
			return new RunwayData(	runway.threshold,
									runway.TORA - BlastAllowance - (obstacle.position - runway.threshold) - runway.threshold,
									runway.TORA - BlastAllowance - (obstacle.position - runway.threshold) - runway.threshold, // - obstacle.start - engineBlastAllowance,?
									runway.TORA - BlastAllowance - (obstacle.position - runway.threshold) - runway.threshold, // - obstacle.start - engineBlastAllowance,?
									runway.LDA - slope - (obstacle.position - runway.threshold) - stripEnd);
		}
	}
}
