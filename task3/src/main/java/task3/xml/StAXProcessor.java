package task3.xml;

import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import task3.models.generated.Node;
import task3.models.generated.Way;
import task3.models.generated.Relation;

public class StAXProcessor implements AutoCloseable {

    private final XsiTypeReader reader;

    private final JAXBContext nodeContext;
    private final JAXBContext wayContext;
    private final JAXBContext relationContext;

    public StAXProcessor(InputStream inputStream) throws XMLStreamException, JAXBException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLStreamReader xMLStreamReader = xmlInputFactory.createXMLStreamReader(inputStream);
        reader = new XsiTypeReader(xMLStreamReader, "http://openstreetmap.org/osm/0.6");

        nodeContext = JAXBContext.newInstance(Node.class);
        wayContext = JAXBContext.newInstance(Way.class);
        relationContext = JAXBContext.newInstance(Relation.class);
    }

    public boolean hasNext() throws XMLStreamException {
        return reader.hasNext();
    }

    public int getNextEvent() throws JAXBException, XMLStreamException {
        return reader.next();
    }

    public String getLocalName() {
        return reader.getLocalName();
    }

    public Node unmarshalNode() throws JAXBException {
        return (Node) nodeContext.createUnmarshaller().unmarshal(reader);
    }

    public Way unmarshalWay() throws JAXBException {
        return (Way) wayContext.createUnmarshaller().unmarshal(reader);
    }

    public Relation unmarshalRelation() throws JAXBException {
        return (Relation) relationContext.createUnmarshaller().unmarshal(reader);
    }

    @Override
    public void close() throws XMLStreamException {
        if (reader != null) {
            reader.close();
        }
    }

}
