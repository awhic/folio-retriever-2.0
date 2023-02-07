package com.awhic.fr.code;

import com.awhic.fr.util.CSVUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class PortfolioGenerator {

    public HashMap<Double, String> getPortfolio() throws IOException {
        CSVUtils CSVUtils = new CSVUtils();
        ArrayList<String> owned =  CSVUtils.getOwned();
        ArrayList<Double> quantityOwned = CSVUtils.getQuantityOwned();

        HashMap<Double, String> ownedMap = new HashMap<>();
        int iterate = 0;
        for (Double d : quantityOwned) {
            ownedMap.put(d, owned.get(iterate));
            iterate++;
        }
        return ownedMap;
    }
}
