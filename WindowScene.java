
import javafx.scene.Scene;


public abstract class WindowScene {
    
    protected MainController control;
    protected Scene scene;
    
    public WindowScene(MainController control) {
        this.control = control;
    }
    
    public Scene getScene() {
        return scene;
    }
    
}
