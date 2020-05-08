import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import javax.xml.bind.JAXBException;


public class RunwayWindowScene extends WindowScene
{
    Airport ap;
    List<Runway> runwayList;
    ComboBox<String> runwayComboBox;
    ComboBox<String> runwaySelectCombo;
    Map<String, String> intersectingRunways;
    Map<Integer, Integer> intersectionPositions;

    public RunwayWindowScene(Airport ap)
    {
        runwaySelectCombo = new ComboBox<>();
        runwayComboBox = new ComboBox<>();
        this.runwayList = new ArrayList<>();
        this.setAirport(ap);
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
        Button quickAdd = new Button("Quick add runways");
        Button removeSelected = new Button("Remove Selected Runway");


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

        //all text fields
        TextField selectedRunwayTextField = new TextField();
        selectedRunwayTextField.setPromptText("No file(s) selected");
        
        TextField runLeftBearingField = initNumTextField("No value given");

        TextField threshold = initNumTextField("No value given");

        TextField threshold2 = initNumTextField("No value given");

        TextField tora = initNumTextField("No value given");

        TextField stopway = initNumTextField("No value given");

        TextField stopway2 = initNumTextField("No value given");

        TextField clearway = initNumTextField("No value given");

        TextField clearway2 = initNumTextField("No value given");

        TextField newRunIntPoint = initNumTextField("On new runway");

        TextField oldRunIntPoint = initNumTextField("On existing runway");

        CheckBox intersectionBox = new CheckBox("Intersects with an existing runway?");

        addRunway.setOnAction(e -> {
            if (selectedRunwayTextField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("You are not able to add a runway");
                alert.setContentText("Please choose a file");
                alert.showAndWait();
            } else {
                //  TODO IMPLEMENT XML RUNWAY
            }
        });



        addRunwayManually.setOnAction(e -> {
            if (runLeftBearingField.getText().isEmpty() || threshold.getText().isEmpty() || threshold2.getText().isEmpty()
                    || tora.getText().isEmpty() || stopway.getText().isEmpty() || stopway2.getText().isEmpty() || clearway.getText().isEmpty() ||clearway2.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("You are not able to add a runway");
                alert.setContentText("Please fill all the runway information text fields");
                alert.showAndWait();

            } else if (intersectionBox.isSelected() &&
                    (newRunIntPoint.getText().isEmpty() || oldRunIntPoint.getText().isEmpty() || runwaySelectCombo.getValue().isEmpty())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("You are not able to add a runway");
                alert.setContentText("Please fill the fields with intersection details and select the intersecting runway");
                alert.showAndWait();
            } else {

                int runwayLeftBearing = Integer.parseInt(runLeftBearingField.getText());
                int thresh = Integer.parseInt(threshold.getText());
                int thresh2 = Integer.parseInt(threshold2.getText());
                int toraa = Integer.parseInt(tora.getText());
                int stop = Integer.parseInt(stopway.getText());
                int stop2 = Integer.parseInt(stopway2.getText());
                int clr = Integer.parseInt(clearway.getText());
                int clr2 = Integer.parseInt(clearway2.getText());

                if (intersectionBox.isSelected()) {

                }

                Runway newRunway = new Runway(runwayLeftBearing, new RunwayData(thresh,toraa,stop,clr), new RunwayData(thresh2,toraa,stop2,clr2));
                addRunway(newRunway);
                runLeftBearingField.clear();
                threshold.clear();
                threshold2.clear();
                tora.clear();
                stopway.clear();
                stopway2.clear();
                clearway.clear();
                clearway2.clear();

            }
        });

        removeSelected.setOnAction(e -> {
            String rmName = runwayComboBox.getValue();
            removeComboRunway(rmName);
        });


        quickAdd.setOnAction(e -> {
            addRunway(new Runway(9, new RunwayData(306, 3902, 3902, 3902, 3595), new RunwayData(0, 3884, 3962, 3884, 3884)));
            addRunway(new Runway(27, new RunwayData(0, 3660, 3660, 3660, 3660), new RunwayData(307, 3660, 3660, 3660, 3353)));
            addRunway(new Runway(16, new RunwayData(0, 3660, 4060, 3860, 3660), new RunwayData(307, 3660, 3660, 3660, 3660)));
            
            goToMainScene(ap);
        });




        //just text
        Text loadRunways = new Text("Add existing runways to the airport");
        loadRunways.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridAddRunways.add(loadRunways, 0, 0, 3, 1);

        Text createRunways = new Text("Create new runways");
        createRunways.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridAddRunways.add(createRunways, 0, 3, 2, 1);
        
        //all labels
        Label selectedRunway = new Label("Selected Runway(s):");
        gridAddRunways.add(selectedRunway, 0, 1);

        Label thresholdLabel = new Label("Threshold Displacement:");
        gridAddRunways.add(thresholdLabel, 0, 5);

        Label toraLabel = new Label("Runway Length:");
        gridAddRunways.add(toraLabel, 0, 6);

        Label stopwayLabel = new Label("Stopway:");
        gridAddRunways.add(stopwayLabel, 0, 7);

        Label clearwayLabel = new Label("Clearway:");
        gridAddRunways.add(clearwayLabel, 0, 8);

        Label newRunIntLabel = new Label("Intersection locations:");
        gridAddRunways.add(newRunIntLabel, 0,10);

        Label selectIntRunLabel = new Label("Select Intersecting Runway:");
        gridAddRunways.add(selectIntRunLabel,0,11);

        Label currRuns = new Label ("Runways Currently in airport: ");
        gridAddRunways.add(currRuns, 0, 13);


        gridAddRunways.add(new Label("Enter Runway Left Bearing:"), 0, 4);



        gridAddRunways.add(hbChooseRunway, 2, 1);
        gridAddRunways.add(selectedRunwayTextField, 1, 1);

        gridAddRunways.add(hbAddRunway, 1, 2);

        gridAddRunways.add(runLeftBearingField, 1, 4);

        gridAddRunways.add(threshold, 1, 5);
        gridAddRunways.add(threshold2, 2, 5);

        gridAddRunways.add(tora, 1, 6);

        gridAddRunways.add(stopway, 1, 7);
        gridAddRunways.add(stopway2, 2, 7);

        gridAddRunways.add(clearway, 1, 8);
        gridAddRunways.add(clearway2, 2, 8);

        gridAddRunways.add(intersectionBox, 1, 9);

        gridAddRunways.add(oldRunIntPoint, 1,10);
        gridAddRunways.add(newRunIntPoint, 2, 10);

        gridAddRunways.add(runwaySelectCombo, 2,11);

        gridAddRunways.add(hbAddRunwayManually, 1, 12);

        gridAddRunways.add(runwayComboBox, 1, 13);
        gridAddRunways.add(removeSelected, 2, 13);

        gridAddRunways.add(back, 0, 14);
        gridAddRunways.add(quickAdd, 1, 14);
        gridAddRunways.add(hbFinish, 2, 14);







        //file choosers with filters
        FileChooser xmlFileChooser = new FileChooser();
        FileChooser airportFileChooser = new FileChooser();
        FileChooser.ExtensionFilter xmlExtensionFilter = new FileChooser.ExtensionFilter(
                "XML Files (*.xml)", "*.xml");
        xmlFileChooser.getExtensionFilters().add(xmlExtensionFilter);
        airportFileChooser.getExtensionFilters().add(xmlExtensionFilter);
        
        chooseRunwayFile.setOnAction(e -> {
            File selectedRunwayFile = xmlFileChooser.showOpenDialog(getAppStage());
            selectedRunwayTextField.setText(selectedRunwayFile.getName());
            selectedRunwayTextField.setStyle("-fx-background-color: yellowgreen");
        });
        
        back.setOnAction(e -> {
            goToLoadCreateScene();
        });



        finish.setOnAction(e -> {
            goToMainScene(ap);
        });

        scene = new Scene(gridAddRunways, 300, 250);
    }

