package com.awhic.fr;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Locale;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Scanner;

import com.awhic.fr.code.PortfolioRetriever;
import com.awhic.fr.exception.ApiLimitException;
import com.awhic.fr.exception.InvalidApiTokenException;
import com.awhic.fr.exception.InvalidTickerException;
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

    public static void main(String[] args) throws URISyntaxException, InterruptedException, IOException {

        // Portfolio - temporary
        String[] owned = { "NFLX", "MSFT", "F" };
        Double[] quantityOwned = { 1.0, 10.0, 37.0 };
        double coreValue = 0.00;

        consoleUtils.welcome();

        String output = "";
        Scanner inquiry = new Scanner(System.in);
        String result;
        boolean helpFlag = false;
        do {
            result = inquiry.nextLine().toUpperCase();

            if (result.equals("p".toUpperCase())) {
                try {
                    output = PortfolioRetriever.retrieveFolio(owned, quantityOwned, coreValue, singleQuoteService, apiUtils.getApiKey(), dollarFormat);
                } catch (ApiLimitException e) {
                    System.out.println("You have reached your API limit of 100 calls per 24 hours, or 3 symbols per request.");
                    result = "999";
                } catch (InvalidApiTokenException e) {
                    if (apiUtils.isEmpty()) {
                        System.out.println("API Token is missing, please enter a valid API token:");
                        apiUtils.writeApiKey();
                    } else {
                        System.out.println("API Token is invalid.");
                        result = "999";
                    }
                }
            } else if (result.equals("key-edit".toUpperCase())) {
                output = "";
                apiUtils.writeApiKey();
            } else if (result.equals("key".toUpperCase())) {
                output = "";
                try {
                    System.out.println("Your API key: " + apiUtils.getApiKey());
                } catch (InvalidApiTokenException e) {
                    output = "";
                    System.out.println("No stored API key. Enter your API key here: ");
                    apiUtils.writeApiKey();
                }
            } else if (result.equals("edit".toUpperCase())) {
                //DEVELOPMENT
                consoleUtils.help();
                output = "";
                helpFlag = true;
            } else if (result.equals("help".toUpperCase())) {
                consoleUtils.help();
                output = "";
                helpFlag = true;
            } else if (result.equals("x".toUpperCase()) || result.equals("exit".toUpperCase())) {
                result = "999";
                inquiry.close();
            } else if (result.toUpperCase().startsWith("T")) {
                helpFlag = false;
                try {
                    String ticker = result.substring(1).trim();
                    output = singleQuoteService.getTickerComplete(ticker, apiUtils.getApiKey(), dollarFormat);
                } catch (InvalidTickerException e) {
                    output = "";
                    System.out.println("Error: Invalid ticker.");
                } catch (ApiLimitException e) {
                    output = "";
                    System.out.println("You have reached your API limit of 100 calls per 24 hours.");
                    result = "999";
                } catch (InvalidApiTokenException | FileNotFoundException e) {
                    if (apiUtils.isEmpty()) {
                        System.out.println("API Token is missing, please enter a valid API token:");
                        apiUtils.writeApiKey();
                    } else {
                        System.out.println("API Token is invalid.");
                        result = "999";
                    }
                } catch (IOException e) {
                    throw new RuntimeException("An unexpected error occurred. Exiting...");
                }
            } else {
                System.out.println("Command not recognized. Type \"help\" for list of commands");
            }
            if (!result.equals("999") && !helpFlag) {
                if (!output.equals("")) {
                    System.out.println(output);
                }
            }
        } while (!result.equals("999"));
    }
}
