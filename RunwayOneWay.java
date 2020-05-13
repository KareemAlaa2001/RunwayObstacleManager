
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import java.util.List;
import java.util.ArrayList;

@XmlRootElement(name = "runwayoneway")
@XmlType(propOrder = {"name", "runwaySpec", "updatedRunway", "impactfulObstacles", "otherObstacles"})
public class RunwayOneWay {

    private String name;
    private RunwayData dataOriginal;
    private RunwayData dataReCalc;
    private List<ObstacleData> impactfulObstacles;
    private List<ObstacleData> otherObstacles;

    private RunwayOneWay() {
        impactfulObstacles = new ArrayList<>();
        otherObstacles = new ArrayList<>();
    }

    public RunwayOneWay(String name, RunwayData RD) {
        this.name = name;
        this.dataOriginal = RD;
        dataReCalc = RD;
        otherObstacles = new ArrayList<ObstacleData>();
        impactfulObstacles = new ArrayList<ObstacleData>();
    }

    public void addObstacle(int distanceFromCentreLine, ObstacleData OD, int bearing, Point start) {
        MainWindowController.addOutputText("Re-Calculating Runway " + name + " due to Obstacle " + OD.maxHeight + "m high, " + MathsHelpers.calculateDistance(OD, bearing, dataOriginal.TORA, start) + "m from threshold " + distanceFromCentreLine + "m from the centreline.");
        RunwayData newData = Functions.reCalculate(dataOriginal, MathsHelpers.calculateDistance(OD, bearing, dataOriginal.TORA, start), OD.maxHeight);
        
        if (newData == null) {
            MainWindowController.addOutputText("Re-Calculating Runway " + name + " causes definate closure, new values are too extreme");
            MainWindowController.addOutputText("");
        } else {
            if (impactfulObstacles.isEmpty()) {
                impactfulObstacles.add(OD);
                dataReCalc = newData;
            } else if (dataCausedChange(newData)) {
                setNewData(newData);
                impactfulObstacles.add(OD);
            } else {
                otherObstacles.add(OD);
            }
        }
    }

    public void removeObstacle(ObstacleData OD, int distanceFromCentreLine, int bearing, Point start) {
        if (OD == null) {
            throw new IllegalArgumentException("Trying to remove a null obstacle!");
        }
        if (otherObstacles.contains(OD) || impactfulObstacles.contains(OD)) {
			MainWindowController.addActivityText("Removed obstacle " + OD.maxHeight + "m high from near runway " + name);
            MainWindowController.addOutputText("Re-calculating runway " + name + " due to the removal of the obstacle " + OD.maxHeight + "m high near to it.");
            MainWindowController.addOutputText("");
        } else {
            return;
        }
        dataReCalc = dataOriginal;

        otherObstacles.remove(OD);
        impactfulObstacles.remove(OD);

        List<ObstacleData> allObstacles = clear();
        for (ObstacleData ob : allObstacles) {
            addObstacle(distanceFromCentreLine, ob, bearing, start);
        }
    }

    public List<ObstacleData> clear() {
        List<ObstacleData> ret = new ArrayList<ObstacleData>();
        ret.addAll(impactfulObstacles);
        ret.addAll(otherObstacles);

        dataReCalc = dataOriginal;
        impactfulObstacles.clear();
        otherObstacles.clear();

        return ret;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    @XmlElement
    public RunwayData getRunwaySpec() { // Returns the runway data as specified without obstacles
        return dataOriginal;
    }

    @XmlElement
    public RunwayData getUpdatedRunway() { // Same as dataOriginal if no obstacles added
        return dataReCalc;
    }

    @XmlElementWrapper(name = "impactfulObstacles")
    @XmlElement(name = "impactfulObstacle")
    public List<ObstacleData> getImpactfulObstacles() {
        return impactfulObstacles;
    }

    @XmlElementWrapper(name = "otherObstacles")
    @XmlElement(name = "otherObstacle")
    public List<ObstacleData> getOtherObstacles() {
        return this.otherObstacles;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRunwaySpec(RunwayData dataOriginal) {
        this.dataOriginal = dataOriginal;
    }

    public void setUpdatedRunway(RunwayData dataReCalc) {
        this.dataReCalc = dataReCalc;
    }

    public void setImpactfulObstacles(List<ObstacleData> impactfulObstacles) {
        this.impactfulObstacles = impactfulObstacles;
    }

    public void setOtherObstacles(List<ObstacleData> otherObstacles) {
        this.otherObstacles = otherObstacles;
    }

    private void setNewData(RunwayData newData) {
        if (newData.threshold > dataReCalc.threshold) {
            dataReCalc.threshold = newData.threshold;
            dataReCalc.thresholdBreakdown = newData.thresholdBreakdown;
        }
        if (newData.takeoffThreshold > dataReCalc.takeoffThreshold) {
            dataReCalc.takeoffThreshold = newData.takeoffThreshold;
        }
        if (newData.TORA < dataReCalc.TORA) {
            dataReCalc.TORA = newData.TORA;
        }
        if (newData.TODA < dataReCalc.TODA) {
            dataReCalc.TODA = newData.TODA;
        }
        if (newData.ASDA < dataReCalc.ASDA) {
            dataReCalc.ASDA = newData.ASDA;
        }
        if (newData.LDA < dataReCalc.LDA) {
            dataReCalc.LDA = newData.LDA;
        }
        dataReCalc.stopway = newData.stopway;
        dataReCalc.clearway = newData.clearway;
    }

    private boolean dataCausedChange(RunwayData newData) {
        return newData.threshold > dataReCalc.threshold || newData.takeoffThreshold > dataReCalc.takeoffThreshold
                || newData.TORA < dataReCalc.TORA || newData.TODA < dataReCalc.TODA
                || newData.ASDA < dataReCalc.ASDA || newData.LDA < dataReCalc.LDA;
    }

}