    public void setAirport(Airport ap) {
        this.ap = ap;

        if (ap.getRunways() != null)
            this.setRunwayList(ap.getRunways());
    }

    private TextField initNumTextField(String prompt) {
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

    private void setRunwayList(List<Runway> runways) {


        this.runwayList = runways;
        this.runwayComboBox.getItems().clear();
        this.runwaySelectCombo.getItems().clear();
        this.runwayList.forEach(runway -> {
            runwayComboBox.getItems().add(runway.getName());
            runwaySelectCombo.getItems().add(runway.getName());
        });
    }

    private void removeComboRunway(String runName) {
        Runway runToRm = null;

        for (Runway run: this.runwayList) {
            if (run.getName().equals(runName)) runToRm = run;
        }
        if (runToRm != null) {
            removeRunway(runToRm);
            this.runwayComboBox.getItems().remove(runName);
            this.runwaySelectCombo.getItems().remove(runName);
        } else {
            throw new IllegalArgumentException("Somehow the name passed was not found in the runway list!");
        }
    }

    private void addRunway(Runway runway) {

        this.runwayList.add(runway);
        this.runwayComboBox.getItems().add(runway.getName());
        this.runwaySelectCombo.getItems().add(runway.getName());
    }

    private void removeRunway(Runway runway) {

        if (this.runwayList.remove(runway)) { }
        else throw new IllegalArgumentException("Tried to remove a runway which isnt in the list!");
    }

    private Stage getAppStage() {
        return InputScreenController.getAppStage();
    }

    private void goToLoadCreateScene() {
        InputScreenController.goToLoadCreateWindowScene();
    }

    //TODO
    private void goToMainScene(Airport ap) {
        ap.setRunways(new ArrayList<>());
        for (int i = 0; i < runwayList.size(); i++) {
            ap.addRunway(runwayList.get(i));
        }
        InputScreenController.goToMainScene(ap);
    }

    //  TODO complete implementation to throw alert window
    private Runway importRunway(String filePath) {
        Runway imported = null;
        try {
            imported = InputScreenController.importRunway(filePath);
        } catch (FileNotFoundException fnfe) {
            return null;
        } catch (JAXBException je) {
            return null;
        }

        return imported;
    }

}
