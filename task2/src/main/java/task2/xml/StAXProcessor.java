package task2.xml;

import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import task2.models.generated.Node;

public class StAXProcessor implements AutoCloseable {

    private final XsiTypeReader reader;
    private final JAXBContext context;

    public StAXProcessor(InputStream inputStream) throws XMLStreamException, JAXBException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLStreamReader xMLStreamReader = xmlInputFactory.createXMLStreamReader(inputStream);
        reader = new XsiTypeReader(xMLStreamReader, "http://openstreetmap.org/osm/0.6");
        context = JAXBContext.newInstance(Node.class);
    }

    public int nextEvent() throws XMLStreamException {
        return reader.next();
    }

    public Node getNextNode() throws JAXBException, XMLStreamException {
        while (reader.hasNext()) {
            int event = reader.next();
            if (XMLStreamConstants.START_ELEMENT == event && reader.getLocalName().equals("node")) {
                return (Node) context.createUnmarshaller().unmarshal(reader);
            }
        }
        return null;
    }

    @Override
    public void close() throws XMLStreamException {
        if (reader != null) {
            reader.close();
        }
    }

}
