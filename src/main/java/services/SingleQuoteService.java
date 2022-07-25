package services;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SingleQuoteService {

    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public SingleQuoteService() throws URISyntaxException { }

    private String getTicker(String ticker, String token) throws URISyntaxException, IOException, InterruptedException {

        HttpRequest request;
        request = HttpRequest.newBuilder()
                .uri(new URI("https://api.stockdata.org/v1/data/quote?symbols=" + ticker + "&api_token=" + token))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public String getTickerTitle(String ticker, String token) throws URISyntaxException, IOException, InterruptedException {
        return getTicker(ticker, token);
    }

    public String getTickerPrice(String ticker, String token) throws URISyntaxException, IOException, InterruptedException {
        return getTicker(ticker, token);
    }


}
