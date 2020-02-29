public class Functions 
{
	private static final int RESA = 240;
	private static final int engineBlastAllowance = 300;

	static public RunwayData reCalculate(RunwayData runway, ObstacleData obstacle)
	{
		boolean landingToward;
		boolean takeOffToward;
		int newLDA = runway.LDA;
		int slope = obstacle.maxHeight * 50;
		int obstacleDistance = obstacle.start + obstacle.length;

		if (obstacle.start > runway.TORA) {
			landingToward = true;
			takeOffToward = true;
		} else {
			landingToward = false;
			takeOffToward = false;
		}

		if (landingToward) {
			newLDA = obstacle.start - RESA - 60;
		} else {
			newLDA = runway.LDA - obstacleDistance - slope - 60;
		}

		if (takeOffToward) {
			return new RunwayData(	runway.threshold,
									obstacle.start + runway.threshold - slope - 60,
									runway.TORA,
									runway.TORA,
									newLDA);
		} else {
			return new RunwayData(	runway.threshold,
									runway.TORA - obstacle.start - engineBlastAllowance,
									runway.TODA - obstacle.start - engineBlastAllowance,
									runway.ASDA - obstacle.start - engineBlastAllowance,
									newLDA);
		}
	}
}