
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class RunwayCanvas extends Canvas {

    private final int MARGIN = 50;
    private final double VRATIO = 0.7;

    private RunwayData nData = null;
    private RunwayData oData = null;
    private String name = "Unselected";
    private List<ObstacleData> obstacles = null;
    private double opClearway = 0;
    private double opStopway = 0;
    private double totalScale = 0;
    private double gradedArea = 0;
    private double runwayVpos = 0;

    public RunwayCanvas(int w, int h, RunwayOneWay runway, double opClearway, double opStopway, double gradedArea) {
        super(w, h);
        this.oData = runway.getRunwaySpec();
        this.nData = runway.getUpdatedRunway();
        this.name = runway.getName();
        this.obstacles = runway.getObstacles();
        this.opClearway = opClearway;
        this.opStopway = opStopway;
        totalScale = (w - (MARGIN * 2)) / (opClearway + oData.TORA + oData.clearway);
        this.gradedArea = gradedArea;
        runwayVpos = this.getWidth() / 2 - 25;
    }

    public RunwayCanvas(int w, int h) {
        super(w, h);
    }

    public void render(double scale, double tx, double ty, boolean rotate) {
        final GraphicsContext g = this.getGraphicsContext2D();
        g.setFill(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        if (oData != null) {

            g.save();
            g.rect(0, 0, this.getWidth(), this.getHeight() * VRATIO);
            g.clip();

            g.save();
            g.translate(-tx, -ty);
            g.scale(scale, scale);

            double topCL = this.getHeight() * VRATIO * 0.5;

            if (rotate) {
                g.save();
                g.translate(this.getWidth() / 2, this.getWidth() / 2);
                g.rotate(Integer.parseInt(name.substring(0, 2)) * 10.0 - 90.0);
                g.translate(-this.getWidth() / 2, -this.getWidth() / 2);
            }

            g.setLineWidth(1);
            g.setFill(Color.LIGHTGRAY);
            double[] xPoints = new double[]{0,
                opClearway * totalScale + MARGIN,
                (opClearway + 200) * totalScale + MARGIN,
                (oData.TORA + opClearway - 200) * totalScale + MARGIN,
                (oData.TORA + opClearway) * totalScale + MARGIN,
                this.getWidth(),
                this.getWidth(),
                0};
            double[] yPoints = new double[]{runwayVpos - 25,
                runwayVpos - 25,
                runwayVpos + 25 - gradedArea * totalScale,
                runwayVpos + 25 - gradedArea * totalScale,
                runwayVpos - 25,
                runwayVpos - 25,
                0,
                0};
            g.fillPolygon(xPoints, yPoints, 8);
            xPoints = new double[]{0,
                opClearway * totalScale + MARGIN,
                (opClearway + 200) * totalScale + MARGIN,
                (oData.TORA + opClearway - 200) * totalScale + MARGIN,
                (oData.TORA + opClearway) * totalScale + MARGIN,
                this.getWidth(),
                this.getWidth(),
                0};
            yPoints = new double[]{runwayVpos + 75,
                runwayVpos + 75,
                runwayVpos + 25 + gradedArea * totalScale,
                runwayVpos + 25 + gradedArea * totalScale,
                runwayVpos + 75,
                runwayVpos + 75,
                this.getWidth(),
                this.getWidth()};
            g.fillPolygon(xPoints, yPoints, 8);

            drawRect(g, opClearway, runwayVpos, oData.TORA, 50, Color.GRAY, Color.BLACK); // Top runway
            drawRect(g, opClearway - opStopway, runwayVpos, opStopway, 50, Color.LIGHTGREY, Color.BLACK); //Top left stopway
            drawRect(g, opClearway + oData.TORA, runwayVpos, oData.stopway, 50, Color.LIGHTGREY, Color.BLACK); //Top right stopway
            drawRect(g, 0, runwayVpos - 10, opClearway, 70, Color.TRANSPARENT, Color.GRAY); //Top left clearway
            drawRect(g, opClearway + oData.TORA, runwayVpos - 10, oData.clearway, 70, Color.TRANSPARENT, Color.GRAY); //Top right clearway

            g.setLineWidth(2);
            g.setStroke(Color.WHITE);
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 4; j++) {
                    g.strokeLine(55 + opClearway * totalScale, runwayVpos + 5 + i * 25 + j * 5, 70 + opClearway * totalScale, runwayVpos + 5 + i * 25 + j * 5);
                }
            }
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3; j++) {
                    g.strokeLine(115 + opClearway * totalScale, runwayVpos + 5 + i * 30 + j * 5, 125 + opClearway * totalScale, runwayVpos + 5 + i * 30 + j * 5);
                }
            }
            drawRotatedText(g, name, 88 + opClearway * totalScale, runwayVpos + 7, 90, Color.WHITE);

            for (ObstacleData ob : obstacles) {
                drawRect(g, ob.position + oData.threshold + opClearway - 50, runwayVpos + 5, 100, 40, Color.RED, Color.BLACK); //Top obstacle
            }

            drawLine(g, opClearway + (oData.TORA - nData.TORA), 235 + MARGIN, opClearway + (oData.TORA - nData.TORA), runwayVpos, Color.BLACK);
            drawLine(g, opClearway + nData.threshold, runwayVpos + 50, opClearway + nData.threshold, runwayVpos + 70, Color.BLACK, "Threshold", false, 5, 20); //Top threshold label

            g.restore();
            g.restore();
            g.restore();

            double bottomCL = this.getHeight() * (1.0 - VRATIO) * 0.5 + this.getHeight() * VRATIO;

            g.setFill(Color.GRAY);
            drawRect(g, opClearway, bottomCL + 50, oData.TORA, 10, Color.GRAY, Color.BLACK); //Bottom runway
            drawRect(g, opClearway - opStopway, bottomCL + 50, opStopway, 10, Color.LIGHTGREY, Color.BLACK); //Bottom left stopway
            drawRect(g, opClearway + oData.TORA, bottomCL + 50, oData.stopway, 10, Color.LIGHTGREY, Color.BLACK); //Bottom right stopway
            drawRect(g, 0, bottomCL + 50, opClearway, 20, Color.TRANSPARENT, Color.BLACK); //Bottom left clearway
            drawRect(g, opClearway + oData.TORA, bottomCL + 50, oData.clearway, 20, Color.TRANSPARENT, Color.BLACK); //Bottom left clearway

            g.setLineWidth(2);
            g.setStroke(Color.WHITE);
            for (int i = 0; i < 2; i++) {
                g.strokeLine(55 + opClearway * totalScale, bottomCL + 52 + i * 3, 70 + opClearway * totalScale, bottomCL + 52 + i * 3);
            }
            g.strokeLine(115 + opClearway * totalScale, bottomCL + 52, 125 + opClearway * totalScale, bottomCL + 52);

            double maxHeight = 0.0;
            for (ObstacleData ob : obstacles) {
                maxHeight = Math.max(maxHeight, ob.maxHeight);
            }
            double vScale = 100.0 / maxHeight;

            ObstacleData leftOb = null;
            ObstacleData rightOb = null;
            for (ObstacleData ob : obstacles) {
                drawTriangle(g, ob.position + oData.threshold + opClearway, bottomCL + 50, ob.maxHeight * vScale, Color.RED, Color.BLACK); //Bottom obstacle
                if (ob.position > oData.TORA / 2) {
                    if (rightOb == null || (ob.position - ob.maxHeight * Airport.MinSlope) < (rightOb.position - rightOb.maxHeight * Airport.MinSlope)) {
                        rightOb = ob;
                    }
                } else {
                    if (leftOb == null || (ob.position + ob.maxHeight * Airport.MinSlope) > (leftOb.position + leftOb.maxHeight * Airport.MinSlope)) {
                        leftOb = ob;
                    }
                }
            }

            if (leftOb != null) {
                double angle = Math.atan((leftOb.maxHeight * Airport.MinSlope * totalScale) / 100.0);
                drawLine(g, leftOb.position + oData.threshold + opClearway, bottomCL + 75,
                        leftOb.position + oData.threshold + opClearway + leftOb.maxHeight * Airport.MinSlope, bottomCL + 75, Color.BLACK,
                        leftOb.maxHeight + "m x 50 = " + (leftOb.maxHeight * 50) + "m", false, 5, 15); //Left horizontal label
                drawLine(g, leftOb.position + oData.threshold + opClearway, bottomCL + 60,
                        leftOb.position + oData.threshold + opClearway, bottomCL + 95, Color.BLACK); //Left left horizontal marker
                drawLine(g, leftOb.position + oData.threshold + opClearway + leftOb.maxHeight * Airport.MinSlope, bottomCL + 60,
                        leftOb.position + oData.threshold + opClearway + leftOb.maxHeight * Airport.MinSlope, bottomCL + 75, Color.BLACK); //Left right horizontal marker
                drawLine(g, leftOb.position + oData.threshold + opClearway, bottomCL + 50 - leftOb.maxHeight * vScale,
                        leftOb.position + oData.threshold + opClearway + leftOb.maxHeight * Airport.MinSlope, bottomCL + 50, Color.BLACK); //Left slope
                drawRotatedText(g, "            ALS", (leftOb.position + oData.threshold + opClearway) * totalScale + MARGIN, bottomCL + 45 - leftOb.maxHeight * vScale,
                        90 - Math.toDegrees(angle), Color.BLACK); //Left slope label
                drawLine(g, leftOb.position + oData.threshold + opClearway - 70, bottomCL + 50 - leftOb.maxHeight * vScale,
                        leftOb.position + oData.threshold + opClearway - 70, bottomCL + 50, Color.BLACK,
                        leftOb.maxHeight + "m", false, 0, -10); //Left vertical label
            }

            if (rightOb != null) {
                double angle = Math.atan((rightOb.maxHeight * Airport.MinSlope * totalScale) / 100.0);
                drawLine(g, rightOb.position + oData.threshold + opClearway, bottomCL + 75,
                        rightOb.position + oData.threshold + opClearway - rightOb.maxHeight * Airport.MinSlope, bottomCL + 75, Color.BLACK,
                        rightOb.maxHeight + "m x 50 = " + (rightOb.maxHeight * 50) + "m", false,
                        - 5 - (int) (textWidth(g, rightOb.maxHeight + "m x 50 = " + (rightOb.maxHeight * 50) + "m")), 15); //Right horizontal marker
                drawLine(g, rightOb.position + oData.threshold + opClearway, bottomCL + 60,
                        rightOb.position + oData.threshold + opClearway, bottomCL + 95, Color.BLACK); //Right right horizontal marker
                drawLine(g, rightOb.position + oData.threshold + opClearway - rightOb.maxHeight * Airport.MinSlope, bottomCL + 60,
                        rightOb.position + oData.threshold + opClearway - rightOb.maxHeight * Airport.MinSlope, bottomCL + 75, Color.BLACK); //Right left horizontal marker
                drawLine(g, rightOb.position + oData.threshold + opClearway, bottomCL + 50 - rightOb.maxHeight * vScale,
                        rightOb.position + oData.threshold + opClearway - rightOb.maxHeight * Airport.MinSlope, bottomCL + 50, Color.BLACK); //Right slope
                drawRotatedText(g, "TOCS", (rightOb.position + oData.threshold + opClearway) * totalScale + MARGIN - 100 * Math.sin(angle), bottomCL + 45 - rightOb.maxHeight * vScale + 100 * Math.cos(angle),
                        -(90 - Math.toDegrees(angle)), Color.BLACK); //Right slope label
                drawLine(g, rightOb.position + oData.threshold + opClearway + 70, bottomCL + 50 - rightOb.maxHeight * vScale,
                        rightOb.position + oData.threshold + opClearway + 70, bottomCL + 50, Color.BLACK,
                        rightOb.maxHeight + "m", false, -(int) textWidth(g, rightOb.maxHeight + "m"), -10); //Right vertical label
            }

            drawLine(g, opClearway + nData.threshold, bottomCL + 30, opClearway + nData.threshold, bottomCL + 50, Color.BLACK, "Threshold", false, 5, 10); //Bottom threshold label

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

    public void drawLine(GraphicsContext g, double x1, double y1, double x2, double y2, Color s, String l, boolean h, int ox, int oy) {
        g.setLineWidth(2);
        g.setStroke(s);
        g.strokeLine(x1 * totalScale + MARGIN, y1, x2 * totalScale + MARGIN, y2);
        if (h) {
            g.strokeLine(x2 * totalScale + MARGIN, y2, x2 * totalScale + MARGIN - 10, y2 - 5);
            g.strokeLine(x2 * totalScale + MARGIN, y2, x2 * totalScale + MARGIN - 10, y2 + 5);
        }
        g.setLineWidth(1);
        g.strokeText(l, x1 * totalScale + ox + MARGIN, y1 + oy);
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
        g.save();
        g.translate(x, y);
        g.rotate(r);
        g.strokeText(s, 5, 0);
        g.restore();
    }

