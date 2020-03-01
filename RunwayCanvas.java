import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RunwayCanvas extends Canvas {

    RunwayData data;

    RunwayCanvas(int w, int h, RunwayData data) {
        super(w, h);
        this.data = data;
    }

    public void render() {
        final GraphicsContext g = this.getGraphicsContext2D();
        g.setFill(Color.BLACK);
        g.setLineWidth(5);
        g.strokeRect(0, 0, g.getCanvas().getWidth(), g.getCanvas().getHeight());
        g.strokeLine(0, g.getCanvas().getHeight() / 2, g.getCanvas().getWidth(), g.getCanvas().getHeight() / 2);
    }

}