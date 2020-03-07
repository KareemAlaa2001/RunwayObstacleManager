
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class RunwayCanvas extends Canvas {

    private final int MARGIN = 50;
    private final double VSCALE = 20.0;

    private RunwayData nData = null;
    private RunwayData oData = null;
    private String name = "Unselected";
    private List<ObstacleData> obstacles = null;
    private double originalRunwayLength = 0;
    private double originalThreshold = 0;
    private double opClearway = 0;
    private double opStopway = 0;
    private double totalScale = 0;

    public RunwayCanvas(int w, int h, RunwayOneWay runway, double opClearway, double opStopway) {
        super(w, h);
        this.oData = runway.getRunwaySpec();
        this.nData = runway.getUpdatedRunway();
        this.name = runway.getName();
        this.obstacles = runway.getObstacles();
        this.originalRunwayLength = runway.getRunwaySpec().TORA;
        this.originalThreshold = runway.getRunwaySpec().threshold;
        this.opClearway = opClearway;
        this.opStopway = opStopway;
        totalScale = (w - (MARGIN * 2)) / (opClearway + oData.TORA + oData.clearway);
    }

    public RunwayCanvas(int w, int h) {
        super(w, h);
    }

    public void render() {
        final GraphicsContext g = this.getGraphicsContext2D();
        g.setFill(Color.BLACK);
        g.setStroke(Color.BLACK);
        g.setLineWidth(5);
        g.strokeRect(0, 0, g.getCanvas().getWidth(), g.getCanvas().getHeight());
        g.setLineWidth(3);
        g.strokeLine(0, g.getCanvas().getHeight() / 2, g.getCanvas().getWidth(), g.getCanvas().getHeight() / 2);
        g.setLineWidth(1);
        g.strokeText(name, 10, 20);
        if (oData != null) {
            drawRect(g, opClearway, 350, oData.TORA, 50, Color.GRAY, Color.BLACK); // Top runway
            drawRect(g, opClearway - opStopway, 350, opStopway, 50, Color.WHITE, Color.BLACK); //Top left stopway
            drawRect(g, opClearway + oData.TORA, 350, oData.stopway, 50, Color.WHITE, Color.BLACK); //Top right stopway
            drawRect(g, 0, 340, opClearway, 70, Color.TRANSPARENT, Color.GRAY); //Top left clearway
            drawRect(g, opClearway + oData.TORA, 340, oData.clearway, 70, Color.TRANSPARENT, Color.GRAY); //Top right clearway

            g.setLineWidth(2);
            g.setStroke(Color.WHITE);
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 4; j++) {
                    g.strokeLine(55 + opClearway * totalScale, 355 + i * 25 + j * 5,
                            70 + opClearway * totalScale, 355 + i * 25 + j * 5);
                }
            }
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3; j++) {
                    g.strokeLine(115 + opClearway * totalScale, 355 + i * 30 + j * 5,
                            125 + opClearway * totalScale, 355 + i * 30 + j * 5);
                }
            }
            g.setLineWidth(1);
            g.save();
            g.translate(88 + opClearway * totalScale, 357);
            g.rotate(90);
            g.strokeText(name, 5, 0);
            g.restore();

            g.setFill(Color.GRAY);
            drawRect(g, opClearway, 725, oData.TORA, 10, Color.GRAY, Color.BLACK); //Bottom runway
            drawRect(g, opClearway - opStopway, 725, opStopway, 10, Color.WHITE, Color.BLACK); //Bottom left stopway
            drawRect(g, opClearway + oData.TORA, 725, oData.stopway, 10, Color.WHITE, Color.BLACK); //Bottom right stopway
            drawRect(g, 0, 725, opClearway, 20, Color.TRANSPARENT, Color.BLACK); //Bottom left clearway
            drawRect(g, opClearway + oData.TORA, 725, oData.clearway, 20, Color.TRANSPARENT, Color.BLACK); //Bottom left clearway

            ObstacleData leftOb = null;
            ObstacleData rightOb = null;
            for (ObstacleData ob : obstacles) {
                drawRect(g, ob.position + oData.threshold + opClearway - 50, 355, 100, 40, Color.RED, Color.BLACK); //Top obstacle
                drawTriangle(g, ob.position + oData.threshold + opClearway, 725, ob.maxHeight, Color.RED, Color.BLACK); //Top bottom obstacle
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
                drawLine(g, leftOb.position + oData.threshold + opClearway, 750,
                        leftOb.position + oData.threshold + opClearway + leftOb.maxHeight * Airport.MinSlope, 750, Color.BLACK,
                        leftOb.maxHeight + "m x 50 = " + (leftOb.maxHeight * 50) + "m", false, 5, 15); //Left horizontal label
                drawLine(g, leftOb.position + oData.threshold + opClearway, 735,
                        leftOb.position + oData.threshold + opClearway, 770, Color.BLACK); //Left left horizontal marker
                drawLine(g, leftOb.position + oData.threshold + opClearway + leftOb.maxHeight * Airport.MinSlope, 735,
                        leftOb.position + oData.threshold + opClearway + leftOb.maxHeight * Airport.MinSlope, 750, Color.BLACK); //Left right horizontal marker
                drawLine(g, leftOb.position + oData.threshold + opClearway, 725 - leftOb.maxHeight * VSCALE * totalScale,
                        leftOb.position + oData.threshold + opClearway + leftOb.maxHeight * Airport.MinSlope, 725, Color.BLACK); //Left slope
                drawLine(g, leftOb.position + oData.threshold + opClearway - 70, 725 - leftOb.maxHeight * VSCALE * totalScale,
                        leftOb.position + oData.threshold + opClearway - 70, 725, Color.BLACK,
                        leftOb.maxHeight + "m", false, 0, -10); //Left vertical label
            }

            if (rightOb != null) {
                drawLine(g, rightOb.position + oData.threshold + opClearway, 750,
                        rightOb.position + oData.threshold + opClearway - rightOb.maxHeight * Airport.MinSlope, 750, Color.BLACK,
                        rightOb.maxHeight + "m x 50 = " + (rightOb.maxHeight * 50) + "m", false,
                        - 5 - (int) (textWidth(g, rightOb.maxHeight + "m x 50 = " + (rightOb.maxHeight * 50) + "m")), 15); //Right horizontal marker
                drawLine(g, rightOb.position + oData.threshold + opClearway, 735,
                        rightOb.position + oData.threshold + opClearway, 770, Color.BLACK); //Right right horizontal marker
                drawLine(g, rightOb.position + oData.threshold + opClearway - rightOb.maxHeight * Airport.MinSlope, 735,
                        rightOb.position + oData.threshold + opClearway - rightOb.maxHeight * Airport.MinSlope, 750, Color.BLACK); //Right left horizontal marker
                drawLine(g, rightOb.position + oData.threshold + opClearway, 725 - rightOb.maxHeight * VSCALE * totalScale,
                        rightOb.position + oData.threshold + opClearway - rightOb.maxHeight * Airport.MinSlope, 725, Color.BLACK); //Right slope
                drawLine(g, rightOb.position + oData.threshold + opClearway + 70, 725 - rightOb.maxHeight * VSCALE * totalScale,
                        rightOb.position + oData.threshold + opClearway + 70, 725, Color.BLACK,
                        rightOb.maxHeight + "m", false, -(int) textWidth(g, rightOb.maxHeight + "m"), -10); //Right vertical label
            }

            int lObOffset = 0;
            if (leftOb != null) {
                lObOffset = leftOb.position;
            }

            drawLine(g, opClearway + (oData.TORA - nData.TORA), 50, opClearway + (oData.TORA - nData.TORA), 350, Color.BLACK);
            drawLine(g, opClearway + (oData.TORA - nData.TORA), 65, opClearway + (oData.TORA - nData.TORA) + nData.TODA, 65, Color.BLACK, "TODA - " + nData.TODA + "m", true, 5, -5); //TODA
            drawLine(g, opClearway + (oData.TORA - nData.TORA), 215, opClearway + oData.TORA, 215, Color.BLACK, "TORA - " + nData.TORA + "m", true, 5, -5); //TORA

        }

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
        double[] yPoints = new double[]{y, y - h * VSCALE * totalScale, y};
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

    private static double textWidth(GraphicsContext g, String s) {
        Text text = new Text(s);
        text.setFont(g.getFont());
        return text.getBoundsInLocal().getWidth();
    }
