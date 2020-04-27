import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    Airport ap;
//    public RunwayWindowScene(Launcher app)
    public RunwayWindowScene(MainController control,Airport ap)
    {
        super(control);

        GridPane gridAddRunways = new GridPane();
        
        gridAddRunways.setStyle("-fx-background-color: rgba(172,168,247,0.58)");
        gridAddRunways.setAlignment(Pos.CENTER);
        gridAddRunways.setHgap(20);
        gridAddRunways.setVgap(20);
        gridAddRunways.setPadding(new Insets(25, 25, 25, 25));

        this.ap = ap;
        List<Runway> runwayList = new ArrayList<>();

        if (ap.getRunways() != null)
            ap.getRunways().forEach(runway -> { runwayList.add(runway);});



        //all buttons
        Button chooseRunwayFile = new Button("Choose XML file(s)");
        Button addRunwayManually = new Button("Add runway to the airport");
        Button addRunway = new Button("Add runway to the airport");
        Button finish = new Button("Finish");
        Button back = new Button("<-- Back");
        Button quickAdd = new Button("Quick add runways");


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
        TextField runLeftBearingField = new TextField();
        runLeftBearingField.setPromptText("Ex. 09, 16, 27, 30");
        runLeftBearingField.setTextFormatter(new TextFormatter<>(c -> {
            if (!c.getControlNewText().matches("\\d*"))
                return null;
            else
                return c;
        }
        ));
        gridAddRunways.add(runLeftBearingField, 1, 4);
        gridAddRunways.add(new Label("Enter Runway Left Bearing:"), 0, 4);


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

        TextField threshold2 = new TextField();
        threshold2.setPromptText("No value given");
        threshold2.setTextFormatter(new TextFormatter<>(c -> {
            if (!c.getControlNewText().matches("\\d*"))
                return null;
            else
                return c;
        }
        ));
        gridAddRunways.add(threshold2, 2, 5);


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

        TextField stopway2 = new TextField();
        stopway2.setPromptText("No value given");
        stopway2.setTextFormatter(new TextFormatter<>(c -> {
            if (!c.getControlNewText().matches("\\d*"))
                return null;
            else
                return c;
        }
        ));
        gridAddRunways.add(stopway2, 2, 7);


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

        TextField clearway2 = new TextField();
        clearway2.setPromptText("No value given");
        clearway2.setTextFormatter(new TextFormatter<>(c -> {
            if (!c.getControlNewText().matches("\\d*"))
                return null;
            else
                return c;
        }
        ));
        gridAddRunways.add(clearway2, 2, 8);


        Label currRuns = new Label ("Runways Currently in airport: ");
        gridAddRunways.add(currRuns, 0, 10);



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
            if (runLeftBearingField.getText().isEmpty() || threshold.getText().isEmpty() || threshold2.getText().isEmpty()
                    || tora.getText().isEmpty() || stopway.getText().isEmpty() || stopway2.getText().isEmpty() || clearway.getText().isEmpty() ||clearway2.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("You are not able to add a runway");
                alert.setContentText("Please fill all the text fields");
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
                runwayList.add(newRunway);

                runLeftBearingField.clear();
                threshold.clear();
                threshold2.clear();
                tora.clear();
                stopway.clear();
                stopway2.clear();
                clearway.clear();
                clearway2.clear();

                Label currRunsCopy = new Label(currRuns.getText());
                currRuns.setText(currRunsCopy.getText() + " " + runwayLeftBearing + "L,");
            }
        });

        quickAdd.setOnAction(e -> {
            List<Runway> quickRunways = new ArrayList<>();
            Runway r1 = new Runway(9, new RunwayData(306, 3902, 3902, 3902, 3595), new RunwayData(0, 3884, 3962, 3884, 3884));
            Runway r2 = new Runway(27, new RunwayData(0, 3660, 3660, 3660, 3660), new RunwayData(307, 3660, 3660, 3660, 3353));
            Runway r3 = new Runway(16, new RunwayData(0, 3660, 4060, 3860, 3660), new RunwayData(307, 3660, 3660, 3660, 3660));
            quickRunways.add(r1);
            quickRunways.add(r2);quickRunways.add(r3);
            ap.addRunways(quickRunways);
            control.switchToMainScreen(ap);
        });
        gridAddRunways.add(quickAdd, 1, 11);

        //just text
        Text loadRunways = new Text("Add existing runways to the airport");
        loadRunways.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridAddRunways.add(loadRunways, 0, 0, 2, 1);

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









        //file choosers with filters
        FileChooser xmlFileChooser = new FileChooser();
        FileChooser airportFileChooser = new FileChooser();
        FileChooser.ExtensionFilter xmlExtensionFilter = new FileChooser.ExtensionFilter(
                "XML Files (*.xml)", "*.xml");
        xmlFileChooser.getExtensionFilters().add(xmlExtensionFilter);
        airportFileChooser.getExtensionFilters().add(xmlExtensionFilter);
        
        chooseRunwayFile.setOnAction(e -> {
            File selectedRunwayFile = xmlFileChooser.showOpenDialog(control.getAppStage());
            selectedRunwayTextField.setText(selectedRunwayFile.getName());
            selectedRunwayTextField.setStyle("-fx-background-color: yellowgreen");
        });
        
        back.setOnAction(e -> {
            control.backToAirportScreen();
        });



        finish.setOnAction(e -> {
            ap.addRunways(runwayList);
            control.switchToMainScreen(ap);
        });

        scene = new Scene(gridAddRunways, 300, 250);
    }

    public void setAirport(Airport ap) {
        this.ap = ap;
    }
    
}
