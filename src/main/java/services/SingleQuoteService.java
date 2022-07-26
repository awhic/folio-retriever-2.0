package services;

import mapper.QuoteMapper;
import model.Data;
import model.Quote;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class SingleQuoteService {
    private final OkHttpClient client = new OkHttpClient().newBuilder()
            .build();

    private final QuoteMapper quoteMapper = new QuoteMapper();

    public SingleQuoteService() throws URISyntaxException { }

    private String getTicker(String ticker, String token) throws IOException {

        HttpUrl.Builder httpBuilder = Objects.requireNonNull(HttpUrl.parse("https://api.stockdata.org/v1/data/quote")).newBuilder();
        httpBuilder.addQueryParameter("api_token", token);
        httpBuilder.addQueryParameter("symbols", ticker);

        Request request = new Request.Builder().url(httpBuilder.build()).build();

        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            return response.body().string();
        }
    }

    public String getTickerTitle(String ticker, String token) throws IOException {
        Quote quote = quoteMapper.mapQuote(getTicker(ticker, token));
        if (quote.getMeta().getReturned() == 1) {
            Data[] data = quote.getData();
            return data[0].getName();
        } else {
            throw new IOException("Error: Ticker invalid.");
        }
    }

    public String getTickerPrice(String ticker, String token) throws URISyntaxException, IOException, InterruptedException {

        Locale usa = new Locale("en", "US");
        NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);

        if (Objects.equals(ticker, "CORE")) {
            return dollarFormat.format(1.00);
        }
        Quote quote = quoteMapper.mapQuote(getTicker(ticker, token));
        if (quote.getMeta().getReturned() == 1) {
            Data[] data = quote.getData();

            return dollarFormat.format(data[0].getPrice());
        } else {
            throw new IOException("Error: Ticker invalid.");
        }
    }


}
