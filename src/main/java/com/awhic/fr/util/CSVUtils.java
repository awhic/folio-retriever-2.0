package com.awhic.fr.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

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
}
