
import java.util.List;
import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class RunwayCanvas extends Canvas {

    private final int MARGIN = 50;
    private final double VRATIO = 0.7;

    private Point runwayStartLocation;
    private int w, h;
    private Runway runway;
    private RunwayOneWay runwayL, runwayR;
    private RunwayData nDataL = null;
    private RunwayData oDataL = null;
    private RunwayData nDataR = null;
    private RunwayData oDataR = null;
    private String name = "Unselected";
    private List<ObstacleData> impactfulObstacles = null;
    private List<ObstacleData> otherObstacles = null;
    private double totalScale = 0;
    private double gradedArea = 0;
    private double runwayVpos = 0;

    public RunwayCanvas(int w, int h, Runway runway, Point runwayStartLocation) {
        super(w, h);
        this.w = w;
        this.h = h;
        this.runway = runway;
        this.runwayStartLocation = runwayStartLocation;
        if (Integer.parseInt(runway.getRunL().getName().substring(0, 2)) < Integer.parseInt(runway.getRunR().getName().substring(0, 2))) {
            runwayL = runway.getRunL();
            runwayR = runway.getRunR();
        } else {
            runwayL = runway.getRunR();
            runwayR = runway.getRunL();
        }

        this.oDataL = runwayL.getRunwaySpec();
        this.nDataL = runwayL.getUpdatedRunway();
        this.oDataR = runwayR.getRunwaySpec();
        this.nDataR = runwayR.getUpdatedRunway();

        this.name = runway.getName();
        this.impactfulObstacles = runway.getRunL().getImpactfulObstacles();
        this.otherObstacles = runway.getRunL().getOtherObstacles();
        totalScale = (w - (MARGIN * 2)) / (oDataR.clearway + oDataL.TORA + oDataL.clearway + 0.0);
        this.gradedArea = runway.getGradedArea();
        runwayVpos = this.getWidth() / 2 - 25;
    }

    public RunwayCanvas(int w, int h) {
        super(w, h);
    }

    public void render(double scale, double tx, double ty, boolean rotate, boolean hideObs) {
        final GraphicsContext g = this.getGraphicsContext2D();

        g.setFill(Color.WHITE);
        g.setFont(Font.font("monospaced", 20));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        if (runway != null) {

            g.setFill(Color.LIGHTGRAY);
            g.fillRect(0, 0, this.getWidth(), g.getCanvas().getHeight() * VRATIO);

            this.impactfulObstacles = runway.getRunL().getImpactfulObstacles();
            this.otherObstacles = runway.getRunL().getOtherObstacles();
            List<ObstacleData> impactfulObstacles;
            List<ObstacleData> otherObstacles;
            if (hideObs) {
                impactfulObstacles = new ArrayList();
                otherObstacles = new ArrayList();
                nDataL = runway.getRunL().getRunwaySpec();
                nDataR = runway.getRunR().getRunwaySpec();
            } else {
                impactfulObstacles = this.impactfulObstacles;
                otherObstacles = this.otherObstacles;
                nDataL = runway.getRunL().getUpdatedRunway();
                nDataR = runway.getRunR().getUpdatedRunway();
            }

            g.save();
            g.rect(0, 0, this.getWidth(), this.getHeight() * VRATIO);
            g.clip();

            g.save();
            g.translate(-tx, -ty);
            g.scale(scale, scale);

            if (rotate) {
                g.save();
                g.translate(this.getWidth() / 2, this.getWidth() / 2);
                g.rotate(Integer.parseInt(runwayL.getName().substring(0, 2)) * 10.0 - 90.0);
                g.translate(-this.getWidth() / 2, -this.getWidth() / 2);
            }

            g.setLineWidth(1);
            g.setFill(Color.WHITE);
            double[] xPoints = new double[]{0,
                oDataR.clearway * totalScale + MARGIN,
                (oDataR.clearway + 200) * totalScale + MARGIN,
                (oDataL.TORA + oDataR.clearway - 200) * totalScale + MARGIN,
                (oDataL.TORA + oDataR.clearway) * totalScale + MARGIN,
                this.getWidth(),
                this.getWidth(),
                (oDataL.TORA + oDataR.clearway) * totalScale + MARGIN,
                (oDataL.TORA + oDataR.clearway - 200) * totalScale + MARGIN,
                (oDataR.clearway + 200) * totalScale + MARGIN,
                oDataR.clearway * totalScale + MARGIN,
                0};
            double[] yPoints = new double[]{runwayVpos - 25,
                runwayVpos - 25,
                runwayVpos + 25 - gradedArea * totalScale,
                runwayVpos + 25 - gradedArea * totalScale,
                runwayVpos - 25,
                runwayVpos - 25,
                runwayVpos + 75,
                runwayVpos + 75,
                runwayVpos + 25 + gradedArea * totalScale,
                runwayVpos + 25 + gradedArea * totalScale,
                runwayVpos + 75,
                runwayVpos + 75};
            g.fillPolygon(xPoints, yPoints, 12);

            drawRect(g, oDataR.clearway, runwayVpos, oDataL.TORA, 50, Color.GRAY, Color.BLACK); // Top runway
            drawRect(g, oDataR.clearway - oDataR.stopway, runwayVpos, oDataR.stopway, 50, Color.LIGHTGREY, Color.BLACK); //Top left stopway
            drawRect(g, oDataR.clearway + oDataL.TORA, runwayVpos, oDataL.stopway, 50, Color.LIGHTGREY, Color.BLACK); //Top right stopway
            drawRect(g, 0, runwayVpos - 10, oDataR.clearway, 70, Color.TRANSPARENT, Color.GRAY); //Top left clearway
            drawRect(g, oDataR.clearway + oDataL.TORA, runwayVpos - 10, oDataL.clearway, 70, Color.TRANSPARENT, Color.GRAY); //Top right clearway

            g.setLineWidth(2);
            g.setStroke(Color.WHITE);
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 4; j++) {
                    g.strokeLine(oDataR.clearway * totalScale + MARGIN + 5, runwayVpos + 5 + i * 25 + j * 5, oDataR.clearway * totalScale + MARGIN + 20, runwayVpos + 5 + i * 25 + j * 5); //Top left first markers
                    g.strokeLine((oDataR.clearway + oDataL.TORA) * totalScale + MARGIN - 5, runwayVpos + 5 + i * 25 + j * 5, (oDataR.clearway + oDataL.TORA) * totalScale + MARGIN - 20, runwayVpos + 5 + i * 25 + j * 5); //Top right first markers
                }
            }
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3; j++) {
                    g.strokeLine(oDataR.clearway * totalScale + MARGIN + 65, runwayVpos + 5 + i * 30 + j * 5, oDataR.clearway * totalScale + MARGIN + 75, runwayVpos + 5 + i * 30 + j * 5); //Top left second markers
                    g.strokeLine((oDataR.clearway + oDataL.TORA) * totalScale + MARGIN - 65, runwayVpos + 5 + i * 30 + j * 5, (oDataR.clearway + oDataL.TORA) * totalScale + MARGIN - 75, runwayVpos + 5 + i * 30 + j * 5); //Top right second markers
                }
            }
            drawRotatedText(g, runwayL.getName(), oDataR.clearway * totalScale + MARGIN + 38, runwayVpos + 20 - (textWidth(g, runwayL.getName()) / 2), 90, Color.WHITE); //Top left bearing marker
            drawRotatedText(g, runwayR.getName(), (oDataR.clearway + oDataL.TORA) * totalScale + MARGIN - 38, runwayVpos + 30 + (textWidth(g, runwayR.getName()) / 2), 270, Color.WHITE); //Top right bearing marker

            for (ObstacleData ob : impactfulObstacles) {
                int position = MathsHelpers.calculateDistance(ob, runway.getLeftBearing(), runway.getRunL().getRunwaySpec().TORA, runwayStartLocation);
                drawRect(g, position + oDataL.threshold + oDataR.clearway - 50, runwayVpos + 5 - ob.distanceFromCentreLine, 100, 40, Color.RED, Color.BLACK); //Top obstacle
            }

            drawLine(g, oDataR.clearway + nDataL.takeoffThreshold, 235 + MARGIN, oDataR.clearway + nDataL.takeoffThreshold, runwayVpos, Color.BLACK); //Left tTake off threshold
            double labelDiv = (runwayVpos - 235 - MARGIN) / 4.0;
            drawLine(g, oDataR.clearway + nDataL.takeoffThreshold, 235 + MARGIN + labelDiv * 0, oDataR.clearway + nDataL.takeoffThreshold + nDataL.TODA, 235 + MARGIN + labelDiv * 0, Color.BLACK, Color.WHITE, "TODA - " + nDataL.TODA + "m", true, true, 5, -5); //Left TODA
            drawLine(g, oDataR.clearway + nDataL.takeoffThreshold, 235 + MARGIN + labelDiv * 1, oDataR.clearway + nDataL.takeoffThreshold + nDataL.ASDA, 235 + MARGIN + labelDiv * 1, Color.BLACK, Color.WHITE, "ASDA - " + nDataL.ASDA + "m", true, true, 5, -5); //Left ASDA
            drawLine(g, oDataR.clearway + nDataL.takeoffThreshold, 235 + MARGIN + labelDiv * 2, oDataR.clearway + nDataL.takeoffThreshold + nDataL.TORA, 235 + MARGIN + labelDiv * 2, Color.BLACK, Color.WHITE, "TORA - " + nDataL.TORA + "m", true, true, 5, -5); //Left TORA

            drawLine(g, oDataR.clearway + nDataL.threshold, 235 + MARGIN + labelDiv * 3, oDataR.clearway + nDataL.threshold, runwayVpos, Color.BLACK); //Left landing threshold
            drawLine(g, oDataR.clearway + nDataL.threshold, 235 + MARGIN + labelDiv * 3, oDataR.clearway + nDataL.threshold + nDataL.LDA, 235 + MARGIN + labelDiv * 3, Color.BLACK, Color.WHITE, "LDA - " + nDataL.LDA + "m", true, true, 5, -5); //Left LDA

            drawLine(g, oDataR.clearway + nDataL.threshold, runwayVpos - 20, oDataR.clearway + nDataL.threshold, runwayVpos, Color.BLACK, Color.WHITE, "Threshold", true, false, 5, 15); //Top left threshold label

            drawLine(g, oDataR.clearway + oDataL.TORA - nDataR.takeoffThreshold, h - 30 - MARGIN, oDataR.clearway + oDataL.TORA - nDataR.takeoffThreshold, runwayVpos + 50, Color.BLACK); //Right tTake off threshold
            labelDiv = (h - 30 - MARGIN - runwayVpos - 50) / 4.0;
            drawLine(g, oDataR.clearway + oDataL.TORA - nDataR.takeoffThreshold, h - 30 - MARGIN - labelDiv * 0, oDataR.clearway + oDataL.TORA - nDataR.takeoffThreshold - nDataR.TODA, h - 30 - MARGIN - labelDiv * 0, Color.BLACK, Color.WHITE, "TODA - " + nDataR.TODA + "m", false, true, -5, -5); //Right TODA
            drawLine(g, oDataR.clearway + oDataL.TORA - nDataR.takeoffThreshold, h - 30 - MARGIN - labelDiv * 1, oDataR.clearway + oDataL.TORA - nDataR.takeoffThreshold - nDataR.ASDA, h - 30 - MARGIN - labelDiv * 1, Color.BLACK, Color.WHITE, "ASDA - " + nDataR.ASDA + "m", false, true, -5, -5); //Right ASDA
            drawLine(g, oDataR.clearway + oDataL.TORA - nDataR.takeoffThreshold, h - 30 - MARGIN - labelDiv * 2, oDataR.clearway + oDataL.TORA - nDataR.takeoffThreshold - nDataR.TORA, h - 30 - MARGIN - labelDiv * 2, Color.BLACK, Color.WHITE, "TORA - " + nDataR.TORA + "m", false, true, -5, -5); //Right TORA
