import javafx.stage.Stage;

public class MainController {
    public static Launcher app;
    private InputScreenController inputControl;

    public MainController(Launcher app) {
        this.app = app;
        InputScreenController.goToLoadCreateWindowScene();
    }

    public static Stage getAppStage() {
        return app.getStage();
    }
}
