public class MainController {
    Launcher app;
    LoadCreateWindowScene loadCreator;
    RunwayWindowScene runwayScene;
    MainWindowScene mainScene;

    public MainController(Launcher app) {
        this.app = app;
        loadCreator = new LoadCreateWindowScene(app,this);
        app.setScene(loadCreator);
    }


    public void switchToRunwaysScreen(String airportName, Airport ap) {
        runwayScene = new RunwayWindowScene(app, this, airportName, ap);
        app.setScene(runwayScene);
    }

    public void switchToMainScreen(Airport ap) {
        mainScene = new MainWindowScene(app, this, ap);
        app.setScene(mainScene);
    }
}
