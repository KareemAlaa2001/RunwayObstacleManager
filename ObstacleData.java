public class ObstacleData
{
	public int position; // Position refers to it's position relative to the threshold
	public int maxHeight;

	public ObstacleData(int position, int maxHeight) throws Exception
	{
		if (maxHeight < 0) {
			throw new Exception("Can't create obstacle with  negative height");
		}
		this.position = position;
		this.maxHeight = maxHeight;
	}
}
