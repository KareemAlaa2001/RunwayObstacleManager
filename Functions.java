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

		boolean towards = obstacle.position > (runway.TORA / 2); // Need to fix this

		if (towards && slopeAllowance >= Airport.RESA) {
			newThreshold = runway.threshold;
			newTORA = newTODA = newASDA = runway.threshold + obstacle.position - slopeAllowance - Airport.StripEnd;
			newLDA = obstacle.position - Airport.RESA - Airport.StripEnd;
		} else if (towards && slopeAllowance < Airport.RESA) {
			newThreshold = runway.threshold;
			newTORA = newTODA = newASDA = runway.threshold + obstacle.position - Airport.RESA - Airport.StripEnd;
			newLDA = obstacle.position - Airport.RESA - Airport.StripEnd;
		} else if (Airport.BlastAllowance > (Airport.RESA + Airport.StripEnd)) {
			newThreshold = obstacle.position + Airport.BlastAllowance;
			if (Airport.RESA > slopeAllowance) {
				newLDA = runway.LDA - obstacle.position - Airport.RESA - Airport.StripEnd;
			} else {
				newLDA = runway.LDA - obstacle.position - slopeAllowance - Airport.StripEnd;
			}
			newTORA = runway.TORA - Airport.BlastAllowance - runway.threshold - obstacle.position;
			//System.out.println(runway.TORA + " - " + Airport.BlastAllowance + " - " + runway.threshold + " - " + obstacle.position + " = " + newTORA);
			newTODA = newTORA + runway.clearway;
			newASDA = newTORA + runway.stopway;
		} else if (Airport.BlastAllowance <= (Airport.RESA + Airport.StripEnd)) {
			newThreshold = obstacle.position + Airport.RESA + Airport.StripEnd;
			if (Airport.RESA > slopeAllowance) {
				newLDA = runway.LDA - obstacle.position - Airport.RESA - Airport.StripEnd;
			} else {
				newLDA = runway.LDA - obstacle.position - slopeAllowance - Airport.StripEnd;
			}
			newTORA = runway.TORA - Airport.RESA - Airport.StripEnd - obstacle.position - runway.threshold;
			//System.out.println(runway.TORA + " - " + Airport.RESA + " - " + Airport.StripEnd + " - " + obstacle.position + " = " + newTORA);
			newTODA = newTORA + runway.clearway;
			newASDA = newTORA + runway.stopway;
		} else {
			//throw new Exception("Unhandled call to reCalculate as event not covered");
		}
		return new RunwayData(newThreshold, runway.stopway, runway.clearway, newTORA, newASDA, newTODA, newLDA);
	}
}
