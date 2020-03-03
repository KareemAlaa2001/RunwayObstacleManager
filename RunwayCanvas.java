
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RunwayCanvas extends Canvas {

    private RunwayData data;
    private String name;
    private List<ObstacleData> obstacles;
    private double originalRunwayLength;

    RunwayCanvas(int w, int h, RunwayData data, String name, List<ObstacleData> obstacles, double originalRunwayLength) {
        super(w, h);
        this.data = data;
        this.name = name;
        this.obstacles = obstacles;
        this.originalRunwayLength = originalRunwayLength;
    }

    public void render() {
        final GraphicsContext g = this.getGraphicsContext2D();
        g.setFill(Color.BLACK);
        g.setLineWidth(5);
        g.strokeRect(0, 0, g.getCanvas().getWidth(), g.getCanvas().getHeight());
        g.setLineWidth(3);
        g.strokeLine(0, g.getCanvas().getHeight() / 2, g.getCanvas().getWidth(), g.getCanvas().getHeight() / 2);
        g.setLineWidth(1);
        g.strokeText(name, 10, 20);
        if (data != null) {
            double maxLength = Math.max(data.TORA,
                    Math.max(data.TODA,
                            Math.max(data.LDA,
                                    Math.max(data.ASDA,
                                            originalRunwayLength + data.clearway))));
            maxLength += data.clearway;
            double scaledOffset = 1000 * (data.clearway / maxLength);
            double obscaleOffset = 1000 * ((originalRunwayLength - data.TORA) / maxLength);
            g.setStroke(Color.BLACK);
            g.setLineWidth(2);

            for (ObstacleData obstacle : obstacles) {
                if ((obstacle.position + 0.0) / originalRunwayLength > 0.5) {
                    g.strokeLine(50 + scaledOffset, 50, 50 + scaledOffset, 350);
                    g.strokeLine(50 + scaledOffset, 65, 1000 * (data.TODA / maxLength) + 50 + scaledOffset, 65); //TODA
                    g.strokeLine(1000 * (data.TODA / maxLength) + 50 + scaledOffset, 65, 1000 * (data.TODA / maxLength) + 40 + scaledOffset, 60);
                    g.strokeLine(1000 * (data.TODA / maxLength) + 50 + scaledOffset, 65, 1000 * (data.TODA / maxLength) + 40 + scaledOffset, 70);

                    g.strokeLine(50 + scaledOffset, 140, 1000 * (data.ASDA / maxLength) + 50 + scaledOffset, 140); //ASDA
                    g.strokeLine(1000 * (data.ASDA / maxLength) + 50 + scaledOffset, 140, 1000 * (data.ASDA / maxLength) + 40 + scaledOffset, 135);
                    g.strokeLine(1000 * (data.ASDA / maxLength) + 50 + scaledOffset, 140, 1000 * (data.ASDA / maxLength) + 40 + scaledOffset, 145);

                    g.strokeLine(50 + scaledOffset, 215, 1000 * (data.TORA / maxLength) + 50 + scaledOffset, 215); //TORA
                    g.strokeLine(1000 * (data.TORA / maxLength) + 50 + scaledOffset, 215, 1000 * (data.TORA / maxLength) + 40 + scaledOffset, 210);
                    g.strokeLine(1000 * (data.TORA / maxLength) + 50 + scaledOffset, 215, 1000 * (data.TORA / maxLength) + 40 + scaledOffset, 220);

                    g.strokeLine(50 + scaledOffset + 1000 * (data.threshold / maxLength), 275, 50 + scaledOffset + 1000 * (data.threshold / maxLength), 350);
                    g.strokeLine(50 + scaledOffset + 1000 * (data.threshold / maxLength), 290, 1000 * (data.LDA / maxLength) + 50 + 1000 * (data.threshold / maxLength) + scaledOffset, 290); //LDA
                    g.strokeLine(1000 * (data.LDA / maxLength) + 50 + 1000 * (data.threshold / maxLength) + scaledOffset, 290, 1000 * (data.LDA / maxLength) + 40 + 1000 * (data.threshold / maxLength) + scaledOffset, 285);
                    g.strokeLine(1000 * (data.LDA / maxLength) + 50 + 1000 * (data.threshold / maxLength) + scaledOffset, 290, 1000 * (data.LDA / maxLength) + 40 + 1000 * (data.threshold / maxLength) + scaledOffset, 295);

                    g.setLineWidth(1);
                    g.strokeText("TODA - " + data.TODA + "m", 55 + scaledOffset, 60);
                    g.strokeText("ASDA - " + data.ASDA + "m", 55 + scaledOffset, 135);
                    g.strokeText("TORA - " + data.TORA + "m", 55 + scaledOffset, 210);
                    g.strokeText("LDA - " + data.LDA + "m", 55 + scaledOffset + 1000 * (data.threshold / maxLength), 285);

                } else {
                    
                    g.strokeLine(50 + scaledOffset + obscaleOffset, 50, 50 + scaledOffset + obscaleOffset, 350);
                    g.strokeLine(50 + scaledOffset + obscaleOffset, 65, 1000 * (data.TODA / maxLength) + 50 + scaledOffset + obscaleOffset, 65); //TODA
                    g.strokeLine(1000 * (data.TODA / maxLength) + 50 + scaledOffset + obscaleOffset, 65, 1000 * (data.TODA / maxLength) + 40 + scaledOffset + obscaleOffset, 60);
                    g.strokeLine(1000 * (data.TODA / maxLength) + 50 + scaledOffset + obscaleOffset, 65, 1000 * (data.TODA / maxLength) + 40 + scaledOffset + obscaleOffset, 70);

                    g.strokeLine(50 + scaledOffset + obscaleOffset, 140, 1000 * (data.ASDA / maxLength) + 50 + scaledOffset + obscaleOffset, 140); //ASDA
                    g.strokeLine(1000 * (data.ASDA / maxLength) + 50 + scaledOffset + obscaleOffset, 140, 1000 * (data.ASDA / maxLength) + 40 + scaledOffset + obscaleOffset, 135);
                    g.strokeLine(1000 * (data.ASDA / maxLength) + 50 + scaledOffset + obscaleOffset, 140, 1000 * (data.ASDA / maxLength) + 40 + scaledOffset + obscaleOffset, 145);

                    g.strokeLine(50 + scaledOffset + obscaleOffset, 215, 1000 * (data.TORA / maxLength) + 50 + scaledOffset + obscaleOffset, 215); //TORA
                    g.strokeLine(1000 * (data.TORA / maxLength) + 50 + scaledOffset + obscaleOffset, 215, 1000 * (data.TORA / maxLength) + 40 + scaledOffset + obscaleOffset, 210);
                    g.strokeLine(1000 * (data.TORA / maxLength) + 50 + scaledOffset + obscaleOffset, 215, 1000 * (data.TORA / maxLength) + 40 + scaledOffset + obscaleOffset, 220);

                    g.strokeLine(50 + scaledOffset + 1000 * ((data.threshold + (data.TORA - data.LDA)) / maxLength), 275, 50 + scaledOffset + 1000 * ((data.threshold + (data.TORA - data.LDA)) / maxLength), 350);
                    g.strokeLine(50 + scaledOffset + 1000 * ((data.threshold + (data.TORA - data.LDA)) / maxLength), 290, 1000 * (data.LDA / maxLength) + 50 + 1000 * ((data.threshold + (data.TORA - data.LDA)) / maxLength) + scaledOffset, 290); //LDA
                    g.strokeLine(1000 * (data.LDA / maxLength) + 50 + 1000 * ((data.threshold + (data.TORA - data.LDA)) / maxLength) + scaledOffset, 290, 1000 * (data.LDA / maxLength) + 40 + 1000 * ((data.threshold + (data.TORA - data.LDA)) / maxLength) + scaledOffset, 285);
                    g.strokeLine(1000 * (data.LDA / maxLength) + 50 + 1000 * ((data.threshold + (data.TORA - data.LDA)) / maxLength) + scaledOffset, 290, 1000 * (data.LDA / maxLength) + 40 + 1000 * ((data.threshold + (data.TORA - data.LDA)) / maxLength) + scaledOffset, 295);

                    g.setLineWidth(1);
                    g.strokeText("TODA - " + data.TODA + "m", 55 + scaledOffset + obscaleOffset, 60);
                    g.strokeText("ASDA - " + data.ASDA + "m", 55 + scaledOffset + obscaleOffset, 135);
                    g.strokeText("TORA - " + data.TORA + "m", 55 + scaledOffset + obscaleOffset, 210);
                    g.strokeText("LDA - " + data.LDA + "m", 55 + scaledOffset + 1000 * ((data.threshold + (data.TORA - data.LDA)) / maxLength), 285);
                }

            }

            g.setFill(Color.GRAY);
            g.fillRect(50 + scaledOffset, 350, 1000 * (originalRunwayLength / maxLength), 50);
            g.setLineWidth(2);
            g.setStroke(Color.BLACK);
            g.strokeRect(50 + scaledOffset, 350, 1000 * (originalRunwayLength / maxLength), 50);
            g.strokeRect(50 + scaledOffset - 1000 * (data.stopway / maxLength), 350, 1000 * (data.stopway / maxLength), 50);
            g.strokeRect(50 + scaledOffset + 1000 * (originalRunwayLength / maxLength), 350, 1000 * (data.stopway / maxLength), 50);
            g.setLineWidth(1);
            g.strokeRect(50, 340, scaledOffset, 70);
            g.strokeRect(50 + scaledOffset + 1000 * (originalRunwayLength / maxLength), 340, scaledOffset, 70);
            g.setLineWidth(2);
            g.setStroke(Color.WHITE);
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 4; j++) {
                    g.strokeLine(55 + scaledOffset, 355 + i * 25 + j * 5,
                            70 + scaledOffset, 355 + i * 25 + j * 5);
                }
            }
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3; j++) {
                    g.strokeLine(115 + scaledOffset, 355 + i * 30 + j * 5,
                            125 + scaledOffset, 355 + i * 30 + j * 5);
                }
            }
            g.setLineWidth(1);
            g.save();
            g.translate(88 + scaledOffset, 357);
            g.rotate(90);
            g.strokeText(name, 5, 0);
            g.restore();

            g.setStroke(Color.BLACK);
            g.setLineWidth(2);
            for (ObstacleData obstacle : obstacles) {
                g.setFill(Color.RED);
                g.fillRect(1000 * ((obstacle.position + data.threshold + 0.0) / maxLength) + 40 + scaledOffset,
                        355,
                        20,
                        40);
                g.setFill(Color.BLACK);
                g.strokeRect(1000 * ((obstacle.position + data.threshold + 0.0) / maxLength) + 40 + scaledOffset,
                        355,
                        20,
                        40);
            }

            g.setLineWidth(2);
            double vScale = 20.0;
            for (ObstacleData obstacle : obstacles) {
                g.setFill(Color.RED);
                double[] xPoints = new double[]{1000 * ((obstacle.position + data.threshold + 0.0) / maxLength) + 40 + scaledOffset,
                    1000 * ((obstacle.position + data.threshold + 0.0) / maxLength) + 50 + scaledOffset,
                    1000 * ((obstacle.position + data.threshold + 0.0) / maxLength) + 60 + scaledOffset};
                double[] yPoints = new double[]{725,
                    725 - 1000 * ((obstacle.maxHeight + 0.0) / maxLength) * vScale,
                    725};
                g.fillPolygon(xPoints, yPoints, 3);
                g.setFill(Color.BLACK);
                g.strokePolygon(xPoints, yPoints, 3);
                if ((obstacle.position + data.threshold + 0.0) / originalRunwayLength > 0.5) {
                    g.setLineWidth(2);
                    g.setFill(Color.BLACK);
                    g.strokeLine(1000 * ((obstacle.position + data.threshold + 0.0) / maxLength) + 70 + scaledOffset, 725,
                            1000 * ((obstacle.position + data.threshold + 0.0) / maxLength) + 70 + scaledOffset, 725 - 1000 * ((obstacle.maxHeight + 0.0) / maxLength) * vScale);
                    g.strokeLine(1000 * ((obstacle.position + data.threshold + 0.0) / maxLength) + 50 + scaledOffset, 725 - 1000 * ((obstacle.maxHeight + 0.0) / maxLength) * vScale,
                            1000 * ((obstacle.position + data.threshold + 0.0) / maxLength) + 50 - 1000 * ((obstacle.maxHeight + 0.0) / maxLength) * Airport.MinSlope + scaledOffset, 725);
                    g.strokeLine(1000 * ((obstacle.position + data.threshold + 0.0) / maxLength) + 50 + scaledOffset, 725,
                            1000 * ((obstacle.position + data.threshold + 0.0) / maxLength) + 50 + scaledOffset, 760);
                    g.strokeLine(1000 * ((obstacle.position + data.threshold + 0.0) / maxLength) + 50 - 1000 * ((obstacle.maxHeight + 0.0) / maxLength) * Airport.MinSlope + scaledOffset, 725,
                            1000 * ((obstacle.position + data.threshold + 0.0) / maxLength) + 50 - 1000 * ((obstacle.maxHeight + 0.0) / maxLength) * Airport.MinSlope + scaledOffset, 760);
                    g.strokeLine(1000 * ((obstacle.position + data.threshold + 0.0) / maxLength) + 50 + scaledOffset, 750,
                            1000 * ((obstacle.position + data.threshold + 0.0) / maxLength) + 50 - 1000 * ((obstacle.maxHeight + 0.0) / maxLength) * Airport.MinSlope + scaledOffset, 750);
                    g.setLineWidth(1);
                    g.strokeText(obstacle.maxHeight + "m",
                            1000 * ((obstacle.position + data.threshold + 0.0) / maxLength) + 70 + scaledOffset, 725 - 1000 * ((obstacle.maxHeight + 0.0) / maxLength) * vScale - 5);
                    g.strokeText(obstacle.maxHeight + "m x 50 = " + (obstacle.maxHeight * 50) + "m",
                            1000 * ((obstacle.position + data.threshold + 0.0) / maxLength) + 50 - 1000 * ((obstacle.maxHeight + 0.0) / maxLength) * Airport.MinSlope + 2 + scaledOffset, 750 + 15);
                } else {
                    g.setLineWidth(2);
                    g.setFill(Color.BLACK);
                    g.strokeLine(1000 * ((obstacle.position + data.threshold + 0.0) / maxLength) + 30 + scaledOffset, 725, //H Label
                            1000 * ((obstacle.position + data.threshold + 0.0) / maxLength) + 30 + scaledOffset, 725 - 1000 * ((obstacle.maxHeight + 0.0) / maxLength) * vScale);
                    g.strokeLine(1000 * ((obstacle.position + data.threshold + 0.0) / maxLength) + 50 + scaledOffset, 725 - 1000 * ((obstacle.maxHeight + 0.0) / maxLength) * vScale, //Slope Label
                            1000 * ((obstacle.position + data.threshold + 0.0) / maxLength) + 50 + 1000 * ((obstacle.maxHeight + 0.0) / maxLength) * Airport.MinSlope + scaledOffset, 725);
                    g.strokeLine(1000 * ((obstacle.position + data.threshold + 0.0) / maxLength) + 50 + scaledOffset, 725, //Obstacle Position Line
                            1000 * ((obstacle.position + data.threshold + 0.0) / maxLength) + 50 + scaledOffset, 760);
                    g.strokeLine(1000 * ((obstacle.position + data.threshold + 0.0) / maxLength) + 50 + 1000 * ((obstacle.maxHeight + 0.0) / maxLength) * Airport.MinSlope + scaledOffset, 725, //Hx50 Line
                            1000 * ((obstacle.position + data.threshold + 0.0) / maxLength) + 50 + 1000 * ((obstacle.maxHeight + 0.0) / maxLength) * Airport.MinSlope + scaledOffset, 760);
                    g.strokeLine(1000 * ((obstacle.position + data.threshold + 0.0) / maxLength) + 50 + scaledOffset, 750, //Hx50 Label
                            1000 * ((obstacle.position + data.threshold + 0.0) / maxLength) + 50 + 1000 * ((obstacle.maxHeight + 0.0) / maxLength) * Airport.MinSlope + scaledOffset, 750);
                    g.setLineWidth(1);
                    g.strokeText(obstacle.maxHeight + "m",
                            1000 * ((obstacle.position + data.threshold + 0.0) / maxLength) + 30 + scaledOffset, 725 - 1000 * ((obstacle.maxHeight + 0.0) / maxLength) * vScale - 5);
                    g.strokeText(obstacle.maxHeight + "m x 50 = " + (obstacle.maxHeight * 50) + "m",
                            1000 * ((obstacle.position + data.threshold + 0.0) / maxLength) + 50 + 2 + scaledOffset, 750 + 15);
                }
            }

            g.setFill(Color.GRAY);
            g.fillRect(50 + scaledOffset, 725, 1000 * (originalRunwayLength / maxLength), 10);
            g.setLineWidth(2);
            g.setStroke(Color.BLACK);
            g.strokeRect(50 + scaledOffset, 725, 1000 * (originalRunwayLength / maxLength), 10);
            
            g.setLineWidth(2);
            g.setStroke(Color.BLACK);
            g.strokeRect(50 + scaledOffset - 1000 * (data.stopway / maxLength), 725, 1000 * (data.stopway / maxLength), 10);
            g.strokeRect(50 + scaledOffset + 1000 * (originalRunwayLength / maxLength), 725, 1000 * (data.stopway / maxLength), 10);
            g.setLineWidth(1);
            g.strokeRect(50, 725, scaledOffset, 20);
            g.strokeRect(50 + scaledOffset + 1000 * (originalRunwayLength / maxLength), 725, scaledOffset, 20);
        }
    }

}
