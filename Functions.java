
public class Functions{

	RunwayData runway;
	ObstacleData obstacle;
	public int newThreshHold;
	public int newTODA;
	public int newTORA;
	public int newLDA;
	public int newASDA;
	public int obstacleDistance;
	public int slope;
	public int RESA;
	public int engineBlastAllowance;
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
		RESA = 240;
		engineBlastAllowance = 300;

	}
	
	public RunwayData recalculate(RunwayData runway, ObstacleData obstacle){

		obstacleDistance = obstacle.start + obstacle.length;
		slope = obstacle.maxHeight * 50;

		if (obstacle.start > runway.TORA){
			landingDirection = "Towards";
			takeOffDirection = "Towards";
		}else if(obstacle.start < runway.TORA){
			landingDirection = "Over";
			takeOffDirection = "Away";
		}else{
			System.out.println("Obstacle in center of Runway?");
		}


		if (landingDirection.equals("Towards")) {
			newLDA = obstacle.start - RESA - 60;
		}else if(landingDirection.equals("Over")) {
			newLDA = runway.LDA - obstacleDistance - slope - 60;
		}else{
			System.out.println("Obstacle in center of Runway?");
		}


		if (takeOffDirection.equals("Towards")){
			newTORA = obstacle.start + runway.threshold - slope - 60;
			newTODA = newTORA;
			newASDA = newTORA;
		}else if (takeOffDirection.equals("Away")){
			newTORA = runway.TORA - obstacle.start - engineBlastAllowance;
			newTODA = runway.TODA - obstacle.start - engineBlastAllowance;
			newASDA = runway.ASDA - obstacle.start - engineBlastAllowance;
		}else{

		}


		RunwayData newRunwayData = new RunwayData(newThreshHold, newTORA, newTODA, newASDA, newLDA);
		return newRunwayData;
		
	}
}
