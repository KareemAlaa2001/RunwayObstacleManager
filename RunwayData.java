import java.util.Map;

public class RunwayData
{
	public int threshold;	// Displaced threshold after recalculation (could be the same value)
	public int takeoffThreshold; // Similar to above but for the start of the takeoff run
	public int stopway;
	public int clearway;
	public int TORA;		// Take Off Run Available
	public int TODA;		// Take Off Distance Available
	public int ASDA;		// Accelerate-Stop Distance Available
	public int LDA;			// Landing Distance Available

	private Map<String, Integer> thresholdBreakdown;

	public RunwayData(int threshold, int TORA, int stopway, int clearway)
	{ // clearway totally seperate to stopway, call this function when initialising a runway
		this.threshold = threshold;
		this.takeoffThreshold = 0;
		this.TORA = TORA;
		this.ASDA = TORA + stopway;
		this.TODA = TORA + clearway;
		this.LDA = TORA - threshold;
		this.stopway = stopway;
		this.clearway = clearway;
		thresholdBreakdown = null;
	}

	public RunwayData(int threshold, int TORA, int TODA, int ASDA, int LDA)
	{ // clearway totally seperate to stopway, call this function when initialising a runway
		this.threshold = threshold;
		this.takeoffThreshold = 0;
		this.TORA = TORA;
		this.ASDA = ASDA;
		this.TODA = TODA;
		this.LDA = LDA;
		this.stopway = ASDA - TORA;
		this.clearway = TODA - TORA;
		thresholdBreakdown = null;
	}

	public RunwayData(int threshold, int takeoffThreshold, int stopway, int clearway, int TORA, int ASDA, int TODA, int LDA)
	{ // Any usage of this constructor should be marked as such, only called in recalculation
		this.threshold = threshold;
		this.takeoffThreshold = takeoffThreshold;
		this.TORA = TORA;
		this.ASDA = ASDA;
		this.TODA = TODA;
		this.LDA = LDA;
		this.stopway = stopway;
		this.clearway = clearway;
		thresholdBreakdown = null;
	}

	public RunwayData(int threshold, int takeoffThreshold, int stopway, int clearway, int TORA, int ASDA, int TODA, int LDA, Map<String, Integer> thresholdBreakdown)
	{ // Any usage of this constructor should be marked as such, only called in recalculation
		this.threshold = threshold;
		this.takeoffThreshold = takeoffThreshold;
		this.TORA = TORA;
		this.ASDA = ASDA;
		this.TODA = TODA;
		this.LDA = LDA;
		this.stopway = stopway;
		this.clearway = clearway;
		this.thresholdBreakdown = thresholdBreakdown;
	}

	public Map<String, Integer> getThresholdBreakdown() throws Exception
	{ // Throws exception if thresholdBreakdown is not initialised, this is not an error, more like a flag
		if (thresholdBreakdown == null) {
			throw new Exception("thresholdBreakdown is not initialised");
		}
		return thresholdBreakdown;
	}

	@Override
	public String toString()
	{ // For debugging purposes
		return "Thresh: " + threshold + "\n" + "TORA: " + TORA + "\n" + "TODA: " + TODA + "\n" + "ASDA: " + ASDA + "\n" + "LDA: " + LDA + "\n";
	}
}
