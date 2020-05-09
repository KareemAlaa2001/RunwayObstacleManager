import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    ComboBox<String> runSelectCombo2;

    List<Intersection> intersections;

    public RunwayWindowScene(Airport ap)
    {
        this.intersections = new ArrayList<>();
        runwaySelectCombo = new ComboBox<>();
        runSelectCombo2 = new ComboBox<>();

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
        Button addIntButton = new Button("Add Intersection");

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

        TextField run1IntPoint = initNumTextField("No value given");

        TextField run2IntPoint = initNumTextField("No value given");

        addIntButton.setOnAction(e -> {
            if (run1IntPoint.getText().isEmpty() || run2IntPoint.getText().isEmpty()
                    || runwaySelectCombo.getValue().isEmpty() || runSelectCombo2.getValue().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("You are not able to add an intersection");
                alert.setContentText("Please select the runways with intersections and enter the respective locations");
                alert.showAndWait();
            } else if (runwaySelectCombo.getValue().equals(runSelectCombo2.getValue())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("You are not able to add an intersection");
                alert.setContentText("Can't have an intersection within the same runway!");
                alert.showAndWait();
            } else {
                String run1Name = runwaySelectCombo.getValue();
                String run2Name = runSelectCombo2.getValue();
                int run1Dist = Integer.parseInt(run1IntPoint.getText());
                int run2Dist = Integer.parseInt(run2IntPoint.getText());

                addIntersection(getRunByName(run1Name),getRunByName(run2Name),run1Dist,run2Dist);

                run1IntPoint.clear();
                run2IntPoint.clear();
            }
        });


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

            } else {

                int runwayLeftBearing = Integer.parseInt(runLeftBearingField.getText());
                int thresh = Integer.parseInt(threshold.getText());
                int thresh2 = Integer.parseInt(threshold2.getText());
                int toraa = Integer.parseInt(tora.getText());
                int stop = Integer.parseInt(stopway.getText());
                int stop2 = Integer.parseInt(stopway2.getText());
                int clr = Integer.parseInt(clearway.getText());
                int clr2 = Integer.parseInt(clearway2.getText());

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

        Text editRunways = new Text("Edit Existing Runways");
        editRunways.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridAddRunways.add(editRunways, 0, 10, 3, 1);

        //all labels
        Label selectedRunway = new Label("Selected Runway(s):");
        gridAddRunways.add(selectedRunway, 0, 1);

        Label thresholdLabel = new Label("Threshold Displacement:");
        gridAddRunways.add(thresholdLabel, 0, 6);

        Label toraLabel = new Label("Runway Length:");
        gridAddRunways.add(toraLabel, 0, 5);

        Label stopwayLabel = new Label("Stopway:");
        gridAddRunways.add(stopwayLabel, 0, 7);

        Label clearwayLabel = new Label("Clearway:");
        gridAddRunways.add(clearwayLabel, 0, 8);

        Label newRunIntLabel = new Label("Intersection locations:");
        gridAddRunways.add(newRunIntLabel, 0,11);

        Label selectIntRunLabel = new Label("Select Intersecting Runway:");
        gridAddRunways.add(selectIntRunLabel,0,12);

        Label currRuns = new Label ("Runways Currently in airport: ");
        gridAddRunways.add(currRuns, 0, 14);


        gridAddRunways.add(new Label("Enter Runway Left Bearing:"), 0, 4);



        gridAddRunways.add(hbChooseRunway, 2, 1);
        gridAddRunways.add(selectedRunwayTextField, 1, 1);

        gridAddRunways.add(hbAddRunway, 1, 2);

        gridAddRunways.add(runLeftBearingField, 1, 4);

        gridAddRunways.add(threshold, 1, 6);
        gridAddRunways.add(threshold2, 2, 6);

        gridAddRunways.add(tora, 1, 5);

        gridAddRunways.add(stopway, 1, 7);
        gridAddRunways.add(stopway2, 2, 7);

        gridAddRunways.add(clearway, 1, 8);
        gridAddRunways.add(clearway2, 2, 8);

        gridAddRunways.add(hbAddRunwayManually, 1, 9);

        gridAddRunways.add(run1IntPoint, 1, 11);
        gridAddRunways.add(run2IntPoint, 2,11);

        gridAddRunways.add(runwaySelectCombo, 1,12);
        gridAddRunways.add(runSelectCombo2, 2,12);

        gridAddRunways.add(addIntButton, 1, 13);

        gridAddRunways.add(runwayComboBox, 1, 14);
        gridAddRunways.add(removeSelected, 2, 14);

        gridAddRunways.add(back, 0, 15);
        gridAddRunways.add(quickAdd, 1, 15);
        gridAddRunways.add(hbFinish, 2, 15);







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
        this.runSelectCombo2.getItems().clear();

        this.runwayList.forEach(runway -> {
            runwayComboBox.getItems().add(runway.getName());
            runwaySelectCombo.getItems().add(runway.getName());
            runSelectCombo2.getItems().add(runway.getName());
        });
    }

    private boolean hasIntersection(Runway run) {
        for (Intersection intersection : this.intersections) {
            if (intersection.isInvolved(run)) return true;
        }

        return false;
    }

    private boolean hasIntersection(Runway run, List<Runway> runList) {
        for (Intersection intersection : this.intersections) {
            if (intersection.isInvolved(run) && runList.contains(intersection.getOtherRunway(run))) return true;
        }

        return false;
    }

    private Runway getRunByName(String runName) {
        Runway runToRm = null;

        for (Runway runway : this.runwayList) {
            if (runway.getName().equals(runName)) runToRm = runway;
        }

        if (runToRm == null ) throw new IllegalArgumentException("Runway name doesn't belong to any runways!");
        else return runToRm;
    }

    private void addIntersection(Runway run1, Runway run2, int dist1, int dist2) {
        this.intersections.add(new Intersection(run1,run2,dist1,dist2));
    }

    private void removeRunIntersections(Runway runway) {
        List<Intersection> inToRm = new ArrayList<>();
        for (int i = 0; i < this.intersections.size(); i++) {
            if (intersections.get(i).isInvolved(runway)) inToRm.add(intersections.get(i));
        }

        this.intersections.removeAll(inToRm);
    }

    private void removeComboRunway(String runName) {
        Runway runToRm = getRunByName(runName);

        if (hasIntersection(runToRm)) {
            removeRunIntersections(runToRm);
        } else {
            removeRunway(runToRm);
            this.runwayComboBox.getItems().remove(runName);
            this.runwaySelectCombo.getItems().remove(runName);
            this.runSelectCombo2.getItems().remove(runName);

        }
    }

    private void addRunway(Runway runway) {

        this.runwayList.add(runway);
        this.runwayComboBox.getItems().add(runway.getName());
        this.runwaySelectCombo.getItems().add(runway.getName());
        this.runSelectCombo2.getItems().add(runway.getName());

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

    private Intersection findIntersection(Runway run) {
        for (Intersection intersection : this.intersections) {
            if (intersection.isInvolved(run)) return intersection;
        }

        throw new IllegalArgumentException("Runway has no intersections!");
    }

    //TODO
    private void goToMainScene(Airport ap) {
        ap.setRunways(new ArrayList<>());
        if (runwayList.size() == 0) {
            return;
        } else {
            ap.addRunway(runwayList.get(0));

            for (int i = 1; i < runwayList.size(); i++) {
                Runway currun = runwayList.get(i);

                if (hasIntersection(currun,runwayList.subList(0,i))) {
                    Intersection intersection = findIntersection(currun);
                    ap.addRunway(intersection.getOtherRunway(currun), intersection.getRunwayDistance(intersection.getOtherRunway(currun)), currun, intersection.getRunwayDistance(currun));
                } else {
                    ap.addRunway(currun);
                }
            }
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
