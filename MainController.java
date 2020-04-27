import javafx.stage.Stage;

public class MainController {
    private Launcher app;
    private LoadCreateWindowScene loadCreator;
    private RunwayWindowScene runwayScene;
    private MainWindowScene mainScene;
    private Airport ap;

    public MainController(Launcher app) {
        this.app = app;
        loadCreator = new LoadCreateWindowScene(this);
        this.ap = null;
        app.setScene(loadCreator);
    }


    public void switchToRunwaysScreen(Airport ap) {
        this.ap = ap;
        runwayScene = new RunwayWindowScene(this, ap);
        app.setScene(runwayScene);

    }

    public void switchToMainScreen(Airport ap) {
        mainScene = new MainWindowScene(this, ap);
        app.setScene(mainScene);
    }

    public void backToAirportScreen() {
        app.setScene(loadCreator);
    }

    public void backToRunwayScreen(Airport ap) {
        runwayScene.setAirport(ap);
        app.setScene(runwayScene);
    }

    public Stage getAppStage() {
        return this.app.getStage();
    }
}
