
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RunwayCanvas extends Canvas {

    private RunwayData data;
    private String name;
    private List<ObstacleData> obstacles;
    private Airport airport;

    RunwayCanvas(int w, int h, RunwayData data, String name, List<ObstacleData> obstacles, Airport airport) {
        super(w, h);
        this.data = data;
        this.name = name;
        this.obstacles = obstacles;
        this.airport = airport;
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
                                    data.ASDA)));
            maxLength += data.clearway;
            double scaledOffset = 1000 * (data.clearway / maxLength);
            g.setStroke(Color.BLACK);
            g.setLineWidth(2);
            g.strokeLine(50 + scaledOffset, 50, 50 + scaledOffset, 350);
            g.strokeLine(50 + scaledOffset, 65 , 1000 * (data.TODA / maxLength) + 50 + scaledOffset, 65 ); //TODA
            g.strokeLine(1000 * (data.TODA / maxLength) + 50 + scaledOffset, 65, 1000 * (data.TODA / maxLength) + 40 + scaledOffset, 60);
            g.strokeLine(1000 * (data.TODA / maxLength) + 50 + scaledOffset, 65, 1000 * (data.TODA / maxLength) + 40 + scaledOffset, 70);
                    
            g.strokeLine(50 + scaledOffset, 140, 1000 * (data.ASDA / maxLength) + 50 + scaledOffset, 140); //ASDA
            g.strokeLine(1000 * (data.ASDA / maxLength) + 50 + scaledOffset, 140, 1000 * (data.ASDA / maxLength) + 40 + scaledOffset, 135);
            g.strokeLine(1000 * (data.ASDA / maxLength) + 50 + scaledOffset, 140, 1000 * (data.ASDA / maxLength) + 40 + scaledOffset, 145);
            
            g.strokeLine(50 + scaledOffset, 215, 1000 * (data.TORA / maxLength) + 50 + scaledOffset, 215); //TORA
            g.strokeLine(1000 * (data.TORA / maxLength) + 50 + scaledOffset, 215, 1000 * (data.TORA / maxLength) + 40 + scaledOffset, 210);
            g.strokeLine(1000 * (data.TORA / maxLength) + 50 + scaledOffset, 215, 1000 * (data.TORA / maxLength) + 40 + scaledOffset, 220);
            
            g.strokeLine(50 + scaledOffset, 290, 1000 * (data.LDA  / maxLength) + 50 + scaledOffset, 290); //LDA
            g.strokeLine(1000 * (data.LDA / maxLength) + 50 + scaledOffset, 290, 1000 * (data.LDA / maxLength) + 40 + scaledOffset, 285);
            g.strokeLine(1000 * (data.LDA / maxLength) + 50 + scaledOffset, 290, 1000 * (data.LDA / maxLength) + 40 + scaledOffset, 295);
            
            g.setLineWidth(1);
            g.strokeText("TODA - " + data.TODA + "m", 55 + scaledOffset, 60);
            g.strokeText("ASDA - " + data.ASDA + "m", 55 + scaledOffset, 135);
            g.strokeText("TORA - " + data.TORA + "m", 55 + scaledOffset, 210);
            g.strokeText("LDA - " + data.LDA + "m", 55 + scaledOffset, 285);

            
            g.setFill(Color.GRAY);
            g.fillRect(50 + scaledOffset, 350, 1000 * (data.TORA / maxLength), 50);
            g.setLineWidth(2);
            g.setStroke(Color.BLACK);
            g.strokeRect(50 + scaledOffset, 350, 1000 * (data.TORA / maxLength), 50);
            g.strokeRect(50 + scaledOffset - 1000 * (data.stopway / maxLength), 350, 1000 * (data.stopway / maxLength), 50);
            g.strokeRect(50 + scaledOffset + 1000 * (data.TORA / maxLength), 350, 1000 * (data.stopway / maxLength), 50);
            g.setLineWidth(1);
            g.strokeRect(50 , 340, scaledOffset, 70);
            g.strokeRect(50 + scaledOffset + 1000 * (data.TORA / maxLength), 340, scaledOffset, 70);
            
            
            for (ObstacleData obstacle : obstacles) {
                g.setFill(Color.RED);
                g.fillRect(1000 * ((obstacle.position + 0.0) / maxLength) + 40 + scaledOffset,
                        355, 
                        20,
                        40);
                g.setFill(Color.BLACK);
                g.strokeRect(1000 * ((obstacle.position + 0.0) / maxLength) + 40 + scaledOffset,
                        355, 
                        20,
                        40);
            }
            
            
            
            g.setLineWidth(2);
            double vScale = 20.0;
            for (ObstacleData obstacle : obstacles) {
                g.setFill(Color.RED);
                double[] xPoints = new double[]{1000 * ((obstacle.position + 0.0) / data.TORA) + 40,
                    1000 * ((obstacle.position + 0.0) / data.TORA) + 50,
                    1000 * ((obstacle.position + 0.0) / data.TORA) + 60};
                double[] yPoints = new double[]{725,
                    725 - 1000 * ((obstacle.maxHeight + 0.0) / data.TORA) * vScale,
                    725};
                g.fillPolygon(xPoints, yPoints, 3);
                g.setFill(Color.BLACK);
                g.strokePolygon(xPoints, yPoints, 3);
                if ((obstacle.position + 0.0) / data.TORA > 0.5) {
                    g.setLineWidth(2);
                    g.setFill(Color.BLACK);
                    g.strokeLine(1000 * ((obstacle.position + 0.0) / data.TORA) + 70, 725,
                            1000 * ((obstacle.position + 0.0) / data.TORA) + 70, 725 - 1000 * ((obstacle.maxHeight + 0.0) / data.TORA) * vScale);
                    g.strokeLine(1000 * ((obstacle.position + 0.0) / data.TORA) + 50, 725 - 1000 * ((obstacle.maxHeight + 0.0) / data.TORA) * vScale,
                            1000 * ((obstacle.position + 0.0) / data.TORA) + 50 - 1000 * ((obstacle.maxHeight + 0.0) / data.TORA) * airport.MinSlope, 725);
                    g.strokeLine(1000 * ((obstacle.position + 0.0) / data.TORA) + 50, 725, 
                            1000 * ((obstacle.position + 0.0) / data.TORA) + 50, 760);
                    g.strokeLine(1000 * ((obstacle.position + 0.0) / data.TORA) + 50 - 1000 * ((obstacle.maxHeight + 0.0) / data.TORA) * airport.MinSlope, 725, 
                            1000 * ((obstacle.position + 0.0) / data.TORA) + 50 - 1000 * ((obstacle.maxHeight + 0.0) / data.TORA) * airport.MinSlope, 760);
                    g.strokeLine(1000 * ((obstacle.position + 0.0) / data.TORA) + 50, 750, 
                            1000 * ((obstacle.position + 0.0) / data.TORA) + 50 - 1000 * ((obstacle.maxHeight + 0.0) / data.TORA) * airport.MinSlope, 750);
                    g.setLineWidth(1);
                    g.strokeText(obstacle.maxHeight + "m", 
                            1000 * ((obstacle.position + 0.0) / data.TORA) + 70, 725 - 1000 * ((obstacle.maxHeight + 0.0) / data.TORA) * vScale - 5);
                    g.strokeText(obstacle.maxHeight + "m x 50 = " + (obstacle.maxHeight * 50) + "m", 
                            1000 * ((obstacle.position + 0.0) / data.TORA) + 50 - 1000 * ((obstacle.maxHeight + 0.0) / data.TORA) * airport.MinSlope + 2, 750 + 15);
                } else {
                    g.setLineWidth(2);
                    g.setFill(Color.BLACK);
                    g.strokeLine(1000 * ((obstacle.position + 0.0) / data.TORA) + 30, 725, //H Label
                            1000 * ((obstacle.position + 0.0) / data.TORA) + 30, 725 - 1000 * ((obstacle.maxHeight + 0.0) / data.TORA) * vScale);
                    g.strokeLine(1000 * ((obstacle.position + 0.0) / data.TORA) + 50, 725 - 1000 * ((obstacle.maxHeight + 0.0) / data.TORA) * vScale, //Slope Label
                            1000 * ((obstacle.position + 0.0) / data.TORA) + 50 + 1000 * ((obstacle.maxHeight + 0.0) / data.TORA) * airport.MinSlope, 725);
                    g.strokeLine(1000 * ((obstacle.position + 0.0) / data.TORA) + 50, 725, //Obstacle Position Line
                            1000 * ((obstacle.position + 0.0) / data.TORA) + 50, 760);
                    g.strokeLine(1000 * ((obstacle.position + 0.0) / data.TORA) + 50 + 1000 * ((obstacle.maxHeight + 0.0) / data.TORA) * airport.MinSlope, 725, //Hx50 Line
                            1000 * ((obstacle.position + 0.0) / data.TORA) + 50 + 1000 * ((obstacle.maxHeight + 0.0) / data.TORA) * airport.MinSlope, 760);
                    g.strokeLine(1000 * ((obstacle.position + 0.0) / data.TORA) + 50, 750, //Hx50 Label
                            1000 * ((obstacle.position + 0.0) / data.TORA) + 50 + 1000 * ((obstacle.maxHeight + 0.0) / data.TORA) * airport.MinSlope, 750);
                    g.setLineWidth(1);
                    g.strokeText(obstacle.maxHeight + "m", 
                            1000 * ((obstacle.position + 0.0) / data.TORA) + 30, 725 - 1000 * ((obstacle.maxHeight + 0.0) / data.TORA) * vScale - 5);
                    g.strokeText(obstacle.maxHeight + "m x 50 = " + (obstacle.maxHeight * 50) + "m", 
                            1000 * ((obstacle.position + 0.0) / data.TORA) + 50 + 2, 750 + 15);
                }
            }
            
            g.setFill(Color.GRAY);
            g.fillRect(50, 725, 1000, 10);
            g.setLineWidth(2);
            g.setStroke(Color.BLACK);
            g.strokeRect(50, 725, 1000, 10);
        }
    }

}
