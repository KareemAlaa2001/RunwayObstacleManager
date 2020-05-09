public class Intersection {
    Runway baseRunway;
    Runway intersectingRunway;
    int baseDistance;
    int intersectingDistance;

    public Intersection(Runway baseRun, Runway intersectingRunway, int baseDistance, int intersectingDistance) {
        this.baseRunway = baseRun;
        this.intersectingRunway = intersectingRunway;
        this.baseDistance = baseDistance;
        this.intersectingDistance = intersectingDistance;
    }

    public Runway getOtherRunway(Runway run) {
        if (run.equals(baseRunway)) return intersectingRunway;
        else if (run.equals(intersectingRunway)) return baseRunway;
        else throw new IllegalArgumentException("Passed runway is not involved in this intersection!");
    }

    public boolean isInvolved(Runway run) {
        if (run.equals(baseRunway) || run.equals(intersectingRunway)) return true;
        else return false;
    }

    public Runway getBaseRunway() {
        return baseRunway;
    }

    public int getRunwayDistance(Runway run) {
        if (run.equals(this.getBaseRunway())) return getBaseDistance();
        else if (run.equals(this.getIntersectingRunway())) return getIntersectingDistance();
        else throw new IllegalArgumentException("Runway not involved in this intersection!");
    }

    public void setBaseRunway(Runway baseRunway) {
        this.baseRunway = baseRunway;
    }

    public Runway getIntersectingRunway() {
        return intersectingRunway;
    }

    public void setIntersectingRunway(Runway intersectingRunway) {
        this.intersectingRunway = intersectingRunway;
    }

    public int getBaseDistance() {
        return baseDistance;
    }

    public void setBaseDistance(int baseDistance) {
        this.baseDistance = baseDistance;
    }

    public int getIntersectingDistance() {
        return intersectingDistance;
    }

    public void setIntersectingDistance(int intersectingDistance) {
        this.intersectingDistance = intersectingDistance;
    }
}
