package com.miniApartment.miniApartment.Controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpHeaders;
import java.util.Map;

public class CassoAPI {
    private static final String API_BASE_URL = "https://oauth.casso.vn/v2";
    private String apiKey;

    public CassoAPI(String apiKey) {
        this.apiKey = apiKey;
    }

    private HttpRequest createRequest(String endpoint, String method, String requestBody) {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + endpoint))
                .header("Authorization", "Apikey " + apiKey)
                .header("Content-Type", "application/json");

        if (method.equals("GET")) {
            requestBuilder.GET();
        } else if (method.equals("POST")) {
            requestBuilder.POST(HttpRequest.BodyPublishers.ofString(requestBody));
        }

        return requestBuilder.build();
    }

    public String sendRequest(String endpoint, String method, String requestBody) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = createRequest(endpoint, method, requestBody);

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            // In response body for more details
            System.out.println("Response Body: " + response.body());
            throw new RuntimeException("Failed: HTTP error code : " + response.statusCode());
        }
    }

    public String getTransactions(String fromDate, String toDate) throws Exception {
        String endpoint = "/transactions?from=" + fromDate + "&to=" + toDate;
        return sendRequest(endpoint, "GET", null);
    }

    public String createWebhook(String url) throws Exception {
        String endpoint = "/webhooks";
        String requestBody = "{\"url\":\"" + url + "\"}";
        return sendRequest(endpoint, "POST", requestBody);
    }
}