//                    g.strokeText("LDA - " + data.LDA + "m", 55 + scaledOffset + 1000 * (data.threshold / maxLength), 285);
//    g.strokeLine(50 + scaledOffset, 215, 1000 * (data.TORA / maxLength) + 50 + scaledOffset, 215); //TORA
//                    g.strokeLine(1000 * (data.TORA / maxLength) + 50 + scaledOffset, 215, 1000 * (data.TORA / maxLength) + 40 + scaledOffset, 210);
//                    g.strokeLine(1000 * (data.TORA / maxLength) + 50 + scaledOffset, 215, 1000 * (data.TORA / maxLength) + 40 + scaledOffset, 220);
//

//            double maxLength = Math.max(data.TORA,
//                    Math.max(data.TODA,
//                            Math.max(data.LDA,
//                                    Math.max(data.ASDA,
//                                            originalRunwayLength + data.clearway))));
//            maxLength += data.clearway;
//            double scaledOffset = 1000 * (data.clearway / maxLength);
//            double obscaleOffset = 1000 * ((originalRunwayLength - data.TORA) / maxLength);
//            g.setStroke(Color.BLACK);
//            g.setLineWidth(2);
//
//            for (ObstacleData obstacle : obstacles) {
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
//
//            g.setFill(Color.GRAY);
//            g.fillRect(50 + scaledOffset, 350, 1000 * (originalRunwayLength / maxLength), 50);
//            g.setLineWidth(2);
//            g.setStroke(Color.BLACK);
//            g.strokeRect(50 + scaledOffset, 350, 1000 * (originalRunwayLength / maxLength), 50);
//            g.strokeRect(50 + scaledOffset - 1000 * (data.stopway / maxLength), 350, 1000 * (data.stopway / maxLength), 50);
//            g.strokeRect(50 + scaledOffset + 1000 * (originalRunwayLength / maxLength), 350, 1000 * (data.stopway / maxLength), 50);
//            g.setLineWidth(1);
//            g.strokeRect(50, 340, scaledOffset, 70);
//            g.strokeRect(50 + scaledOffset + 1000 * (originalRunwayLength / maxLength), 340, scaledOffset, 70);
//            g.setLineWidth(2);
//            g.setStroke(Color.WHITE);
//            for (int i = 0; i < 2; i++) {
//                for (int j = 0; j < 4; j++) {
//                    g.strokeLine(55 + scaledOffset, 355 + i * 25 + j * 5,
//                            70 + scaledOffset, 355 + i * 25 + j * 5);
//                }
//            }
//            for (int i = 0; i < 2; i++) {
//                for (int j = 0; j < 3; j++) {
//                    g.strokeLine(115 + scaledOffset, 355 + i * 30 + j * 5,
//                            125 + scaledOffset, 355 + i * 30 + j * 5);
//                }
//            }
//            g.setLineWidth(1);
//            g.save();
//            g.translate(88 + scaledOffset, 357);
//            g.rotate(90);
//            g.strokeText(name, 5, 0);
//            g.restore();
//
//            g.setStroke(Color.BLACK);
//            g.setLineWidth(2);
//            for (ObstacleData obstacle : obstacles) {
//                g.setFill(Color.RED);
//                g.fillRect(1000 * ((obstacle.position + originalThreshold + 0.0) / maxLength) + 40 + scaledOffset,
//                        355,
//                        20,
//                        40);
//                g.setFill(Color.BLACK);
//                g.strokeRect(1000 * ((obstacle.position + originalThreshold + 0.0) / maxLength) + 40 + scaledOffset,
//                        355,
//                        20,
//                        40);
//            }
//
//            g.setLineWidth(2);
//            for (ObstacleData obstacle : obstacles) {
//                g.setFill(Color.RED);
//                double[] xPoints = new double[]{1000 * ((obstacle.position + originalThreshold + 0.0) / maxLength) + 40 + scaledOffset,
//                    1000 * ((obstacle.position + originalThreshold + 0.0) / maxLength) + 50 + scaledOffset,
//                    1000 * ((obstacle.position + originalThreshold + 0.0) / maxLength) + 60 + scaledOffset};
//                double[] yPoints = new double[]{725,
//                    725 - 1000 * ((obstacle.maxHeight + 0.0) / maxLength) * vScale,
//                    725};
//                g.fillPolygon(xPoints, yPoints, 3);
//                g.setFill(Color.BLACK);
//                g.strokePolygon(xPoints, yPoints, 3);
//                if ((obstacle.position + originalThreshold + 0.0) / originalRunwayLength > 0.5) {
//                    g.setLineWidth(2);
//                    g.setFill(Color.BLACK);
//                    g.strokeLine(1000 * ((obstacle.position + originalThreshold + 0.0) / maxLength) + 70 + scaledOffset, 725,
//                            1000 * ((obstacle.position + originalThreshold + 0.0) / maxLength) + 70 + scaledOffset, 725 - 1000 * ((obstacle.maxHeight + 0.0) / maxLength) * vScale);
//                    g.strokeLine(1000 * ((obstacle.position + originalThreshold + 0.0) / maxLength) + 50 + scaledOffset, 725 - 1000 * ((obstacle.maxHeight + 0.0) / maxLength) * vScale,
//                            1000 * ((obstacle.position + originalThreshold + 0.0) / maxLength) + 50 - 1000 * ((obstacle.maxHeight + 0.0) / maxLength) * Airport.MinSlope + scaledOffset, 725);
//                    g.strokeLine(1000 * ((obstacle.position + originalThreshold + 0.0) / maxLength) + 50 + scaledOffset, 725,
//                            1000 * ((obstacle.position + originalThreshold + 0.0) / maxLength) + 50 + scaledOffset, 760);
//                    g.strokeLine(1000 * ((obstacle.position + originalThreshold + 0.0) / maxLength) + 50 - 1000 * ((obstacle.maxHeight + 0.0) / maxLength) * Airport.MinSlope + scaledOffset, 725,
//                            1000 * ((obstacle.position + originalThreshold + 0.0) / maxLength) + 50 - 1000 * ((obstacle.maxHeight + 0.0) / maxLength) * Airport.MinSlope + scaledOffset, 760);
//                    g.strokeLine(1000 * ((obstacle.position + originalThreshold + 0.0) / maxLength) + 50 + scaledOffset, 750,
//                            1000 * ((obstacle.position + originalThreshold + 0.0) / maxLength) + 50 - 1000 * ((obstacle.maxHeight + 0.0) / maxLength) * Airport.MinSlope + scaledOffset, 750);
//                    g.setLineWidth(1);
//                    g.strokeText(obstacle.maxHeight + "m",
//                            1000 * ((obstacle.position + originalThreshold + 0.0) / maxLength) + 70 + scaledOffset, 725 - 1000 * ((obstacle.maxHeight + 0.0) / maxLength) * vScale - 5);
//                    g.strokeText(obstacle.maxHeight + "m x 50 = " + (obstacle.maxHeight * 50) + "m",
//                            1000 * ((obstacle.position + originalThreshold + 0.0) / maxLength) + 50 - 1000 * ((obstacle.maxHeight + 0.0) / maxLength) * Airport.MinSlope + 2 + scaledOffset, 750 + 15);
//                } else {
//                    g.setLineWidth(2);
//                    g.setFill(Color.BLACK);
//                    g.strokeLine(1000 * ((obstacle.position + originalThreshold + 0.0) / maxLength) + 30 + scaledOffset, 725, //H Label
//                            1000 * ((obstacle.position + originalThreshold + 0.0) / maxLength) + 30 + scaledOffset, 725 - 1000 * ((obstacle.maxHeight + 0.0) / maxLength) * vScale);
//                    g.strokeLine(1000 * ((obstacle.position + originalThreshold + 0.0) / maxLength) + 50 + scaledOffset, 725 - 1000 * ((obstacle.maxHeight + 0.0) / maxLength) * vScale, //Slope Label
//                            1000 * ((obstacle.position + originalThreshold + 0.0) / maxLength) + 50 + 1000 * ((obstacle.maxHeight + 0.0) / maxLength) * Airport.MinSlope + scaledOffset, 725);
//                    g.strokeLine(1000 * ((obstacle.position + originalThreshold + 0.0) / maxLength) + 50 + scaledOffset, 725, //Obstacle Position Line
//                            1000 * ((obstacle.position + originalThreshold + 0.0) / maxLength) + 50 + scaledOffset, 760);
//                    g.strokeLine(1000 * ((obstacle.position + originalThreshold + 0.0) / maxLength) + 50 + 1000 * ((obstacle.maxHeight + 0.0) / maxLength) * Airport.MinSlope + scaledOffset, 725, //Hx50 Line
//                            1000 * ((obstacle.position + originalThreshold + 0.0) / maxLength) + 50 + 1000 * ((obstacle.maxHeight + 0.0) / maxLength) * Airport.MinSlope + scaledOffset, 760);
//                    g.strokeLine(1000 * ((obstacle.position + originalThreshold + 0.0) / maxLength) + 50 + scaledOffset, 750, //Hx50 Label
//                            1000 * ((obstacle.position + originalThreshold + 0.0) / maxLength) + 50 + 1000 * ((obstacle.maxHeight + 0.0) / maxLength) * Airport.MinSlope + scaledOffset, 750);
//                    g.setLineWidth(1);
//                    g.strokeText(obstacle.maxHeight + "m",
//                            1000 * ((obstacle.position + originalThreshold + 0.0) / maxLength) + 30 + scaledOffset, 725 - 1000 * ((obstacle.maxHeight + 0.0) / maxLength) * vScale - 5);
//                    g.strokeText(obstacle.maxHeight + "m x 50 = " + (obstacle.maxHeight * 50) + "m",
//                            1000 * ((obstacle.position + originalThreshold + 0.0) / maxLength) + 50 + 2 + scaledOffset, 750 + 15);
//                }
//            }
//
//            g.setFill(Color.GRAY);
//            g.fillRect(50 + scaledOffset, 725, 1000 * (originalRunwayLength / maxLength), 10);
//            g.setLineWidth(2);
//            g.setStroke(Color.BLACK);
//            g.strokeRect(50 + scaledOffset, 725, 1000 * (originalRunwayLength / maxLength), 10);
//            
//            g.setLineWidth(2);
//            g.setStroke(Color.BLACK);
//            g.strokeRect(50 + scaledOffset - 1000 * (data.stopway / maxLength), 725, 1000 * (data.stopway / maxLength), 10);
//            g.strokeRect(50 + scaledOffset + 1000 * (originalRunwayLength / maxLength), 725, 1000 * (data.stopway / maxLength), 10);
//            g.setLineWidth(1);
//            g.strokeRect(50, 725, scaledOffset, 20);
//            g.strokeRect(50 + scaledOffset + 1000 * (originalRunwayLength / maxLength), 725, scaledOffset, 20);
}
