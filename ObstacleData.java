public class ObstacleData
{
	public int start
	public int length;
	public int maxPoint;
	public int maxHeight;
	public boolean left;

	public RunwayData(int start, int length, int maxPoint, int maxHeight, boolean left)
	{
		this.left = left;
		this.start = start;
		this.length = length;
		this.maxPoint = maxPoint;
		this.maxHeight = maxHeight;
	}
}