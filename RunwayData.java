public class RunwayData
{
	public int threshold;	// displaced threshold after recalculation (could be the same value)
	public int stopway;
	public int clearway;
	public int TORA;		// take off run available
	public int TODA;		// take off distance available
	public int ASDA;		// accelerate-stop distance available
	public int LDA;			// landing distance available

	public RunwayData(int threshold, int TORA, int stopway, int clearway)
	{ // clearway totally seperate to stopway, call this function when initialising a runway
		this.threshold = threshold;
		this.TORA = TORA;
		this.ASDA = TORA + stopway;
		this.TODA = TORA + clearway;
		this.LDA = TORA - threshold;
		this.stopway = stopway;
		this.clearway = clearway;
	}

	public RunwayData(int threshold, int TORA, int TODA, int ASDA, int LDA)
	{ // clearway totally seperate to stopway, call this function when initialising a runway
		this.threshold = threshold;
		this.TORA = TORA;
		this.ASDA = ASDA;
		this.TODA = TODA;
		this.LDA = LDA;
		this.stopway = ASDA - TORA;
		this.clearway = TODA - TORA;
	}

	public RunwayData(int threshold, int stopway, int clearway, int TORA, int ASDA, int TODA, int LDA)
	{ // Any usage of this constructor should be marked as such, only called in recalculation
		this.threshold = threshold;
		this.TORA = TORA;
		this.ASDA = ASDA;
		this.TODA = TODA;
		this.LDA = LDA;
		this.stopway = stopway;
		this.clearway = clearway;
	}

	@Override
	public String toString()
	{ // For debugging purposes
		return "Thresh: " + threshold + "\n" + "TORA: " + TORA + "\n" + "TODA: " + TODA + "\n" + "ASDA: " + ASDA + "\n" + "LDA: " + LDA + "\n";
	}
}
