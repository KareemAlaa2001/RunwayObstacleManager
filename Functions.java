public class Functions 
{
	static public RunwayData reCalculate(RunwayData runway, ObstacleData obstacle)
	{ // Very dirty due to prints and overlapping repeat code, needs cleaning
		int slopeAllowance = obstacle.maxHeight * Airport.MinSlope;

		int newTakeoffThreshold = -1;
		int newThreshold = -1;
		int newTORA = -1;
		int newTODA = -1;
		int newASDA = -1;
		int newLDA = -1;

		boolean towards = obstacle.position > (runway.TORA / 2); // Not exactly perfect, find a way to do better

		if (towards && slopeAllowance >= Airport.RESA) {
			newThreshold = runway.threshold;
			newTakeoffThreshold = 0;
			newTORA = newTODA = newASDA = runway.threshold + obstacle.position - slopeAllowance - Airport.StripEnd;
			newLDA = obstacle.position - Airport.RESA - Airport.StripEnd;

			System.out.println("New TORA, TODA, ASDA = " + runway.threshold + " + " + obstacle.position + " - " + slopeAllowance + " - " + Airport.StripEnd + " = " + newTORA);
			System.out.println("New LDA = " + obstacle.position + " - " + Airport.RESA + " - " + Airport.StripEnd + " = " + newLDA);
		} else if (towards && slopeAllowance < Airport.RESA) {
			newThreshold = runway.threshold;
			newTakeoffThreshold = 0;
			newTORA = newTODA = newASDA = runway.threshold + obstacle.position - Airport.RESA - Airport.StripEnd;
			newLDA = obstacle.position - Airport.RESA - Airport.StripEnd;

			System.out.println("New TORA, TODA, ASDA = " + runway.threshold + " + " + obstacle.position + " - " + slopeAllowance + " - " + Airport.StripEnd + " = " + newTORA);
			System.out.println("New LDA = " + obstacle.position + " - " + Airport.RESA + " - " + Airport.StripEnd + " = " + newLDA);
		} else if (Airport.BlastAllowance > (Airport.RESA + Airport.StripEnd)) {
			newTakeoffThreshold = runway.threshold + obstacle.position + Airport.BlastAllowance;

			if (Airport.RESA > slopeAllowance) {
				newThreshold = runway.threshold + obstacle.position + Airport.RESA + Airport.StripEnd;
				System.out.println("New Threshold = " + runway.threshold + " + " + obstacle.position + " + " + Airport.RESA + " + " + Airport.StripEnd + " = " + newThreshold);
				newLDA = runway.LDA - obstacle.position - Airport.RESA - Airport.StripEnd;
				System.out.println("New LDA = " + runway.LDA + " - " + obstacle.position + " - " + Airport.RESA + " - " + Airport.StripEnd + " = " + newLDA);
			} else {
				newThreshold = runway.threshold + obstacle.position + slopeAllowance + Airport.StripEnd;
				System.out.println("New Threshold = " + runway.threshold + " + " + obstacle.position + " + " + slopeAllowance + " + " + Airport.StripEnd + " = " + newThreshold);
				newLDA = runway.LDA - obstacle.position - slopeAllowance - Airport.StripEnd;
				System.out.println("New LDA = " + runway.LDA + " - " + obstacle.position + " - " + slopeAllowance + " - " + Airport.StripEnd + " = " + newLDA);
			}
			newTORA = runway.TORA - Airport.BlastAllowance - runway.threshold - obstacle.position;
			newTODA = newTORA + runway.clearway;
			newASDA = newTORA + runway.stopway;

			System.out.println("New TORA = " + runway.TORA + " - " + Airport.BlastAllowance + " - " + runway.threshold + " - " + obstacle.position + " = " + newTORA);
			System.out.println("New TODA = " + newTORA + " + " + runway.clearway + " = " + newTODA);
			System.out.println("New ASDA = " + newTORA + " + " + runway.stopway + " = " + newASDA);
		} else if (Airport.BlastAllowance <= (Airport.RESA + Airport.StripEnd)) {
			newTakeoffThreshold = runway.threshold + obstacle.position + Airport.RESA + Airport.StripEnd;
			System.out.println("New Threshold = " + runway.threshold + " + " + obstacle.position + " + " + Airport.RESA + " + " + Airport.StripEnd + " = " + newThreshold);
			
			if (Airport.RESA > slopeAllowance) {
				newThreshold = runway.threshold + obstacle.position + Airport.RESA + Airport.StripEnd;
				System.out.println("New Threshold = " + runway.threshold + " + " + obstacle.position + " + " + Airport.RESA + " + " + Airport.StripEnd + " = " + newThreshold);
				newLDA = runway.LDA - obstacle.position - Airport.RESA - Airport.StripEnd;
				System.out.println("New LDA = " + runway.LDA + " - " + obstacle.position + " - " + Airport.RESA + " - " + Airport.StripEnd + " = " + newLDA);
			} else {
				newThreshold = runway.threshold + obstacle.position + slopeAllowance + Airport.StripEnd;
				System.out.println("New Threshold = " + runway.threshold + " + " + obstacle.position + " + " + slopeAllowance + " + " + Airport.StripEnd + " = " + newThreshold);
				newLDA = runway.LDA - obstacle.position - slopeAllowance - Airport.StripEnd;
				System.out.println("New LDA = " + runway.LDA + " - " + obstacle.position + " - " + slopeAllowance + " - " + Airport.StripEnd + " = " + newLDA);
			}
			newTORA = runway.TORA - Airport.RESA - Airport.StripEnd - obstacle.position - runway.threshold;
			newTODA = newTORA + runway.clearway;
			newASDA = newTORA + runway.stopway;

			System.out.println("New TORA = " + runway.TORA + " - " + Airport.RESA + " - " + Airport.StripEnd + " - " + obstacle.position + " = " + newTORA);
			System.out.println("New TODA = " + newTORA + " + " + runway.clearway + " = " + newTODA);
			System.out.println("New ASDA = " + newTORA + " + " + runway.stopway + " = " + newASDA);
		}
		System.out.println();
		return new RunwayData(newThreshold, runway.stopway, runway.clearway, newTORA, newASDA, newTODA, newLDA);
	}
}
