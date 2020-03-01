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

		boolean towards = obstacle.position > runway.threshold; // Need to fix this

		if (slopeAllowance >= Airport.RESA && towards) {
			newThreshold = runway.threshold;
			newTORA = newTODA = newASDA = newLDA = obstacle.position - slopeAllowance - Airport.StripEnd;
		} else if (slopeAllowance < Airport.RESA && towards) {
			newThreshold = runway.threshold;
			newTORA = newTODA = newASDA = newLDA = obstacle.position - Airport.RESA - Airport.StripEnd;
		} else if (Airport.BlastAllowance > (slopeAllowance + Airport.StripEnd) && Airport.BlastAllowance > (Airport.RESA + Airport.StripEnd)) {
			newLDA = runway.LDA - obstacle.position - Airport.BlastAllowance;
			newThreshold = obstacle.position + Airport.BlastAllowance;
		} else if (Airport.BlastAllowance <= (slopeAllowance + Airport.StripEnd)) {

		} else if (Airport.BlastAllowance <= (Airport.RESA + Airport.StripEnd)) {

		}


		return new RunwayData(newThreshold, newTORA, newTODA, newASDA, newLDA);
	}
}
