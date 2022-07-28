package runner;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Locale;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Scanner;

import exception.ApiLimitException;
import exception.InvalidApiTokenException;
import exception.InvalidTickerException;
import service.SingleQuoteService;
import util.ConsoleUtils;

public class Runner {

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

    public static void main(String[] args) throws URISyntaxException, InterruptedException {
        // Portfolio
        String[] owned = { "CORE", "NFLX", "MSFT", "F" };
        Double[] quantityOwned = { 5000.0, 1.0, 10.0, 37.0 };

        consoleUtils.clearScreen();
        consoleUtils.welcome();

        String output = "";
        Scanner inquiry = new Scanner(System.in);
        String result;
        Boolean tokenWorked = true;
        do {
            result = inquiry.next().toUpperCase();

            if (result.equals("$")) {
                System.out.println("Loading Your Portfolio, please wait...");
                System.out.println("");
                try {
                    output = retrieveFolio(owned, quantityOwned);
                } catch (ApiLimitException e) {
                    consoleUtils.clearScreen();
                    System.out.println("You have reached your API limit of 100 calls per 24 hours.");
                    result = "999";
                } catch (InvalidApiTokenException e) {
                    System.out.println("API Token is missing or invalid.");
                    result = "999";
                    tokenWorked = false;
                }
            } else if (result.equals("999")) {
                consoleUtils.clearScreen();
                inquiry.close();
                output = "Exiting...";
            } else {
                consoleUtils.clearScreen();
                System.out.println("");
                try {
                    System.out.println("Loading Ticker, please wait... ");
                    System.out.println("");
                    output = singleQuoteService.getTickerTitle(result, getApiKey()) + ": " +
                           dollarFormat.format(singleQuoteService.getTickerPrice(result, getApiKey()));
                } catch (IOException e) {
                    consoleUtils.clearScreen();
                    System.out.println("Error: Invalid Ticker.");
                } catch (ApiLimitException e) {
                    consoleUtils.clearScreen();
                    System.out.println("You have reached your API limit of 100 calls per 24 hours.");
                    result = "999";
                } catch (InvalidApiTokenException e) {
                    System.out.println("API Token is missing or invalid.");
                    result = "999";
                    tokenWorked = false;
                }
            }

            consoleUtils.clearScreen();
            if (!result.equals("999") && tokenWorked) {
                System.out.println(output);
                System.out.println("");
                System.out.println("What else can I do for you? " +
                        "(Options: Quote (ex., MSFT), Portfolio ('$'), Exit ('999'): ");
            }
        } while (!result.equals("999"));
    }

    private static String getApiKey() throws FileNotFoundException {
        File file = new File("api-key.txt");
        Scanner scanner = new Scanner(file);

        if (scanner.hasNext()) {
            return scanner.next();
        } else {
            throw new InvalidApiTokenException();
        }
    }

    private static String retrieveFolio(String[] owned, Double[] quantityOwned) {
        ArrayList<Double> prices = new ArrayList<>();
        for (String ticker : owned) {
            try {
                prices.add((BigDecimal.valueOf(singleQuoteService.getTickerPrice(ticker, getApiKey()))).doubleValue());
            } catch (InvalidTickerException e) {
                System.out.println("Error: One or more ticker symbols provided are invalid.");
            } catch (URISyntaxException | InterruptedException | IOException | IndexOutOfBoundsException e) {
                throw new RuntimeException("Unexpected Error Occurred. Exiting...");
            } catch (ApiLimitException e) {
                throw new ApiLimitException();
            }
        }

        int iterate = 0;
        ArrayList<Double> totals = new ArrayList<>();
        for (Double q : quantityOwned) {
            totals.add(q * prices.get(iterate));
            iterate++;
        }

        Double output = totals.stream().mapToDouble(i -> i).sum();

        Locale usa = new Locale("en", "US");
        NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);

        return "Your Current Portfolio Value: " + dollarFormat.format(output);
    }
}
