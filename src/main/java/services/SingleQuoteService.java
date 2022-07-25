package services;

import java.net.http.HttpRequest;

public class SingleQuoteService {
    HttpRequest request = HttpRequest.newBuilder().build();

    public HttpRequest getRequest() {
        return request;
    }
}
