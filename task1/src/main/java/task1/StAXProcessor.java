package task1;

import java.io.InputStream;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class StAXProcessor implements AutoCloseable {

    private final XMLEventReader reader;

    public StAXProcessor(InputStream inputStream) throws XMLStreamException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        reader = xmlInputFactory.createXMLEventReader(inputStream);
    }
    
    public boolean hasNext() {
        return reader.hasNext();
    }
    
    public XMLEvent nextEvent() throws XMLStreamException {
        return reader.nextEvent();
    }

    public void countTagAttribute(Map<String, Integer> map, XMLEvent event, String tag, QName attribute) {
        if (event.isStartElement()) {
            StartElement startEvent = event.asStartElement();
            String eventName = startEvent.getName().getLocalPart();
            if (eventName.equals(tag)) {
                String attrValue = startEvent.getAttributeByName(attribute).getValue();
                map.compute(attrValue, (key, val) -> val == null ? 1 : val + 1);
            }
        }
    }

    @Override
    public void close() throws XMLStreamException {
        if (reader != null) {
            reader.close();
        }
    }

}
