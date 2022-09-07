package com.awhic.fr;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Locale;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Scanner;

import com.awhic.fr.code.PortfolioRetriever;
import com.awhic.fr.code.exception.ApiLimitException;
import com.awhic.fr.code.exception.InvalidApiTokenException;
import com.awhic.fr.code.exception.InvalidTickerException;
import com.awhic.fr.service.SingleQuoteService;
import com.awhic.fr.util.ApiUtils;
import com.awhic.fr.util.ConsoleUtils;

public class FolioRetrieverApplication {
    static SingleQuoteService singleQuoteService;
    static {
        try {
            singleQuoteService = new SingleQuoteService();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    static ConsoleUtils consoleUtils = new ConsoleUtils();
    static NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
    static ApiUtils apiUtils = new ApiUtils();

    public static void main(String[] args) throws URISyntaxException, InterruptedException {

        // Portfolio - temporary
        String[] owned = { "NFLX", "MSFT", "F" };
        Double[] quantityOwned = { 1.0, 10.0, 37.0 };
        double coreValue = 5000.00;

        consoleUtils.welcome();

        String output = "";
        Scanner inquiry = new Scanner(System.in);
        String result;
        boolean tokenWorked = true;
        boolean helpFlag = false;
        do {
            result = inquiry.next().toUpperCase();

            if (result.equals("-p".toUpperCase())) {
                System.out.println("Loading Your Portfolio, please wait...");
                System.out.println("");
                try {
                    output = PortfolioRetriever.retrieveFolio(owned, quantityOwned, coreValue, singleQuoteService, apiUtils.getApiKey(), dollarFormat);
                } catch (ApiLimitException e) {
                    System.out.println("You have reached your API limit of 100 calls per 24 hours, or 3 symbols per request.");
                    result = "999";
                } catch (InvalidApiTokenException e) {
                    System.out.println("API Token is missing or invalid.");
                    result = "999";
                    tokenWorked = false;
                }
            } else if (result.equals("-a".toUpperCase())) {
                //DEVELOPMENT
                consoleUtils.help();
                output = "";
                helpFlag = true;
            } else if (result.equals("-e".toUpperCase())) {
                //DEVELOPMENT
                consoleUtils.help();
                output = "";
                helpFlag = true;
            } else if (result.equals("-help".toUpperCase())) {
                consoleUtils.help();
                output = "";
                helpFlag = true;
            } else if (result.equals("-x".toUpperCase())) {
                result = "999";
                inquiry.close();
            } else {
                helpFlag = false;
                System.out.println("");
                try {
                    System.out.println("Loading Ticker, please wait... ");
                    System.out.println("");
                    output = singleQuoteService.getTickerTitle(result, apiUtils.getApiKey()) + ": " +
                           dollarFormat.format(singleQuoteService.getTickerPrice(result, apiUtils.getApiKey()));
                } catch (InvalidTickerException e) {
                    output = "";
                    System.out.println("Error: Invalid Ticker.");
                    System.out.println();
                } catch (ApiLimitException e) {
                    output = "";
                    System.out.println("You have reached your API limit of 100 calls per 24 hours.");
                    result = "999";
                } catch (InvalidApiTokenException | FileNotFoundException e) {
                    System.out.println("API Token is missing or invalid.");
                    result = "999";
                    tokenWorked = false;
                    output = "";
                } catch (IOException e) {
                    throw new RuntimeException("An Unexpected Error Occurred. Exiting...");
                }
            }

            if (!result.equals("999") && tokenWorked && !helpFlag) {
                if (!output.equals("")) {
                    System.out.println(output);
                    System.out.println("");
                }
                consoleUtils.sleeper();
                System.out.println("What else can I do for you?: ");
            }
        } while (!result.equals("999"));
    }
}
