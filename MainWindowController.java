import javafx.stage.Stage;

public class MainWindowController
{
    private static MainWindowScene outputScene = null;

    public static void setScene(MainWindowScene outputScene)
    {
        MainWindowController.outputScene = outputScene;
    }

    public static void goToMainScreen(Airport ap) {

        outputScene = new MainWindowScene(ap);

        Launcher.setScene(outputScene);
    }

    public static void goToRunwayScreen(Airport ap) {
        InputScreenController.goToRunwayScreen(ap);
    }

    public static void goToCreateScreen() {
        InputScreenController.goToLoadCreateWindowScene();
    }

    public static void addOutputText(String string) {
        if (outputScene == null) {
            System.err.println("Attempted to add output text to null window");
            return;
        }
        outputScene.addOutputText(string);
    }

    public static void addActivityText(String string) {
        if (outputScene == null) {
            System.err.println("Attempted to add activity text to null window");
            return;
        }
        outputScene.addActivityText(string);
    }

    public static void clearOutput() {
        if (outputScene == null) {
            System.err.println("Attempted to clear output text of a null window");
            return;
        }
        outputScene.clearOutput();
    }

    public static void clearActivity() {
        if (outputScene == null) {
            System.err.println("Attempted to clear activity text of a null window");
            return;
        }
        outputScene.clearActivity();
    }

    public static Stage getAppStage() {
        return InputScreenController.getAppStage();
    }
}
