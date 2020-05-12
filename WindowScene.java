
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;


public abstract class WindowScene {
    
    protected Scene scene;
    
    public Scene getScene() {
        return scene;
    }

    public TextField initNumTextField(String prompt) {
        TextField field = new TextField();
        field.setPromptText(prompt);
        field.setTextFormatter(new TextFormatter<>(c -> {
            if (!c.getControlNewText().matches("\\d*"))
                return null;
            else
                return c;
        }
        ));

        return field;
    }

    public void generateAlert(String headerMsg, String contentMsg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(headerMsg);
        alert.setContentText(contentMsg);
        alert.showAndWait();
    }

}
