import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.awt.Point;

@XmlRootElement(name = "obstacle")
public class ObstacleData
{
	public Point position; // Position refers to it's position relative to the threshold
	public int maxHeight;

	public ObstacleData(Point position, int maxHeight) throws IllegalArgumentException
	{
		if (maxHeight < 0) {
			throw new IllegalArgumentException("Can't create obstacle with  negative height");

		}
		this.position = position;
		this.maxHeight = maxHeight;
	}
}
