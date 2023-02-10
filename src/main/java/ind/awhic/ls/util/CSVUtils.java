package ind.awhic.ls.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.*;

public class CSVUtils {

    public ArrayList<String> getOwned() throws IOException {
        ArrayList<String> owned = new ArrayList<>();
        Reader in = new FileReader("src\\main\\resources\\owned.csv");
        Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in);
        for (CSVRecord record : records) {
            Collections.addAll(owned, record.values());
        }
        return owned;
    }

    public ArrayList<Double> getQuantityOwned() throws IOException {
        ArrayList<Double> quantityOwned = new ArrayList<>();
        Reader in = new FileReader("src\\main\\resources\\quantityOwned.csv");
        Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in);
        for (CSVRecord record : records) {
            for (String value : record.values()) {
                quantityOwned.add(Double.parseDouble(value));
            }
        }
        return quantityOwned;
    }

    public void setOwned(HashMap<Double, String> proposedStocks) {
        try {
            FileWriter keysWriter = new FileWriter("src/main/resources/quantityOwned.csv", true);
            CSVPrinter keysPrinter = new CSVPrinter(keysWriter, CSVFormat.RFC4180);
            FileWriter valuesWriter = new FileWriter("src/main/resources/owned.csv", true);
            CSVPrinter valuesPrinter = new CSVPrinter(valuesWriter, CSVFormat.RFC4180);

            ArrayList<Double> keyState = getQuantityOwned();
            ArrayList<String> valueState = getOwned();

            flushCSV("src/main/resources/quantityOwned.csv");
            flushCSV("src/main/resources/owned.csv");

            for (double key : proposedStocks.keySet()) {
                String proposedValue = proposedStocks.get(key).toUpperCase();

                if (!valueState.contains(proposedValue)) {
                    keyState.add(key);
                    valueState.add(proposedValue);
                } else if (valueState.contains(proposedValue) &&
                        key != keyState.get(valueState.indexOf(proposedValue))) {

                    keyState.remove(valueState.indexOf(proposedValue));
                    valueState.remove(proposedValue);

                    keyState.add(key);
                    valueState.add(proposedValue);
                }
            }

            keysPrinter.printRecords(keyState);
            valuesPrinter.printRecords(valueState);

            keysPrinter.flush();
            keysPrinter.close();
            valuesPrinter.flush();
            valuesPrinter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void flushCSV(String path) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
