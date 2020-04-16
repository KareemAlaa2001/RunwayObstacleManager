class BackendHelpers
{
    public static boolean obstacleEqual(ObstacleData a, ObstacleData b)
    {
        return (a.position == b.position) && (a.maxHeight == b.maxHeight);
    }

	public static boolean runwayDataSame(RunwayData a, RunwayData b)
	{
		return (a.TORA == b.TORA) && (a.TODA == b.TODA) && (a.ASDA == b.ASDA) && (a.LDA == b.LDA);
	}
	
    public static boolean runwayDataEqual(RunwayData a, RunwayData b)
    {
        return  (a.threshold == b.threshold) && (a.takeoffThreshold == b.takeoffThreshold) && 
                (a.stopway == b.stopway) && (a.clearway == b.clearway) && (a.TORA == b.TORA) && 
                (a.TODA == b.TODA) && (a.ASDA == b.ASDA) && (a.LDA == b.LDA);
    }
}
