import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;


import org.w3c.dom.*;
import org.w3c.dom.Element;

import javax.xml.parsers.*;
import java.io.*;
import java.util.Map;

public class XMLLoader {

    public static Airport importAirport(String filePath) throws JAXBException, FileNotFoundException {
        JAXBContext ja = JAXBContext.newInstance(Airport.class);
        Unmarshaller unmarshaller = ja.createUnmarshaller();
        return (Airport) unmarshaller.unmarshal(new FileReader(filePath));
    }

    public static void exportAirport(Airport ap, String filePath) throws JAXBException {
        JAXBContext ja = JAXBContext.newInstance(Airport.class);
        Marshaller marshaller = ja.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(ap, new File(filePath));

    }

    public static Runway importRunway(String filePath) throws JAXBException, FileNotFoundException {
        JAXBContext ja = JAXBContext.newInstance(Runway.class);
        Unmarshaller unmarshaller = ja.createUnmarshaller();
        return (Runway) unmarshaller.unmarshal(new FileReader(filePath));
    }

    public static void exportRunway(Runway runway, String filePath) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Runway.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(runway, new File(filePath));
    }
/*
    public static void main(String[] args) throws Exception {
        String fileName = "";

        List<Runway> quickRunways = new ArrayList<>();
        Runway r1 = new Runway(9, new RunwayData(306, 3902, 3902, 3902, 3595), new RunwayData(0, 3884, 3962, 3884, 3884));
        Runway r2 = new Runway(27, new RunwayData(0, 3660, 3660, 3660, 3660), new RunwayData(307, 3660, 3660, 3660, 3353));
        Runway r3 = new Runway(16, new RunwayData(0, 3660, 4060, 3860, 3660), new RunwayData(307, 3660, 3660, 3660, 3660));
        quickRunways.add(r1);
        quickRunways.add(r2);quickRunways.add(r3);
        Airport airport = new Airport(250,70,350,60);
        for (Runway quickRunway : quickRunways) {
            airport.addRunway(quickRunway);
        }
        airport.addObstacle(new ObstacleData(new Point(1,1),25));
        airport.addObstacle(new ObstacleData(new Point(10001, 100001),10));


        JAXBContext ja = JAXBContext.newInstance(Airport.class);
        Marshaller marshaller = ja.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(airport, new File("TestFile.xml"));

        System.out.println("Exported Successfully");

        Unmarshaller unmarshaller = ja.createUnmarshaller();
        Airport ap = (Airport) unmarshaller.unmarshal(new FileReader("TestFile.xml"));

        System.out.println("Imported airport successfully");
        System.out.println(ap.getRunwayPositions().get("27L/09R").getX());
        System.out.println(ap.getRunways().get(2).getRunL().getName());

        JAXBContext jr = JAXBContext.newInstance(Runway.class);
        Marshaller m = jr.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(r3,new File("RunwayTestFile.xml"));

        Unmarshaller u = jr.createUnmarshaller();
        Runway r33 = (Runway) u.unmarshal(new FileReader("RunwayTestFile.xml"));

    }
*/
}
