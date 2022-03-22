package task3.xml;


import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;

public class XsiTypeReader extends StreamReaderDelegate {
    private final String namespaceURI;

    public XsiTypeReader(XMLStreamReader reader, String namespaceURI) {
        super(reader);
        this.namespaceURI = namespaceURI;
    }

    @Override
    public String getAttributeNamespace(int paramInt) {
        return super.getAttributeNamespace(paramInt);
    }

    @Override
    public String getNamespaceURI() {
        return namespaceURI;
    }
}
