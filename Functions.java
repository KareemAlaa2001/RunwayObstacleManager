public class Functions 
{
	static public RunwayData reCalculate(RunwayData runway, ObstacleData obstacle) throws Exception
	{ // Very dirty due to prints and overlapping repeat code, needs cleaning also needs to throw an exception if a runway becomes unusable due to recalculation
		if (runway == null || obstacle == null) {
			throw new Exception("null input to reCalculate");
		}
		int slopeAllowance = obstacle.maxHeight * Airport.MinSlope;
		int newTakeoffThreshold = -1;
		int newThreshold = -1;
		int newTORA = -1;
		int newTODA = -1;
		int newASDA = -1;
		int newLDA = -1;

		boolean towards = obstacle.position > (runway.TORA / 2); // Not exactly perfect, find a way to do better

		// Calculates values that depend on LDA / Threshold
		if (towards) {
			newThreshold = runway.threshold;
			newTakeoffThreshold = 0;
			newLDA = obstacle.position - Airport.RESA - Airport.StripEnd;

			Controller.addOutputText("obstacle distance from threshold - RESA - strip end = New LDA");
			Controller.addOutputText(obstacle.position + " - " + Airport.RESA + " - " + Airport.StripEnd + " = " + newLDA);
		} else if (Airport.RESA > slopeAllowance) {
			newThreshold = runway.threshold + obstacle.position + Airport.RESA + Airport.StripEnd;
			newLDA = runway.LDA - obstacle.position - Airport.RESA - Airport.StripEnd;

			Controller.addOutputText("obstacle distance from threshold + old threshold + RESA + strip end = New Threshold");
			Controller.addOutputText(runway.threshold + " + " + obstacle.position + " + " + Airport.RESA + " + " + Airport.StripEnd + " = " + newThreshold);
			Controller.addOutputText("old LDA - obstacle distance from threshold + RESA + strip end = New LDA");
			Controller.addOutputText(runway.LDA + " - " + obstacle.position + " - " + Airport.RESA + " - " + Airport.StripEnd + " = " + newLDA);
		} else {
			newThreshold = runway.threshold + obstacle.position + slopeAllowance + Airport.StripEnd;
			newLDA = runway.LDA - obstacle.position - slopeAllowance - Airport.StripEnd;

			Controller.addOutputText("obstacle distance from threshold + old threshold + RESA + strip end = New Threshold");
			Controller.addOutputText(runway.threshold + " + " + obstacle.position + " + " + slopeAllowance + " + " + Airport.StripEnd + " = " + newThreshold);
			Controller.addOutputText("old LDA - obstacle distance from threshold - slope allowance - strip end = New LDA");
			Controller.addOutputText(runway.LDA + " - " + obstacle.position + " - " + slopeAllowance + " - " + Airport.StripEnd + " = " + newLDA);
		}

		// Calculates values that depend on the usable length of the runway
		if (towards && slopeAllowance >= Airport.RESA) {
			newTORA = newTODA = newASDA = runway.threshold + obstacle.position - slopeAllowance - Airport.StripEnd;

			Controller.addOutputText("old threshold + obstacle distance from threshold - slope allowance - strip end = New TORA, TODA and ASDA");
			Controller.addOutputText(runway.threshold + " + " + obstacle.position + " - " + slopeAllowance + " - " + Airport.StripEnd + " = " + newTORA);
		} else if (towards && slopeAllowance < Airport.RESA) {
			newTORA = newTODA = newASDA = runway.threshold + obstacle.position - Airport.RESA - Airport.StripEnd;

			Controller.addOutputText("old threshold + obstacle distance from threshold - RESA - strip end = New TORA, TODA and ASDA");
			Controller.addOutputText(runway.threshold + " + " + obstacle.position + " - " + Airport.RESA + " - " + Airport.StripEnd + " = " + newTORA);
		} else if (Airport.BlastAllowance > (Airport.RESA + Airport.StripEnd)) {
			newTakeoffThreshold = runway.threshold + obstacle.position + Airport.BlastAllowance;

			newTORA = runway.TORA - Airport.BlastAllowance - runway.threshold - obstacle.position;
			newTODA = newTORA + runway.clearway;
			newASDA = newTORA + runway.stopway;

			Controller.addOutputText("old TORA - blast allowance  - old Threshold - obstacle distance from threshold = New TORA");
			Controller.addOutputText(runway.TORA + " - " + Airport.BlastAllowance + " - " + runway.threshold + " - " + obstacle.position + " = " + newTORA);
			Controller.addOutputText("new TORA + clearway = New TODA");
			Controller.addOutputText(newTORA + " + " + runway.clearway + " = " + newTODA);
			Controller.addOutputText("new TORA + stopway = New ASDA");
			Controller.addOutputText(newTORA + " + " + runway.stopway + " = " + newASDA);
		} else if (Airport.BlastAllowance <= (Airport.RESA + Airport.StripEnd)) {
			newTakeoffThreshold = runway.threshold + obstacle.position + Airport.RESA + Airport.StripEnd;
			
			newTORA = runway.TORA - Airport.RESA - Airport.StripEnd - obstacle.position - runway.threshold;
			newTODA = newTORA + runway.clearway;
			newASDA = newTORA + runway.stopway;

			Controller.addOutputText("old TORA - RESA - strip end - obstacle distance from threshold = New TORA");
			System.out.println(runway.TORA + " - " + Airport.RESA + " - " + Airport.StripEnd + " - " + obstacle.position + " = " + newTORA);
			Controller.addOutputText("new TORA + clearway = New TODA");
			System.out.println(newTORA + " + " + runway.clearway + " = " + newTODA);
			Controller.addOutputText("new TORA + stopway = New ASDA");
			System.out.println(newTORA + " + " + runway.stopway + " = " + newASDA);
		}
		Controller.addOutputText("");
		return new RunwayData(newThreshold, newTakeoffThreshold, runway.stopway, runway.clearway, newTORA, newASDA, newTODA, newLDA);
	}
}
