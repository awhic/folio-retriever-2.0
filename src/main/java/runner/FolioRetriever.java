package runner;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

public class FolioRetriever {

    public static void main(String[] args) {
        // Portfolio
        String[] owned = { "CORE", "NFLX", "MSFT", "F" };
        Double[] quantityOwned = { 5000.0, 1.0, 10.0, 37.0 };

        clearScreen();
        welcome();

        String output = "";
        Scanner inquiry = new Scanner(System.in);
        String result = inquiry.next().toUpperCase().toString();
        inquiry.close();

        if (result.equals("$")) {
            System.out.println("Loading Your Portfolio, please wait...");
            output = retrieveFolio(owned, quantityOwned);
        } else {
            System.out.println("Loading Ticker, please wait... ");
            output = getTickerTitle(result) + getTickerPrice(result);
        }

        clearScreen();
        System.out.println(output);
        System.out.println("");
    }

    private static String getTickerPrice(String passedInTicker) {
        if (passedInTicker.equals("CORE")) {
            return "1.00";
        }

        String url_link = "https://finance.yahoo.com/quote/" + passedInTicker + "/history";
        String price = "";

        WebClient webClient = new WebClient(BrowserVersion.FIREFOX);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setJavaScriptEnabled(false);

        try {
            HtmlPage page = webClient.getPage(url_link);
            price = page.asNormalizedText();

            int firstIndex = price.indexOf("9M");
            price = price.substring(firstIndex, firstIndex + 45).toString();
            price = price.replace("-", "+");

            int secondIndex = price.indexOf("(+");
            price = price.substring(0, secondIndex);

            int lastIndex = price.indexOf("+");
            price = price.substring(0, lastIndex).trim();
            price = price.replace("9M", "");
            price = price.replaceAll("[^0-9.]", "");

            webClient.getCurrentWindow().getJobManager().removeAllJobs();
            webClient.close();

        } catch (IOException e) {
            clearScreen();
            System.out.println("An unexpected error occurred: " + e);
        } catch (StringIndexOutOfBoundsException e) {
            clearScreen();
            System.out.println("Error: Data could not be returned. Input may be invalid.");
        }

        return price;
    }

    private static String getTickerTitle(String passedInTicker) {
        if (passedInTicker.equals("CORE")) {
            return "'CORE' represents dollar amount available to trade and is equal to one (1) US Dollar: ";
        }

        String url_link = "https://finance.yahoo.com/quote/" + passedInTicker + "/history";
        String title = "";

        WebClient webClient = new WebClient(BrowserVersion.FIREFOX);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setJavaScriptEnabled(false);

        try {
            HtmlPage page = webClient.getPage(url_link);
            title = page.getTitleText().toString().replace(" Stock Historical Prices & Data - Yahoo Finance", "")
                    + ":".trim() + " ";

            webClient.getCurrentWindow().getJobManager().removeAllJobs();
            webClient.close();

        } catch (IOException e) {
            clearScreen();
            System.out.println("An unexpected error occurred: " + e);
        } catch (StringIndexOutOfBoundsException e) {
            clearScreen();
            System.out.println("Error: Data could not be returned. One or more tickers invalid.");
        }

        return title;
    }

    private static String retrieveFolio(String[] owned, Double[] quantityOwned) {
        ArrayList<Double> prices = new ArrayList<Double>();
        for (String ticker : owned) {
            try {
                prices.add((new BigDecimal(getTickerPrice(ticker))).doubleValue());
            } catch (NumberFormatException e) {
                System.out.println("Error, bad ticker in list.");
            }
        }

        int iterate = 0;
        ArrayList<Double> totals = new ArrayList<Double>();
        for (Double q : quantityOwned) {
            totals.add(q * prices.get(iterate));
            iterate++;
        }

        Double output = totals.stream().mapToDouble(i -> i).sum();

        Locale usa = new Locale("en", "US");
        NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);

        return "Your Current Portfolio Value: " + dollarFormat.format(output);
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void welcome() {
        System.out.println("Welcome to Folio Retriever.");
        sleeper();
        System.out.println("");
        System.out.println("Enter a Stock Ticker *OR* enter '$' to see your portfolio value: ");
    }

    private static void sleeper() {
        try {
            Thread.sleep(1100L);
        } catch (InterruptedException e) { }
    }
}
