
import java.io.File;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;


public class LoadCreateWindowScene extends WindowScene {
    
    public LoadCreateWindowScene(Launcher app) {
        super(app);
        GridPane gridCreateScene = new GridPane();
        
        gridCreateScene.setStyle("-fx-background-color: rgba(172,168,247,0.58)");
        gridCreateScene.setAlignment(Pos.CENTER);
        gridCreateScene.setHgap(20);
        gridCreateScene.setVgap(20);
        gridCreateScene.setPadding(new Insets(25, 25, 25, 25));
        
        //all buttons
        Button chooseAirportFile = new Button("Choose XML file");
        Button loadAirportButton = new Button("Load Airport");
        Button createAirportButton = new Button("Create Airport");
        
        HBox hbChooseAirport = new HBox(10);
        hbChooseAirport.getChildren().add(chooseAirportFile);

        HBox hbLoadAirport = new HBox(10);
        hbLoadAirport.getChildren().add(loadAirportButton);
        hbLoadAirport.setAlignment(Pos.CENTER);

        HBox hbCreateAirport = new HBox(10);
        hbCreateAirport.getChildren().add(createAirportButton);
        hbCreateAirport.setAlignment(Pos.CENTER);
        
        gridCreateScene.add(hbChooseAirport, 2, 3);
        gridCreateScene.add(hbLoadAirport, 1, 4);
        gridCreateScene.add(hbCreateAirport, 1, 12);
        
        //all text fields
        TextField selectedAirportTextField = new TextField();
        selectedAirportTextField.setPromptText("No file selected");
        selectedAirportTextField.setDisable(true);
        selectedAirportTextField.setStyle("-fx-opacity: 1;");
        gridCreateScene.add(selectedAirportTextField, 1, 3);

        TextField airportNameTextField = new TextField();
        airportNameTextField.setPromptText("Example Text");
        gridCreateScene.add(airportNameTextField, 1, 7);

        TextField resaValue = new TextField();
        resaValue.setPromptText("No value given");
        resaValue.setTextFormatter(new TextFormatter<>(c -> {
            if (!c.getControlNewText().matches("\\d*"))
                return null;
            else
                return c;
        }
        ));
        gridCreateScene.add(resaValue, 1, 8);

        TextField stripEndValue = new TextField();
        stripEndValue.setPromptText("No value given");
        stripEndValue.setTextFormatter(new TextFormatter<>(c -> {
            if (!c.getControlNewText().matches("\\d*"))
                return null;
            else
                return c;
        }
        ));
        gridCreateScene.add(stripEndValue, 1, 9);

        TextField blastAllowanceValue = new TextField();
        blastAllowanceValue.setPromptText("No value given");
        blastAllowanceValue.setTextFormatter(new TextFormatter<>(c -> {
            if (!c.getControlNewText().matches("\\d*"))
                return null;
            else
                return c;
        }
        ));
        gridCreateScene.add(blastAllowanceValue, 1, 10);

        TextField minSlopeValue = new TextField();
        minSlopeValue.setPromptText("No value given");
        minSlopeValue.setTextFormatter(new TextFormatter<>(c -> {
            if (!c.getControlNewText().matches("\\d*"))
                return null;
            else
                return c;
        }
        ));
        gridCreateScene.add(minSlopeValue, 1, 11);
        
        BooleanBinding isAirportNameEmpty = Bindings.createBooleanBinding(() -> {
            if (airportNameTextField.getText().isEmpty())
                return false;
            else
                return true;
        }, airportNameTextField.textProperty());

        BooleanBinding isResaEmpty = Bindings.createBooleanBinding(() -> {
            if (resaValue.getText().isEmpty())
                return false;
            else
                return true;
        }, resaValue.textProperty());

        BooleanBinding isStripEndEmpty = Bindings.createBooleanBinding(() -> {
            if (stripEndValue.getText().isEmpty())
                return false;
            else
                return true;
        }, stripEndValue.textProperty());

        BooleanBinding isBlastAllowanceEmpty = Bindings.createBooleanBinding(() -> {
            if (blastAllowanceValue.getText().isEmpty())
                return false;
            else
                return true;
        }, blastAllowanceValue.textProperty());

        BooleanBinding isMinimumSlopeEmpty = Bindings.createBooleanBinding(() -> {
            if (minSlopeValue.getText().isEmpty())
                return false;
            else
                return true;
        }, minSlopeValue.textProperty());
        createAirportButton.disableProperty().bind(isAirportNameEmpty.not().and(isBlastAllowanceEmpty).not().and(isMinimumSlopeEmpty).not().and(isResaEmpty).not().and(isStripEndEmpty).not());

        BooleanBinding isAirportTextFieldEmpty = Bindings.createBooleanBinding(() -> {
            if (selectedAirportTextField.getText().isEmpty())
                return false;
            else
                return true;
        }, selectedAirportTextField.textProperty());
        loadAirportButton.disableProperty().bind(isAirportTextFieldEmpty.not());

        //just text
        Text welcomeText = new Text("Welcome to the runway redeclaration tool");
        welcomeText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 22));
        gridCreateScene.add(welcomeText, 0, 0, 3, 1);

        Text loadAirport = new Text("Load Airport");
        loadAirport.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        gridCreateScene.add(loadAirport, 0, 2);

        Text createAirport = new Text("Create Airport");
        createAirport.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        gridCreateScene.add(createAirport, 0, 6);
        
        //all labels
        Label selectedFile = new Label("Selected File:");
        gridCreateScene.add(selectedFile, 0, 3);

        Label airportName = new Label("Airport Name:");
        gridCreateScene.add(airportName, 0, 7);

        Label resa = new Label("RESA:");
        gridCreateScene.add(resa, 0, 8);

        Label stripEnd = new Label("Strip End:");
        gridCreateScene.add(stripEnd, 0, 9);

        Label blastAllowance = new Label("Blast Allowance:");
        gridCreateScene.add(blastAllowance, 0, 10);

        Label minSlope = new Label("Minimum Slope:");
        gridCreateScene.add(minSlope, 0, 11);
        
        //file choosers with filters
        FileChooser xmlFileChooser = new FileChooser();
        FileChooser airportFileChooser = new FileChooser();
        FileChooser.ExtensionFilter xmlExtensionFilter = new FileChooser.ExtensionFilter(
                "XML Files (*.xml)", "*.xml");
        xmlFileChooser.getExtensionFilters().add(xmlExtensionFilter);
        airportFileChooser.getExtensionFilters().add(xmlExtensionFilter);
        
        chooseAirportFile.setOnAction(e -> {
            File selectedAirportFile = airportFileChooser.showOpenDialog(app.getStage());
            selectedAirportTextField.setText(selectedAirportFile.getName());
            selectedAirportTextField.setStyle("-fx-background-color: yellowgreen");
            selectedAirportTextField.setStyle("-fx-opacity: 1;");
        });
        
        createAirportButton.setOnAction(e -> app.setScene(app.runwayWindowScene));
        loadAirportButton.setOnAction(e -> app.setScene(app.mainWindowScene));
        
        scene = new Scene(gridCreateScene, 300, 250);
    }
    
}
