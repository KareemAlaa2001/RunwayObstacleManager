public class Functions 
{
	static public RunwayData reCalculate(RunwayData runway, ObstacleData obstacle)
	{ // Method known to not work
		int slopeAllowance = obstacle.maxHeight * Airport.MinSlope;

		int newThreshold = -1;
		int newTORA = -1;
		int newTODA = -1;
		int newASDA = -1;
		int newLDA = -1;

		boolean towards = obstacle.position > (runway.LDA / 2); // Need to fix this

		if (slopeAllowance >= Airport.RESA && towards) {
			newThreshold = runway.threshold;
			newTORA = newTODA = newASDA = obstacle.position - slopeAllowance - Airport.StripEnd;
			newLDA = newTORA - runway.threshold;
		} else if (slopeAllowance < Airport.RESA && towards) {
			newThreshold = runway.threshold;
			newTORA = newTODA = newASDA = obstacle.position - Airport.RESA - Airport.StripEnd;
			newLDA = newTORA - runway.threshold;
		} else if (Airport.BlastAllowance > (slopeAllowance + Airport.StripEnd) && Airport.BlastAllowance > (Airport.RESA + Airport.StripEnd)) {
			newThreshold = obstacle.position + Airport.BlastAllowance;
			newLDA = runway.TORA - newThreshold;
			newTORA = newLDA;
			newTODA = newTORA + runway.clearway;
			newASDA = newTORA + runway.stopway;
		} else if (Airport.BlastAllowance <= (slopeAllowance + Airport.StripEnd)) {
			newThreshold = obstacle.position + slopeAllowance + Airport.StripEnd;
			newLDA = runway.TORA - newThreshold;
			newTORA = newLDA;
			newTODA = newTORA + runway.clearway;
			newASDA = newTORA + runway.stopway;
		} else if (Airport.BlastAllowance <= (Airport.RESA + Airport.StripEnd)) {
			newThreshold = obstacle.position + Airport.RESA + Airport.StripEnd;
			newLDA = runway.TORA - newThreshold;
			newTORA = newLDA;
			newTODA = newTORA + runway.clearway;
			newASDA = newTORA + runway.stopway;
		} else {
			//throw new Exception("Unhandled call to reCalculate as event not covered");
		}
		return new RunwayData(newThreshold, runway.stopway, runway.clearway, newTORA, newASDA, newTODA, newLDA);
	}
}
