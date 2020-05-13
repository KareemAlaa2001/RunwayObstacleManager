import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "obstacle")
public class ObstacleData
{
	@XmlElement
	public Point position; // Position refers to it's position relative to the threshold
	@XmlElement
	public int maxHeight;
	@XmlElement
	public int distanceFromCentreLine;

	private ObstacleData()
	{
		this.position = new Point();
	}

	public ObstacleData(Point position, int maxHeight, int distanceFromCentreLine) throws IllegalArgumentException
	{
		if (maxHeight < 0) {
			throw new IllegalArgumentException("Can't create obstacle with  negative height");

		}
		this.position = position;
		this.maxHeight = maxHeight;
        this.distanceFromCentreLine = distanceFromCentreLine;
	}

	public String getName()
	{
		return "Max Height: " + this.maxHeight;
	}
}
