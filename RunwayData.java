public class RunwayData
{
	public int threshold;	// displaced threshold
	public int stopway;
	public int clearway;
	public int TORA;		// take off run available
	public int TODA;		// take off distance available
	public int ASDA;		// accelerate-stop distance available
	public int LDA;			// landing distance available

	public RunwayData(int threshold, int TORA, int stopway, int clearway)
	{ // clearway includes stopway
		this.threshold = threshold;
		this.TORA = TORA;
		this.ASDA = TORA + stopway;
		this.TODA = TORA + clearway;
		this.LDA = TORA - threshold;
		this.stopway = stopway;
		this.clearway = clearway;
	}

	@Override
	public String toString()
	{
		return "Thresh: " + threshold + "\n" + "TORA: " + TORA + "\n" + "TODA: " + TODA + "\n" + "ASDA: " + ASDA + "\n" + "LDA: " + LDA + "\n";
	}
}