//                if ((obstacle.position + 0.0) / originalRunwayLength > 0.5) {
//                    g.strokeLine(50 + scaledOffset, 50, 50 + scaledOffset, 350);
//                    g.strokeLine(50 + scaledOffset, 65, 1000 * (data.TODA / maxLength) + 50 + scaledOffset, 65); //TODA
//                    g.strokeLine(1000 * (data.TODA / maxLength) + 50 + scaledOffset, 65, 1000 * (data.TODA / maxLength) + 40 + scaledOffset, 60);
//                    g.strokeLine(1000 * (data.TODA / maxLength) + 50 + scaledOffset, 65, 1000 * (data.TODA / maxLength) + 40 + scaledOffset, 70);
//
//                    g.strokeLine(50 + scaledOffset, 140, 1000 * (data.ASDA / maxLength) + 50 + scaledOffset, 140); //ASDA
//                    g.strokeLine(1000 * (data.ASDA / maxLength) + 50 + scaledOffset, 140, 1000 * (data.ASDA / maxLength) + 40 + scaledOffset, 135);
//                    g.strokeLine(1000 * (data.ASDA / maxLength) + 50 + scaledOffset, 140, 1000 * (data.ASDA / maxLength) + 40 + scaledOffset, 145);
//
//                    g.strokeLine(50 + scaledOffset, 215, 1000 * (data.TORA / maxLength) + 50 + scaledOffset, 215); //TORA
//                    g.strokeLine(1000 * (data.TORA / maxLength) + 50 + scaledOffset, 215, 1000 * (data.TORA / maxLength) + 40 + scaledOffset, 210);
//                    g.strokeLine(1000 * (data.TORA / maxLength) + 50 + scaledOffset, 215, 1000 * (data.TORA / maxLength) + 40 + scaledOffset, 220);
//
//                    g.strokeLine(50 + scaledOffset + 1000 * (data.threshold / maxLength), 275, 50 + scaledOffset + 1000 * (data.threshold / maxLength), 350);
//                    g.strokeLine(50 + scaledOffset + 1000 * (data.threshold / maxLength), 290, 1000 * (data.LDA / maxLength) + 50 + 1000 * (data.threshold / maxLength) + scaledOffset, 290); //LDA
//                    g.strokeLine(1000 * (data.LDA / maxLength) + 50 + 1000 * (data.threshold / maxLength) + scaledOffset, 290, 1000 * (data.LDA / maxLength) + 40 + 1000 * (data.threshold / maxLength) + scaledOffset, 285);
//                    g.strokeLine(1000 * (data.LDA / maxLength) + 50 + 1000 * (data.threshold / maxLength) + scaledOffset, 290, 1000 * (data.LDA / maxLength) + 40 + 1000 * (data.threshold / maxLength) + scaledOffset, 295);
//
//                    g.setLineWidth(1);
//                    g.strokeText("TODA - " + data.TODA + "m", 55 + scaledOffset, 60);
//                    g.strokeText("ASDA - " + data.ASDA + "m", 55 + scaledOffset, 135);
//                    g.strokeText("TORA - " + data.TORA + "m", 55 + scaledOffset, 210);
//                    g.strokeText("LDA - " + data.LDA + "m", 55 + scaledOffset + 1000 * (data.threshold / maxLength), 285);
//
//                } else {
//                    
//                    g.strokeLine(50 + scaledOffset + obscaleOffset, 50, 50 + scaledOffset + obscaleOffset, 350);
//                    g.strokeLine(50 + scaledOffset + obscaleOffset, 65, 1000 * (data.TODA / maxLength) + 50 + scaledOffset + obscaleOffset, 65); //TODA
//                    g.strokeLine(1000 * (data.TODA / maxLength) + 50 + scaledOffset + obscaleOffset, 65, 1000 * (data.TODA / maxLength) + 40 + scaledOffset + obscaleOffset, 60);
//                    g.strokeLine(1000 * (data.TODA / maxLength) + 50 + scaledOffset + obscaleOffset, 65, 1000 * (data.TODA / maxLength) + 40 + scaledOffset + obscaleOffset, 70);
//
//                    g.strokeLine(50 + scaledOffset + obscaleOffset, 140, 1000 * (data.ASDA / maxLength) + 50 + scaledOffset + obscaleOffset, 140); //ASDA
//                    g.strokeLine(1000 * (data.ASDA / maxLength) + 50 + scaledOffset + obscaleOffset, 140, 1000 * (data.ASDA / maxLength) + 40 + scaledOffset + obscaleOffset, 135);
//                    g.strokeLine(1000 * (data.ASDA / maxLength) + 50 + scaledOffset + obscaleOffset, 140, 1000 * (data.ASDA / maxLength) + 40 + scaledOffset + obscaleOffset, 145);
//
//                    g.strokeLine(50 + scaledOffset + obscaleOffset, 215, 1000 * (data.TORA / maxLength) + 50 + scaledOffset + obscaleOffset, 215); //TORA
//                    g.strokeLine(1000 * (data.TORA / maxLength) + 50 + scaledOffset + obscaleOffset, 215, 1000 * (data.TORA / maxLength) + 40 + scaledOffset + obscaleOffset, 210);
//                    g.strokeLine(1000 * (data.TORA / maxLength) + 50 + scaledOffset + obscaleOffset, 215, 1000 * (data.TORA / maxLength) + 40 + scaledOffset + obscaleOffset, 220);
//
//                    g.strokeLine(50 + scaledOffset + 1000 * ((data.threshold + (data.TORA - data.LDA)) / maxLength), 275, 50 + scaledOffset + 1000 * ((data.threshold + (data.TORA - data.LDA)) / maxLength), 350);
//                    g.strokeLine(50 + scaledOffset + 1000 * ((data.threshold + (data.TORA - data.LDA)) / maxLength), 290, 1000 * (data.LDA / maxLength) + 50 + 1000 * ((data.threshold + (data.TORA - data.LDA)) / maxLength) + scaledOffset, 290); //LDA
//                    g.strokeLine(1000 * (data.LDA / maxLength) + 50 + 1000 * ((data.threshold + (data.TORA - data.LDA)) / maxLength) + scaledOffset, 290, 1000 * (data.LDA / maxLength) + 40 + 1000 * ((data.threshold + (data.TORA - data.LDA)) / maxLength) + scaledOffset, 285);
//                    g.strokeLine(1000 * (data.LDA / maxLength) + 50 + 1000 * ((data.threshold + (data.TORA - data.LDA)) / maxLength) + scaledOffset, 290, 1000 * (data.LDA / maxLength) + 40 + 1000 * ((data.threshold + (data.TORA - data.LDA)) / maxLength) + scaledOffset, 295);
//
//                    g.setLineWidth(1);
//                    g.strokeText("TODA - " + data.TODA + "m", 55 + scaledOffset + obscaleOffset, 60);
//                    g.strokeText("ASDA - " + data.ASDA + "m", 55 + scaledOffset + obscaleOffset, 135);
//                    g.strokeText("TORA - " + data.TORA + "m", 55 + scaledOffset + obscaleOffset, 210);
//                    g.strokeText("LDA - " + data.LDA + "m", 55 + scaledOffset + 1000 * ((data.threshold + (data.TORA - data.LDA)) / maxLength), 285);
//                }
//
//            }
}
