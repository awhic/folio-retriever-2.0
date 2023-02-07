package com.awhic.fr.code;

import com.awhic.fr.util.CSVUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

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

    public HashMap<Double, String> addStockToPortfolio() {
        Scanner sc = new Scanner(System.in);
        HashMap<Double, String> map = new HashMap<>();
        boolean isFinished = false;
        while (!isFinished) {
            System.out.print("Enter the stock ticker: ");
            String ticker = sc.nextLine();
            System.out.print("Enter the quantity owned: ");
            double quantity = sc.nextDouble();
            sc.nextLine();
            map.put(quantity, ticker);
            System.out.print("Would you like to enter another stock? (y/n): ");
            String response = sc.nextLine().toUpperCase();
            if (!response.equals("y".toUpperCase())) {
                isFinished = true;
            }
        }
        return map;
    }
}
