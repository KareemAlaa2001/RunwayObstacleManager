public class Controller
{
	private initAirport()
	{
        ArrayList<Runway> runwayList = new ArrayList();
        Runway r1 = new Runway(9, new RunwayData(306, 3902, 3902, 3902, 3595), new RunwayData(0, 3884, 3962, 3884, 3884));
        Runway r2 = new Runway(27, new RunwayData(0, 3660, 3660, 3660, 3660), new RunwayData(307, 3660, 3660, 3660, 3353));
        Runway r3 = new Runway(16, new RunwayData(0, 3660, 4060, 3860, 3660), new RunwayData(307, 3660, 3660, 3660, 3660));
        r1.addObstacleL(new ObstacleData(-50, 12));
        r1.addObstacleR(new ObstacleData(3646, 12));
        r2.addObstacleL(new ObstacleData(500, 25));
        r2.addObstacleR(new ObstacleData(2853, 25));
        r3.addObstacleL(new ObstacleData(500, 25));
        r3.addObstacleR(new ObstacleData(2853, 25));
        runwayList.add(r1);
        runwayList.add(r2);
        runwayList.add(r3);

        return new Airport(runwayList);
	}

	public static void main(String[] args)
	{
        Window wnd = new Window(initAirport());

		Window.launch(args);
	}
}
