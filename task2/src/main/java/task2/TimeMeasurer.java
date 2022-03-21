package task2;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import task2.dao.NodeDao;
import task2.dbManager.DbManager;
import task2.xml.StAXProcessor;
import task2.models.generated.Node;

public class TimeMeasurer {

    public final int totalNodes;

    public TimeMeasurer(int totalNodes) {
        this.totalNodes = totalNodes;
    }

    public double executeTime() throws IOException, SQLException, XMLStreamException, JAXBException {

        long totalTime = 0l;

        try ( InputStream inputStream = new BZip2CompressorInputStream(new FileInputStream("src/main/resources/RU-NVS.osm.bz2"), true)) {
            try ( StAXProcessor processor = new StAXProcessor(inputStream)) {
                DbManager.initDB();
                NodeDao nodeDao = new NodeDao();
                for (int i = 0; i < 5000; i++) {
                    Node node = processor.getNextNode();

                    long startTime = System.currentTimeMillis();
                    nodeDao.saveExecute(node);
                    long endTime = System.currentTimeMillis();
                    totalTime += (endTime - startTime);
                }
            }
        }
        return (double) totalTime / 1000;
    }

    public double prepTime() throws IOException, SQLException, XMLStreamException, JAXBException {

        long totalTime = 0l;

        try ( InputStream inputStream = new BZip2CompressorInputStream(new FileInputStream("src/main/resources/RU-NVS.osm.bz2"), true)) {
            try ( StAXProcessor processor = new StAXProcessor(inputStream)) {
                DbManager.initDB();
                NodeDao nodeDao = new NodeDao();
                for (int i = 0; i < 5000; i++) {
                    Node node = processor.getNextNode();

                    long startTime = System.currentTimeMillis();
                    nodeDao.savePrep(node);
                    long endTime = System.currentTimeMillis();
                    totalTime += (endTime - startTime);
                }
            }
        }
        return (double) totalTime / 1000;
    }

    public double batchTime() throws IOException, SQLException, XMLStreamException, JAXBException {

        long totalTime = 0l;

        try ( InputStream inputStream = new BZip2CompressorInputStream(new FileInputStream("src/main/resources/RU-NVS.osm.bz2"), true)) {
            try ( StAXProcessor processor = new StAXProcessor(inputStream)) {
                DbManager.initDB();
                NodeDao nodeDao = new NodeDao();
                for (int i = 0; i < 5000; i++) {
                    Node node = processor.getNextNode();

                    long startTime = System.currentTimeMillis();
                    nodeDao.saveBatch(node);
                    long endTime = System.currentTimeMillis();
                    totalTime += (endTime - startTime);
                }
                long startTime = System.currentTimeMillis();
                nodeDao.flushBatch();
                long endTime = System.currentTimeMillis();
                totalTime += (endTime - startTime);
            }
        }
        return (double) totalTime / 1000;
    }

}
