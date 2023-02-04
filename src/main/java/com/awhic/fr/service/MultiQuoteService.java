package com.awhic.fr.service;

import com.awhic.fr.exception.ApiLimitException;
import com.awhic.fr.exception.InvalidTickerException;
import com.awhic.fr.mapper.QuoteMapper;
import com.awhic.fr.model.Data;
import com.awhic.fr.model.Quote;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class MultiQuoteService {
    private final OkHttpClient client = new OkHttpClient().newBuilder()
            .build();

    private final QuoteMapper quoteMapper = new QuoteMapper();

    public MultiQuoteService() { }

    private String getTickers(String[] tickers, String token) throws IOException {

        StringBuilder url = new StringBuilder();
        int iterator = 0;
        for (String ticker : tickers) {
            url.append(ticker);
            if (iterator < (Arrays.stream(tickers).count() - 1)) {
                url.append(",");
                iterator++;
            }
        }

        HttpUrl.Builder httpBuilder = Objects.requireNonNull(HttpUrl.parse("https://api.stockdata.org/v1/data/quote")).newBuilder();
        httpBuilder.addQueryParameter("symbols", url.toString());
        httpBuilder.addQueryParameter("api_token", token);

        Request request = new Request.Builder().url(httpBuilder.build()).build();

        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            return response.body().string();
        }
    }

    public ArrayList<Double> getPrices(String[] owned, Double[] quantityOwned, String token) throws URISyntaxException, IOException, InterruptedException {

        if (Arrays.stream(owned).count() > 3) {
            throw new ApiLimitException();
        }

        ArrayList<Double> prices = new ArrayList<>();

        Quote quotes = quoteMapper.mapQuote(getTickers(owned, token));
        if (quotes.getMeta() == null) {
            throw new ApiLimitException();
        }

        Data[] datum = quotes.getData();
        for (Data data : datum) {
            System.out.println(data.getTicker());
        }

        if (quotes.getMeta().getReturned() > 0) {
            for (Data data : datum) {
                prices.add(data.getPrice());
            }
        } else {
            throw new InvalidTickerException();
        }
        return prices;
    }
}
