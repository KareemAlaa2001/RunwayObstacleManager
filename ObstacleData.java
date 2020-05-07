public class ObstacleData
{
	public int[] position; // Position refers to it's position relative to the threshold
	public int maxHeight;

	public ObstacleData(int[] position, int maxHeight) throws IllegalArgumentException
	{
		if (maxHeight < 0) {
			throw new IllegalArgumentException("Can't create obstacle with  negative height");
		}
		this.position = position;
		this.maxHeight = maxHeight;
	}
}
