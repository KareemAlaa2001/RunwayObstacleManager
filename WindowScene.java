
import javafx.scene.Scene;


public abstract class WindowScene {
    
    protected Launcher app;
    protected Scene scene;
    
    public WindowScene(Launcher app) {
        this.app = app;
    }
    
    public Scene getScene() {
        return scene;
    }
    
}
