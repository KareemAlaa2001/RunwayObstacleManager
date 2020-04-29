
import java.io.File;

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
import javafx.stage.Stage;


public class LoadCreateWindowScene extends WindowScene {

    public LoadCreateWindowScene() {

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
        Button quickCreate = new Button("Quick Create Airport");

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
        gridCreateScene.add(quickCreate, 2, 12);

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

        loadAirportButton.setOnAction(e -> {
            if (selectedAirportTextField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("You are not able to load an airport");
                alert.setContentText("Please choose a file");
                alert.showAndWait();
            } else {
                // TODO  fill in functionality
            }
        });

        createAirportButton.setOnAction(e -> {
            if (airportNameTextField.getText().isEmpty() || resaValue.getText().isEmpty() || stripEndValue.getText().isEmpty() || blastAllowanceValue.getText().isEmpty() || minSlopeValue.getText().isEmpty()) {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Error");
                alert1.setHeaderText("You are not able to create an airport");
                alert1.setContentText("Please fill all the text fields");
                alert1.showAndWait();
            } else {
                String airportName = airportNameTextField.getText();
                int resa = Integer.parseInt(resaValue.getText());
                int stripEnd = Integer.parseInt(stripEndValue.getText());
                int blastAllowance = Integer.parseInt(blastAllowanceValue.getText());
                int minSlope = Integer.parseInt(minSlopeValue.getText());

                goToNextStep(resa, stripEnd, blastAllowance, minSlope);

            }
        });

        quickCreate.setOnAction((e -> {
            goToNextStep(Airport.RESA, Airport.StripEnd, Airport.BlastAllowance, Airport.MinSlope);
        }));

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

        Label defResa = new Label("" + Airport.RESA);
        Label defStripEnd = new Label("" + Airport.StripEnd);
        Label defBlast = new Label("" + Airport.BlastAllowance);
        Label defSlope = new Label("" + Airport.MinSlope);

        //file choosers with filters
        FileChooser xmlFileChooser = new FileChooser();
        FileChooser airportFileChooser = new FileChooser();
        FileChooser.ExtensionFilter xmlExtensionFilter = new FileChooser.ExtensionFilter(
                "XML Files (*.xml)", "*.xml");
        xmlFileChooser.getExtensionFilters().add(xmlExtensionFilter);
        airportFileChooser.getExtensionFilters().add(xmlExtensionFilter);
        
        chooseAirportFile.setOnAction(e -> {
            File selectedAirportFile = airportFileChooser.showOpenDialog(getAppStage());
            selectedAirportTextField.setText(selectedAirportFile.getName());
            selectedAirportTextField.setStyle("-fx-background-color: yellowgreen");
            selectedAirportTextField.setStyle("-fx-opacity: 1;");
        });
        
        scene = new Scene(gridCreateScene, 300, 250);
    }

    private void goToNextStep(int resa, int stripEnd, int blastAllowance, int minSlope) {
        Airport ap = InputScreenController.initialiseAirport(resa, stripEnd, blastAllowance, minSlope);
        InputScreenController.goToRunwayScreen(ap);
    }

    private Stage getAppStage() {
       return InputScreenController.getAppStage();
    }

}
