package task3.xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import task3.entity.NodeEntity;
import task3.entity.RelationEntity;
import task3.entity.WayEntity;
import task3.repository.NodeRepository;
import task3.repository.RelationRepository;
import task3.repository.WayRepository;

@Component
@RequiredArgsConstructor
public class ParsingStarter {

    private final NodeRepository nodeRepository;
    private final WayRepository wayRepository;
    private final RelationRepository relationRepository;

//    @EventListener(ApplicationReadyEvent.class)
    public void run() throws FileNotFoundException, IOException, XMLStreamException, JAXBException {
        try ( InputStream in = new FileInputStream("src/main/resources/RU-NVS.osm.bz2")) {
            InputStream inputStream = new BZip2CompressorInputStream(in, true);
            StAXProcessor processor = new StAXProcessor(inputStream);

            long cur_event = 0;
            long start = System.currentTimeMillis();
            while (processor.hasNext()) {
                int event = processor.getNextEvent();
                if (XMLStreamConstants.START_ELEMENT == event) {
                    if (++cur_event % 1000 == 0)
                        System.out.println(cur_event);
                    switch (processor.getLocalName()) {
                        case "node":
                            NodeEntity nodeEntity = NodeEntity.convertFromXMLNode(processor.unmarshalNode());
                            nodeRepository.save(nodeEntity);
                            break;
                        case "way":
                            WayEntity wayEntity = WayEntity.convertFromXMLWay(processor.unmarshalWay());
                            wayRepository.save(wayEntity);
                            break;
                        case "relation":
                            RelationEntity relationEntity = RelationEntity.convertFromXMRelation(processor.unmarshalRelation());
                            relationRepository.save(relationEntity);
                            break;
                    }
                }
            }
            long end = System.currentTimeMillis();
            System.out.println(String.format("End of insert. %s seconds.",
                    (double) (end - start) / 1000.0));
        }
    }
}
