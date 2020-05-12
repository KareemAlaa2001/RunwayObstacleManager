import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.HashMap;
import java.util.Map;
import javafx.util.Pair;

public class RunwayData
{
	@XmlElement
	public int threshold;	// Displaced threshold after recalculation (could be the same value)
	@XmlElement
	public int takeoffThreshold; // Similar to above but for the start of the takeoff run
	@XmlElement
	public int stopway;
	@XmlElement
	public int clearway;
	@XmlElement
	public int TORA;		// Take Off Run Available
	@XmlElement
	public int TODA;		// Take Off Distance Available
	@XmlElement
	public int ASDA;		// Accelerate-Stop Distance Available
	@XmlElement
	public int LDA;			// Landing Distance Available

	private ArrayList<Pair<String, Integer>> thresholdBreakdown;

	private RunwayData() {
		thresholdBreakdown = new ArrayList();
	}

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

	public RunwayData(int threshold, int takeoffThreshold, int stopway, int clearway, int TORA, int ASDA, int TODA, int LDA, ArrayList<Pair<String, Integer>> thresholdBreakdown)
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

	@XmlElementWrapper(name = "thresholdBreakdown")
	@XmlElement(name = "thresholdBreakdownEntry")
	public ArrayList<Pair<String, Integer>> getThresholdBreakdown()
	{
		return thresholdBreakdown;
	}

	public void setThresholdBreakdown(ArrayList<Pair<String, Integer>> thresholdBreakdown) {
		this.thresholdBreakdown = thresholdBreakdown;
	}

	@Override
	public String toString()
	{ // For debugging purposes
		return "Thresh: " + threshold + "\n" + "TORA: " + TORA + "\n" + "TODA: " + TODA + "\n" + "ASDA: " + ASDA + "\n" + "LDA: " + LDA + "\n";
	}
}
