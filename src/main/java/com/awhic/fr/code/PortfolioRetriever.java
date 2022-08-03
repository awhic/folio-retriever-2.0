package com.awhic.fr.code;

import com.awhic.fr.code.exception.ApiLimitException;
import com.awhic.fr.code.exception.InvalidTickerException;
import com.awhic.fr.service.SingleQuoteService;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.util.ArrayList;

public class PortfolioRetriever {
    public static String retrieveFolio(String[] owned, Double[] quantityOwned, double coreValue, SingleQuoteService staticService, String token, NumberFormat format) {
        ArrayList<Double> prices = new ArrayList<>();
        for (String ticker : owned) {
            try {
                prices.add((BigDecimal.valueOf(staticService.getTickerPrice(ticker, token))).doubleValue());
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
        totals.add(coreValue);

        Double output = totals.stream().mapToDouble(i -> i).sum();

        return "Your Current Portfolio Value: " + format.format(output);
    }

    // portfolio method using MultiQuoteService. Does not work because data is unsorted and can only request 3 at once. Need workarounds for both issues.

//    private static String retrieveFolio(String[] owned, Double[] quantityOwned, Double coreValue, String token) throws URISyntaxException, IOException, InterruptedException {
//
//        ArrayList<Double> prices = multiQuoteService.getPrices(owned, quantityOwned, token);
//
//        int iterate = 0;
//        ArrayList<Double> totals = new ArrayList<>();
//        for (Double quantity : quantityOwned) {
//            totals.add(quantity * prices.get(iterate));
//            iterate++;
//        }
//        totals.add(coreValue);
//
//        Double output = totals.stream().mapToDouble(i -> i).sum();
//
//        Locale usa = new Locale("en", "US");
//        NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);
//
//        return "Your Current Portfolio Value: " + dollarFormat.format(output);
//    }
}
