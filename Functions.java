
public class Functions {

	private static final int RESA = 240;
	private static final int engineBlastAllowance = 300;

	RunwayData runway;
	ObstacleData obstacle;
	public int newThreshHold;
	public int newTODA;
	public int newTORA;
	public int newLDA;
	public int newASDA;
	public int obstacleDistance;
	public int slope;
	public String landingDirection;
	public String takeOffDirection;

	public Functions(RunwayData runway, ObstacleData obstacle) {
		this.runway = runway;
		newTODA = runway.TODA;
		newLDA = runway.LDA;
		newASDA = runway.ASDA;
		newTORA = runway.TORA;
		newThreshHold = runway.threshold;

		this.obstacle = obstacle;

		slope = 0;

	}


	public static RunwayData reCalculate(RunwayData runway, ObstacleData obstacle) {
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
			return new RunwayData(runway.threshold,
					obstacle.start + runway.threshold - slope - 60,
					runway.TORA,
					runway.TORA,
					newLDA);
		} else {
			return new RunwayData(runway.threshold,
					runway.TORA - obstacle.start - engineBlastAllowance,
					runway.TODA - obstacle.start - engineBlastAllowance,
					runway.ASDA - obstacle.start - engineBlastAllowance,
					newLDA);
		}
	}

}

