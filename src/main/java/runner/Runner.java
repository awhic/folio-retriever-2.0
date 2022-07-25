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

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import services.SingleQuoteService;

public class Runner {

    static SingleQuoteService singleQuoteService;
    static {
        try {
            singleQuoteService = new SingleQuoteService();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        // Portfolio
        String[] owned = { "CORE", "NFLX", "MSFT", "F" };
        Double[] quantityOwned = { 5000.0, 1.0, 10.0, 37.0 };

        clearScreen();
        welcome();

        String output;
        Scanner inquiry = new Scanner(System.in);
        String result = inquiry.next().toUpperCase();
        inquiry.close();

        if (result.equals("$")) {
            System.out.println("Loading Your Portfolio, please wait...");
            output = retrieveFolio(owned, quantityOwned);
        } else {
            System.out.println("Loading Ticker, please wait... ");
            output = singleQuoteService.getTickerTitle(result, getApiKey()) +
                    singleQuoteService.getTickerPrice(result, getApiKey());
        }

        clearScreen();
        System.out.println(output);
        System.out.println("");
    }

    private static String getApiKey() throws FileNotFoundException {
        File file = new File("api-key.txt");
        Scanner scanner = new Scanner(file);

        if (scanner.hasNext()) {
            return scanner.next();
        } else {
            return null;
        }
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
            price = price.substring(firstIndex, firstIndex + 45);
            price = price.replace("-", "+");

            int secondIndex = price.indexOf("(+");
            price = price.substring(0, secondIndex);

            int lastIndex = price.indexOf("+");
            price = price.substring(0, lastIndex).trim();
            price = price.replace("9M", "");
            price = price.replaceAll("[^\\d.]", "");

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

    private static String retrieveFolio(String[] owned, Double[] quantityOwned) {
        ArrayList<Double> prices = new ArrayList<>();
        for (String ticker : owned) {
            try {
                prices.add((new BigDecimal(getTickerPrice(ticker))).doubleValue());
            } catch (NumberFormatException e) {
                System.out.println("Error, bad ticker in list.");
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
        } catch (InterruptedException ignored) { }
    }
}
