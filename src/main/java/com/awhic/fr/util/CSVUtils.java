package com.awhic.fr.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

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

    public void setOwned(HashMap<Double, String> ownedStocks) {
        try {
            FileWriter keysWriter = new FileWriter("src/main/resources/quantityOwned.csv", true);
            CSVPrinter keysPrinter = new CSVPrinter(keysWriter, CSVFormat.RFC4180);
            FileWriter valuesWriter = new FileWriter("src/main/resources/owned.csv", true);
            CSVPrinter valuesPrinter = new CSVPrinter(valuesWriter, CSVFormat.RFC4180);

            for (double key : ownedStocks.keySet()) {
                String value = ownedStocks.get(key);

                //TODO: Meant to check if ticker is present BUT q is different, not working, so close to. Need to flush
//                ArrayList<Double> keyState = getQuantityOwned();
//                ArrayList<String> valueState = getOwned();
//                if (valueState.contains(value.toUpperCase())) {
//                    if (key != keyState.get(getOwned().indexOf(value.toUpperCase()))) {
//
//                        //TODO: remove everything first HERE!!!
//
//                        keyState.remove(getOwned().indexOf(value.toUpperCase()));
//                        valueState.remove(getOwned().indexOf(value.toUpperCase()));
//                        for (double alreadyOwned : keyState) {
//                            keysPrinter.printRecord(alreadyOwned);
//                        }
//                        for (String alreadyOwned : valueState) {
//                            valuesPrinter.printRecord(alreadyOwned);
//                        }
//                        keysPrinter.printRecord(key);
//                        valuesPrinter.printRecord(value.toUpperCase());
//                    }
//                } else {
//                    keysPrinter.printRecord(key);
//                    valuesPrinter.printRecord(value.toUpperCase());
//                }
                if (!getOwned().contains(value.toUpperCase())) {
                    keysPrinter.printRecord(key);
                    valuesPrinter.printRecord(value.toUpperCase());
                }
            }

            keysPrinter.flush();
            keysPrinter.close();
            valuesPrinter.flush();
            valuesPrinter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
