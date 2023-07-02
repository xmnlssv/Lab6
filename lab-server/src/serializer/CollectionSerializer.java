package serializer;

import ch.qos.logback.classic.Logger;
import model.LabWork;
import model.LabWorksArrayList;
import utils.LogUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class CollectionSerializer {
    private static Logger logger = LogUtil.getLogger("server");
    public static ArrayList<LabWork> unmarshal(String filePath) {
        ArrayList<LabWork> collection = new ArrayList<>();
        try {
            QName qName = new QName("labWork");
            if (!Files.exists(Path.of(filePath))) {
                Files.createFile(Path.of(filePath));
            }
            InputStream inputStream = new FileInputStream(filePath);
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);
            JAXBContext context = JAXBContext.newInstance(LabWork.class);
            XMLEvent e;
            Unmarshaller unmarshaller = context.createUnmarshaller();
            while ((e = xmlEventReader.peek()) != null) {
                if (e.isStartElement() && ((StartElement) e).getName().equals(qName)) {
                    LabWork unmarshalledPerson = unmarshaller.unmarshal(xmlEventReader, LabWork.class).getValue();
                    collection.add(unmarshalledPerson);
                } else {
                    xmlEventReader.next();
                }
            }
            return collection;
        } catch (Exception exception) {
            logger.error("Exception while parsing XML from file: " + exception);
        }
        return collection;
    }

    public static String marshal(ArrayList<LabWork> collection) {
        try {
            LabWorksArrayList labWorkSetWrapper = new LabWorksArrayList(collection);
            JAXBContext jaxbContext = JAXBContext.newInstance(LabWorksArrayList.class, LabWork.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(labWorkSetWrapper, stringWriter);
            return stringWriter.toString();
        } catch (Exception exception) {
            logger.error("Exception while saving XML to file: " + exception);
            return "";
        }
    }

}
