package task1;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.namespace.QName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

public class Main {

    public static void main(String[] args) throws IOException {
        Logger logger = LogManager.getLogger();
        logger.info("Start program");
        Map<String, Integer> userCorr = new HashMap<>();
        Map<String, Integer> tagAmount = new HashMap<>();

        try ( FileInputStream in = new FileInputStream("src/main/resources/RU-NVS.osm.bz2")) {
            InputStream inputStream = new BZip2CompressorInputStream(in, true);
            StAXProcessor processor = new StAXProcessor(inputStream);
            while (processor.hasNext()) {
                XMLEvent event = processor.nextEvent();
                processor.countTagAttribute(userCorr, event, "node", new QName("user"));
                processor.countTagAttribute(tagAmount, event, "tag", new QName("k"));
            }
        } catch (XMLStreamException ex) {
            logger.error(ex);
        }
        userCorr.entrySet()
                .stream()
                .sorted(Entry.comparingByValue())
                .forEach((Entry<String, Integer> i) -> {
                    System.out.println(i.getKey() + ": " + i.getValue());
                });
        System.out.println("\nTotal unique key names: " + tagAmount.size());
        tagAmount.entrySet()
                .stream()
                .sorted(Entry.comparingByValue())
                .forEach((Entry<String, Integer> i) -> {
                    System.out.println(i.getKey() + ": " + i.getValue());
                });
        logger.info("Program end.");
    }

}
