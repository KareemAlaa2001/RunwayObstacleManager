import java.io.File;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;



public class RunwayWindowScene extends WindowScene
{
    public RunwayWindowScene(Launcher app)
    {
        super(app);
        GridPane gridAddRunways = new GridPane();
        
        gridAddRunways.setStyle("-fx-background-color: rgba(172,168,247,0.58)");
        gridAddRunways.setAlignment(Pos.CENTER);
        gridAddRunways.setHgap(20);
        gridAddRunways.setVgap(20);
        gridAddRunways.setPadding(new Insets(25, 25, 25, 25));
        
        //all buttons
        Button chooseRunwayFile = new Button("Choose XML file(s)");
        Button addRunwayManually = new Button("Add runway to the airport");
        Button addRunway = new Button("Add runway to the airport");
        Button finish = new Button("Finish");
        Button back = new Button("<-- Back");
        
        HBox hbChooseRunway = new HBox(10);
        hbChooseRunway.getChildren().add(chooseRunwayFile);

        HBox hbFinish = new HBox(10);
        hbFinish.getChildren().add(finish);
        hbFinish.setAlignment(Pos.CENTER_RIGHT);

        HBox hbAddRunwayManually = new HBox(10);
        hbAddRunwayManually.getChildren().add(addRunwayManually);
        hbAddRunwayManually.setAlignment(Pos.CENTER);

        HBox hbAddRunway = new HBox(10);
        hbAddRunway.getChildren().add(addRunway);
        hbAddRunway.setAlignment(Pos.CENTER);
        
        gridAddRunways.add(hbChooseRunway, 2, 1);
        gridAddRunways.add(back, 0, 11);
        gridAddRunways.add(hbAddRunwayManually, 1, 9);
        gridAddRunways.add(hbAddRunway, 1, 2);
        gridAddRunways.add(hbFinish, 2, 11);
        
        //all text fields
        TextField selectedRunwayTextField = new TextField();
        selectedRunwayTextField.setPromptText("No file(s) selected");
        gridAddRunways.add(selectedRunwayTextField, 1, 1);

        TextField threshold = new TextField();
        threshold.setPromptText("No value given");
        threshold.setTextFormatter(new TextFormatter<>(c -> {
            if (!c.getControlNewText().matches("\\d*"))
                return null;
            else
                return c;
        }
        ));
        gridAddRunways.add(threshold, 1, 5);

        TextField tora = new TextField();
        tora.setPromptText("No value given");
        tora.setTextFormatter(new TextFormatter<>(c -> {
            if (!c.getControlNewText().matches("\\d*"))
                return null;
            else
                return c;
        }
        ));
        gridAddRunways.add(tora, 1, 6);

        TextField stopway = new TextField();
        stopway.setPromptText("No value given");
        stopway.setTextFormatter(new TextFormatter<>(c -> {
            if (!c.getControlNewText().matches("\\d*"))
                return null;
            else
                return c;
        }
        ));
        gridAddRunways.add(stopway, 1, 7);

        TextField clearway = new TextField();
        clearway.setPromptText("No value given");
        clearway.setTextFormatter(new TextFormatter<>(c -> {
            if (!c.getControlNewText().matches("\\d*"))
                return null;
            else
                return c;
        }
        ));
        gridAddRunways.add(clearway, 1, 8);

        addRunway.setOnAction(e -> {
            if (selectedRunwayTextField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("You are not able to add a runway");
                alert.setContentText("Please choose a file");
                alert.showAndWait();
            }
        });

        addRunwayManually.setOnAction(e -> {
            if (threshold.getText().isEmpty() || tora.getText().isEmpty() || stopway.getText().isEmpty() || clearway.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("You are not able to add a runway");
                alert.setContentText("Please fill all the text fields");
                alert.showAndWait();
            }
        });

        //just text
        Text loadRunways = new Text("Add existing runways to the airport");
        loadRunways.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridAddRunways.add(loadRunways, 0, 0, 2, 1);

        Text createRunways = new Text("Create new runways");
        createRunways.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridAddRunways.add(createRunways, 0, 4, 2, 1);
        
        //all labels
        Label selectedRunway = new Label("Selected Runway(s):");
        gridAddRunways.add(selectedRunway, 0, 1);

        Label thresholdLabel = new Label("Threshold:");
        gridAddRunways.add(thresholdLabel, 0, 5);

        Label toraLabel = new Label("TORA:");
        gridAddRunways.add(toraLabel, 0, 6);

        Label stopwayLabel = new Label("Stopway:");
        gridAddRunways.add(stopwayLabel, 0, 7);

        Label clearwayLabel = new Label("Clearway:");
        gridAddRunways.add(clearwayLabel, 0, 8);
        
        //file choosers with filters
        FileChooser xmlFileChooser = new FileChooser();
        FileChooser airportFileChooser = new FileChooser();
        FileChooser.ExtensionFilter xmlExtensionFilter = new FileChooser.ExtensionFilter(
                "XML Files (*.xml)", "*.xml");
        xmlFileChooser.getExtensionFilters().add(xmlExtensionFilter);
        airportFileChooser.getExtensionFilters().add(xmlExtensionFilter);
        
        chooseRunwayFile.setOnAction(e -> {
            File selectedRunwayFile = xmlFileChooser.showOpenDialog(app.getStage());
            selectedRunwayTextField.setText(selectedRunwayFile.getName());
            selectedRunwayTextField.setStyle("-fx-background-color: yellowgreen");
        });
        
        back.setOnAction(e -> app.setScene(app.loadCreateWindowScene));
        finish.setOnAction(e -> app.setScene(app.mainWindowScene));
        
        scene = new Scene(gridAddRunways, 300, 250);
    }
    
}
