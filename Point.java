import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlAdapter;

@XmlRootElement
public class Point {
    @XmlTransient
    public int x, y;

    public Point() {
        this(0, 0);
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
   }

    public void setX(int x) {
        this.x = x;
   }

    public void setY(int y) {
        this.y = y;
   }
}

// class MyPointAdapter extends XmlAdapter<Point, java.awt.Point> {
//
//    /*
//     * Java => XML
//     */
//    public Point marshal(java.awt.Point val) throws Exception {
//        return new Point((int) val.getX(), (int) val.getY());
//    }
//
//    /*
//     * XML => Java
//     */
//    public java.awt.Point unmarshal(Point val) throws Exception {
//        return new java.awt.Point(val.getX(), val.getY());
//    }
//
//}