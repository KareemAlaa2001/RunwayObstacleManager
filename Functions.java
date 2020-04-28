import java.util.Map;
import java.util.HashMap;

public class Functions 
{
	static public RunwayData reCalculate(RunwayData runway, ObstacleData obstacle) throws Exception
	{ // Very dirty due to prints and overlapping repeat code, needs cleaning also needs to throw an exception if a runway becomes unusable due to recalculation
		if (runway == null || obstacle == null) {
			throw new Exception("null input to reCalculate");
		}
		Map<String, Integer> thresholdBreakdown = new HashMap<String, Integer>();
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

			thresholdBreakdown.put("RESA", Airport.RESA);
			thresholdBreakdown.put("Strip End", Airport.StripEnd);
			MainWindowController.addOutputText("obstacle distance from threshold - RESA - strip end = New LDA");
			MainWindowController.addOutputText(obstacle.position + " - " + Airport.RESA + " - " + Airport.StripEnd + " = " + newLDA);
		} else if (Airport.RESA > slopeAllowance) {
			newThreshold = runway.threshold + obstacle.position + Airport.RESA + Airport.StripEnd;
			newLDA = runway.LDA - obstacle.position - Airport.RESA - Airport.StripEnd;

			thresholdBreakdown.put("RESA", Airport.RESA);
			thresholdBreakdown.put("Strip End", Airport.StripEnd);
			MainWindowController.addOutputText("obstacle distance from threshold + old threshold + RESA + strip end = New Threshold");
			MainWindowController.addOutputText(runway.threshold + " + " + obstacle.position + " + " + Airport.RESA + " + " + Airport.StripEnd + " = " + newThreshold);
			MainWindowController.addOutputText("old LDA - obstacle distance from threshold + RESA + strip end = New LDA");
			MainWindowController.addOutputText(runway.LDA + " - " + obstacle.position + " - " + Airport.RESA + " - " + Airport.StripEnd + " = " + newLDA);
		} else {
			newThreshold = runway.threshold + obstacle.position + slopeAllowance + Airport.StripEnd;
			newLDA = runway.LDA - obstacle.position - slopeAllowance - Airport.StripEnd;

			thresholdBreakdown.put("Slope Allowance", slopeAllowance);
			thresholdBreakdown.put("Strip End", Airport.StripEnd);
			MainWindowController.addOutputText("obstacle distance from threshold + old threshold + RESA + strip end = New Threshold");
			MainWindowController.addOutputText(runway.threshold + " + " + obstacle.position + " + " + slopeAllowance + " + " + Airport.StripEnd + " = " + newThreshold);
			MainWindowController.addOutputText("old LDA - obstacle distance from threshold - slope allowance - strip end = New LDA");
			MainWindowController.addOutputText(runway.LDA + " - " + obstacle.position + " - " + slopeAllowance + " - " + Airport.StripEnd + " = " + newLDA);
		}

		// Calculates values that depend on the usable length of the runway
		if (towards && slopeAllowance >= Airport.RESA) {
			newTORA = newTODA = newASDA = runway.threshold + obstacle.position - slopeAllowance - Airport.StripEnd;

			thresholdBreakdown.put("Slope Allowance", slopeAllowance);
			thresholdBreakdown.put("Strip End", Airport.StripEnd);
			MainWindowController.addOutputText("old threshold + obstacle distance from threshold - slope allowance - strip end = New TORA, TODA and ASDA");
			MainWindowController.addOutputText(runway.threshold + " + " + obstacle.position + " - " + slopeAllowance + " - " + Airport.StripEnd + " = " + newTORA);
		} else if (towards && slopeAllowance < Airport.RESA) {
			newTORA = newTODA = newASDA = runway.threshold + obstacle.position - Airport.RESA - Airport.StripEnd;

			thresholdBreakdown.put("RESA", Airport.RESA);
			thresholdBreakdown.put("Strip End", Airport.StripEnd);
			MainWindowController.addOutputText("old threshold + obstacle distance from threshold - RESA - strip end = New TORA, TODA and ASDA");
			MainWindowController.addOutputText(runway.threshold + " + " + obstacle.position + " - " + Airport.RESA + " - " + Airport.StripEnd + " = " + newTORA);
		} else if (Airport.BlastAllowance > (Airport.RESA + Airport.StripEnd)) {
			newTakeoffThreshold = runway.threshold + obstacle.position + Airport.BlastAllowance;

			newTORA = runway.TORA - Airport.BlastAllowance - runway.threshold - obstacle.position;
			newTODA = newTORA + runway.clearway;
			newASDA = newTORA + runway.stopway;

			thresholdBreakdown.put("Blast Allowance", Airport.BlastAllowance);
			MainWindowController.addOutputText("old TORA - blast allowance  - old Threshold - obstacle distance from threshold = New TORA");
			MainWindowController.addOutputText(runway.TORA + " - " + Airport.BlastAllowance + " - " + runway.threshold + " - " + obstacle.position + " = " + newTORA);
			MainWindowController.addOutputText("new TORA + clearway = New TODA");
			MainWindowController.addOutputText(newTORA + " + " + runway.clearway + " = " + newTODA);
			MainWindowController.addOutputText("new TORA + stopway = New ASDA");
			MainWindowController.addOutputText(newTORA + " + " + runway.stopway + " = " + newASDA);
		} else if (Airport.BlastAllowance <= (Airport.RESA + Airport.StripEnd)) {
			newTakeoffThreshold = runway.threshold + obstacle.position + Airport.RESA + Airport.StripEnd;

			newTORA = runway.TORA - Airport.RESA - Airport.StripEnd - obstacle.position - runway.threshold;
			newTODA = newTORA + runway.clearway;
			newASDA = newTORA + runway.stopway;

			thresholdBreakdown.put("RESA", Airport.RESA);
			thresholdBreakdown.put("Strip End", Airport.StripEnd);
			MainWindowController.addOutputText("old TORA - RESA - strip end - obstacle distance from threshold = New TORA");
			MainWindowController.addOutputText(runway.TORA + " - " + Airport.RESA + " - " + Airport.StripEnd + " - " + obstacle.position + " = " + newTORA);
			MainWindowController.addOutputText("new TORA + clearway = New TODA");
			MainWindowController.addOutputText(newTORA + " + " + runway.clearway + " = " + newTODA);
			MainWindowController.addOutputText("new TORA + stopway = New ASDA");
			MainWindowController.addOutputText(newTORA + " + " + runway.stopway + " = " + newASDA);
		}
		MainWindowController.addOutputText("");
		
		if (newThreshold < 0 || newThreshold < 0 || newTakeoffThreshold < 0 || runway.stopway < 0 || runway.clearway < 0 || newTORA < 0 || newASDA < 0 || newTODA < 0 || newLDA < 0 || thresholdBreakdown == null) {
			return null;
		} else {
			return new RunwayData(newThreshold, newTakeoffThreshold, runway.stopway, runway.clearway, newTORA, newASDA, newTODA, newLDA, thresholdBreakdown);
		}
	}
}
