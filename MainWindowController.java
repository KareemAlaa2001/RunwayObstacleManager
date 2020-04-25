import java.util.ArrayList;
import java.util.List;

public class MainWindowController
{
    private static MainWindowScene outputScene;

    public static void setScene(MainWindowScene outputScene)
    {
        this.outputScene = outputScene;
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
}