//            
            drawLine(g, oDataR.clearway + oDataL.TORA - nDataR.threshold, h - 30 - MARGIN - labelDiv * 3, oDataR.clearway + oDataL.TORA - nDataR.threshold, runwayVpos + 50, Color.BLACK); //Right landing threshold
            drawLine(g, oDataR.clearway + oDataL.TORA - nDataR.threshold, h - 30 - MARGIN - labelDiv * 3, oDataR.clearway + oDataL.TORA - nDataR.threshold - nDataR.LDA, h - 30 - MARGIN - labelDiv * 3, Color.BLACK, Color.WHITE, "LDA - " + nDataR.LDA + "m", false, true, -5, -5); //Right LDA
//            
            drawLine(g, oDataR.clearway + oDataL.TORA - nDataR.threshold, runwayVpos + 70, oDataR.clearway + oDataL.TORA - nDataR.threshold, runwayVpos + 50, Color.BLACK, Color.WHITE, "Threshold", false, false, -5, -3); //Top right threshold label

            g.restore();
            g.restore();
            g.restore();

            double bottomCL = this.getHeight() * (1.0 - VRATIO) * 0.5 + this.getHeight() * VRATIO;

            g.setFill(Color.GRAY);
            drawRect(g, oDataR.clearway, bottomCL + 50, oDataL.TORA, 10, Color.GRAY, Color.BLACK); //Bottom runway
            drawRect(g, oDataR.clearway - oDataR.stopway, bottomCL + 50, oDataR.stopway, 10, Color.LIGHTGREY, Color.BLACK); //Bottom left stopway
            drawRect(g, oDataR.clearway + oDataL.TORA, bottomCL + 50, oDataL.stopway, 10, Color.LIGHTGREY, Color.BLACK); //Bottom right stopway
            drawRect(g, 0, bottomCL + 50, oDataR.clearway, 20, Color.TRANSPARENT, Color.BLACK); //Bottom left clearway
            drawRect(g, oDataR.clearway + oDataL.TORA, bottomCL + 50, oDataL.clearway, 20, Color.TRANSPARENT, Color.BLACK); //Bottom left clearway

            g.setLineWidth(2);
            g.setStroke(Color.WHITE);
            for (int i = 0; i < 2; i++) {
                g.strokeLine(oDataR.clearway * totalScale + MARGIN + 5, bottomCL + 52 + i * 3, oDataR.clearway * totalScale + MARGIN + 20, bottomCL + 52 + i * 3);
                g.strokeLine((oDataR.clearway + oDataL.TORA) * totalScale + MARGIN - 5, bottomCL + 52 + i * 3, (oDataR.clearway + oDataL.TORA) * totalScale + MARGIN - 20, bottomCL + 52 + i * 3);
            }
            g.strokeLine(oDataR.clearway * totalScale + MARGIN + 65, bottomCL + 52, oDataR.clearway * totalScale + MARGIN + 75, bottomCL + 52);
            g.strokeLine((oDataR.clearway + oDataL.TORA) * totalScale + MARGIN - 65, bottomCL + 52, (oDataR.clearway + oDataL.TORA) * totalScale + MARGIN - 75, bottomCL + 52);

            double maxHeight = 0.0;
            for (ObstacleData ob : impactfulObstacles) {
                maxHeight = Math.max(maxHeight, ob.maxHeight);
            }
            double vScale = 100.0 / maxHeight;

            ObstacleData leftOb = null;
            int leftObPos = 0;
            ObstacleData rightOb = null;
            int rightObPos = 0;
            for (ObstacleData ob : impactfulObstacles) {
                int position = MathsHelpers.calculateDistance(ob, runway.getLeftBearing(), runway.getRunL().getRunwaySpec().TORA, runwayStartLocation);
                drawTriangle(g, position + oDataL.threshold + oDataR.clearway, bottomCL + 50, ob.maxHeight * vScale, Color.RED, Color.BLACK); //Bottom obstacle
                if (position > oDataL.TORA / 2) {
                    if (rightOb == null || (position - ob.maxHeight * Airport.MinSlope) < (rightObPos - rightOb.maxHeight * Airport.MinSlope)) {
                        rightOb = ob;
                        rightObPos = position;
                    }
                } else {
                    if (leftOb == null || (position + ob.maxHeight * Airport.MinSlope) > (leftObPos + leftOb.maxHeight * Airport.MinSlope)) {
                        leftOb = ob;
                        leftObPos = position;
                    }
                }
            }

            if (leftOb != null) {
                double angle = Math.atan((leftOb.maxHeight * Airport.MinSlope * totalScale) / 100.0);
                drawLine(g, leftObPos + oDataL.threshold + oDataR.clearway, bottomCL + 75,
                        leftObPos + oDataL.threshold + oDataR.clearway + leftOb.maxHeight * Airport.MinSlope, bottomCL + 75, Color.BLACK,
                        leftOb.maxHeight + "m x 50 = " + (leftOb.maxHeight * 50) + "m", true, false, 5, 15); //Left horizontal label
                drawLine(g, leftObPos + oDataL.threshold + oDataR.clearway, bottomCL + 60,
                        leftObPos + oDataL.threshold + oDataR.clearway, bottomCL + 95, Color.BLACK); //Left left horizontal marker
                drawLine(g, leftObPos + oDataL.threshold + oDataR.clearway + leftOb.maxHeight * Airport.MinSlope, bottomCL + 60,
                        leftObPos + oDataL.threshold + oDataR.clearway + leftOb.maxHeight * Airport.MinSlope, bottomCL + 75, Color.BLACK); //Left right horizontal marker
                drawLine(g, leftObPos + oDataL.threshold + oDataR.clearway, bottomCL + 50 - leftOb.maxHeight * vScale,
                        leftObPos + oDataL.threshold + oDataR.clearway + leftOb.maxHeight * Airport.MinSlope, bottomCL + 50, Color.BLACK); //Left slope
                drawRotatedText(g, "            ALS", (leftObPos + oDataL.threshold + oDataR.clearway) * totalScale + MARGIN, bottomCL + 45 - leftOb.maxHeight * vScale,
                        90 - Math.toDegrees(angle), Color.BLACK); //Left slope label
                drawLine(g, leftObPos + oDataL.threshold + oDataR.clearway - 70, bottomCL + 50 - leftOb.maxHeight * vScale,
                        leftObPos + oDataL.threshold + oDataR.clearway - 70, bottomCL + 50, Color.BLACK,
                        leftOb.maxHeight + "m", true, false, 0, -10); //Left vertical label
            }

            if (rightOb != null) {
                double angle = Math.atan((rightOb.maxHeight * Airport.MinSlope * totalScale) / 100.0);
                drawLine(g, rightObPos + oDataL.threshold + oDataR.clearway, bottomCL + 75,
                        rightObPos + oDataL.threshold + oDataR.clearway - rightOb.maxHeight * Airport.MinSlope, bottomCL + 75, Color.BLACK,
                        rightOb.maxHeight + "m x 50 = " + (rightOb.maxHeight * 50) + "m", true, false,
                        - 5 - (int) (textWidth(g, rightOb.maxHeight + "m x 50 = " + (rightOb.maxHeight * 50) + "m")), 15); //Right horizontal marker
                drawLine(g, rightObPos + oDataL.threshold + oDataR.clearway, bottomCL + 60,
                        rightObPos + oDataL.threshold + oDataR.clearway, bottomCL + 95, Color.BLACK); //Right right horizontal marker
                drawLine(g, rightObPos + oDataL.threshold + oDataR.clearway - rightOb.maxHeight * Airport.MinSlope, bottomCL + 60,
                        rightObPos + oDataL.threshold + oDataR.clearway - rightOb.maxHeight * Airport.MinSlope, bottomCL + 75, Color.BLACK); //Right left horizontal marker
                drawLine(g, rightObPos + oDataL.threshold + oDataR.clearway, bottomCL + 50 - rightOb.maxHeight * vScale,
                        rightObPos + oDataL.threshold + oDataR.clearway - rightOb.maxHeight * Airport.MinSlope, bottomCL + 50, Color.BLACK); //Right slope
                drawRotatedText(g, "TOCS", (rightObPos + oDataL.threshold + oDataR.clearway) * totalScale + MARGIN - 100 * Math.sin(angle), bottomCL + 45 - rightOb.maxHeight * vScale + 100 * Math.cos(angle),
                        -(90 - Math.toDegrees(angle)), Color.BLACK); //Right slope label
                drawLine(g, rightObPos + oDataL.threshold + oDataR.clearway + 70, bottomCL + 50 - rightOb.maxHeight * vScale,
                        rightObPos + oDataL.threshold + oDataR.clearway + 70, bottomCL + 50, Color.BLACK,
                        rightOb.maxHeight + "m", true, false, -(int) textWidth(g, rightOb.maxHeight + "m"), -10); //Right vertical label
            }

            if ((oDataR.clearway + nDataL.threshold) * totalScale - 5 > textWidth(g, "Threshold")) {
                drawLine(g, oDataR.clearway + nDataL.threshold, bottomCL - maxHeight * vScale, oDataR.clearway + nDataL.threshold, bottomCL + 50, Color.BLACK, "Threshold", false, false, -5, 10); //Bottom left threshold label
            } else {
                drawLine(g, oDataR.clearway + nDataL.threshold, bottomCL - maxHeight * vScale, oDataR.clearway + nDataL.threshold, bottomCL + 50, Color.BLACK, "Threshold", true, false, 5, 10); //Bottom left threshold label
            }
            if ((oDataL.clearway + nDataR.threshold) * totalScale - 5 > textWidth(g, "Threshold")) {
                drawLine(g, oDataR.clearway + oDataL.TORA - nDataR.threshold, bottomCL - maxHeight * vScale, oDataR.clearway + oDataL.TORA - nDataR.threshold, bottomCL + 50, Color.BLACK, "Threshold", true, false, 5, 10); //Bottom right threshold label
            } else {
                drawLine(g, oDataR.clearway + oDataL.TORA - nDataR.threshold, bottomCL - maxHeight * vScale, oDataR.clearway + oDataL.TORA - nDataR.threshold, bottomCL + 50, Color.BLACK, "Threshold", false, false, -5, 10); //Bottom right threshold label
            }

        }

        g.setFill(Color.BLACK);
        g.setStroke(Color.BLACK);
        g.setLineWidth(5);
        g.strokeRect(0, 0, g.getCanvas().getWidth(), g.getCanvas().getHeight());
        g.setLineWidth(3);
        g.strokeLine(0, g.getCanvas().getHeight() * VRATIO, g.getCanvas().getWidth(), g.getCanvas().getHeight() * VRATIO);
        g.setLineWidth(1);
        g.strokeText(name, 10, 20);

    }
    
    public void drawRect(GraphicsContext g, double x, double y, double w, double h, Color f, Color s) {
        g.setLineWidth(1);
        g.setFill(f);
        g.fillRect(x * totalScale + MARGIN, y, w * totalScale, h);
        g.setLineWidth(1);
        g.setStroke(s);
        g.strokeRect(x * totalScale + MARGIN, y, w * totalScale, h);
    }

    public void drawTriangle(GraphicsContext g, double x, double y, double h, Color f, Color s) {
        g.setLineWidth(1);
        g.setFill(f);
        double[] xPoints = new double[]{(x - 50) * totalScale + MARGIN, x * totalScale + MARGIN, (x + 50) * totalScale + MARGIN};
        double[] yPoints = new double[]{y, y - h, y};
        g.fillPolygon(xPoints, yPoints, 3);
        g.setStroke(s);
        g.strokePolygon(xPoints, yPoints, 3);
    }

    public void drawLine(GraphicsContext g, double x1, double y1, double x2, double y2, Color s, String l, boolean d, boolean h, int ox, int oy) {
        g.setLineWidth(2);
        g.setStroke(s);
        g.strokeLine(x1 * totalScale + MARGIN, y1, x2 * totalScale + MARGIN, y2);
        if (h) {
            if (d) {
                g.strokeLine(x2 * totalScale + MARGIN, y2, x2 * totalScale + MARGIN - 10, y2 - 5);
                g.strokeLine(x2 * totalScale + MARGIN, y2, x2 * totalScale + MARGIN - 10, y2 + 5);
            } else {
                g.strokeLine(x2 * totalScale + MARGIN, y2, x2 * totalScale + MARGIN + 10, y2 - 5);
                g.strokeLine(x2 * totalScale + MARGIN, y2, x2 * totalScale + MARGIN + 10, y2 + 5);
            }
        }
        g.setLineWidth(1);
        if (d) {
            g.strokeText(l, x1 * totalScale + ox + MARGIN, y1 + oy);
        } else {
            g.strokeText(l, x1 * totalScale + ox - textWidth(g, l) + MARGIN, y1 + oy);
        }
    }

    public void drawLine(GraphicsContext g, double x1, double y1, double x2, double y2, Color s, Color b, String l, boolean d, boolean h, int ox, int oy) {
        g.setLineWidth(2);
        g.setFill(b);
        if (d) {
            g.fillRect(x1 * totalScale + ox + MARGIN - 2, y1 + oy - 15, textWidth(g, l) + 4, 18);
        } else {
            g.fillRect(x1 * totalScale + ox - textWidth(g, l) + MARGIN - 2, y1 + oy - 15, textWidth(g, l) + 4, 18);
        }
        g.setStroke(s);
        g.strokeLine(x1 * totalScale + MARGIN, y1, x2 * totalScale + MARGIN, y2);
        if (h) {
            if (d) {
                g.strokeLine(x2 * totalScale + MARGIN, y2, x2 * totalScale + MARGIN - 10, y2 - 5);
                g.strokeLine(x2 * totalScale + MARGIN, y2, x2 * totalScale + MARGIN - 10, y2 + 5);
            } else {
                g.strokeLine(x2 * totalScale + MARGIN, y2, x2 * totalScale + MARGIN + 10, y2 - 5);
                g.strokeLine(x2 * totalScale + MARGIN, y2, x2 * totalScale + MARGIN + 10, y2 + 5);
            }
        }
        g.setLineWidth(1);
        if (d) {
            g.strokeText(l, x1 * totalScale + ox + MARGIN, y1 + oy);
        } else {
            g.strokeText(l, x1 * totalScale + ox - textWidth(g, l) + MARGIN, y1 + oy);
        }
    }

    public void drawLine(GraphicsContext g, double x1, double y1, double x2, double y2, Color s) {
        g.setLineWidth(2);
        g.setStroke(s);
        g.strokeLine(x1 * totalScale + MARGIN, y1, x2 * totalScale + MARGIN, y2);
    }

    public double textWidth(GraphicsContext g, String s) {
        Text text = new Text(s);
        text.setFont(g.getFont());
        return text.getBoundsInLocal().getWidth();
    }

    public void drawRotatedText(GraphicsContext g, String s, double x, double y, double r, Color c) {
        g.setLineWidth(1);
        g.setStroke(c);
        g.setFill(c);
        g.save();
        g.translate(x, y);
        g.rotate(r);
        g.fillText(s, 5, 0);
        g.restore();
    }

    public Runway getRunway() {
        return runway;
    }

}
