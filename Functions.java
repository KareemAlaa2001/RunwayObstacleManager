public class Functions 
{
	static public RunwayData reCalculate(RunwayData runway, ObstacleData obstacle)
	{ // Method known to not work
		int slopeAllowance = obstacle.maxHeight * Airport.MinSlope;

		int newThreshold;
		int newTORA;
		int newTODA;
		int newASDA;
		int newLDA;

		boolean towards = obstacle.position > runway.threshold;

		if (slopeAllowance > Airport.RESA && towards) {
			newLDA = obstacle.position - slopeAllowance - Airport.StripEnd;
		} else if (slopeAllowance < Airport.RESA && towards) {
			newLDA = obstacle.position - Airport.RESA - Airport.StripEnd;
		} else if (slopeAllowance > Airport.RESA && !towards && Airport.BlastAllowance > (slopeAllowance + Airport.StripEnd)) {
			newLDA = runway.LDA - obstacle.position - Airport.BlastAllowance;
			newThreshold = obstacle.position + Airport.BlastAllowance;
		}
		
		return new RunwayData(newThreshold, newTORA, newTODA, newASDA, newLDA);
	}
}
