package task2;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

public class Main {

    static int totalNodes = 5000;
    
    public static void main(String[] args) {
        
        TimeMeasurer timeMeasurer = new TimeMeasurer(totalNodes);
        try {
            double executeTime = timeMeasurer.executeTime();
            System.out.println(String.format("Execute query speed: %s rows / second", totalNodes/executeTime));
            
            double prepTime = timeMeasurer.prepTime();
            System.out.println(String.format("Prepared query time: %s rows / second", totalNodes/prepTime));
  
            double batchTime = timeMeasurer.batchTime();
            System.out.println(String.format("batch query time: %s rows / second", totalNodes/batchTime));
            
        } catch (IOException | SQLException | XMLStreamException | JAXBException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
