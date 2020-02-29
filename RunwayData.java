public class RunwayData
{
	public int threshold; //  displaced threshold
	public int TORA; //  take off run available
	public int TODA; //  take off distance available
	public int ASDA; //  accelerate-stop distance available
	public int LDA;  //  landing distance available


	public RunwayData(int threshold, int TORA, int TODA, int ASDA, int LDA)
	{
		this.threshold = threshold;
		this.TORA = TORA;
		this.TODA = TODA;
		this.ASDA = ASDA;
		this.LDA = LDA;
	}
}
