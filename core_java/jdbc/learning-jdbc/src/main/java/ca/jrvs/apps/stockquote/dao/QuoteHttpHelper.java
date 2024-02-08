package ca.jrvs.apps.stockquote.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QuoteHttpHelper {

    private String apiKey = "6adb62f13cmshe99279fab639d71p129e6bjsn61cf973eb184";
    private OkHttpClient client;

    public QuoteHttpHelper(String apiKey, OkHttpClient client) {
        this.apiKey = apiKey;
        this.client = new OkHttpClient();
    }

    /**
     * Fetch latest quote data from Alpha Vantage endpoint
     *
     * @param symbol Stock symbol
     * @return Quote with latest data
     * @throws IllegalArgumentException if no data was found for the given symbol
     */
    public Quote fetchQuoteInfo(String symbol) throws IllegalArgumentException {
        // Build the Alpha Vantage API URL
        String apiUrl = "https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&datatype=json";


        Request request = new Request.Builder()
                .url(apiUrl)
                .header("X-RapidAPI-Key", apiKey)
                .header("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IllegalArgumentException("Failed to fetch data. HTTP status code: " + response.code());
            }

            // Parse JSON response using Jackson
            String jsonResponse = response.body().string();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);

            // Check if the response contains valid data
            if (jsonNode.has("Global Quote")) {
                return objectMapper.treeToValue(jsonNode.get("Global Quote"), Quote.class);
            } else {
                throw new IllegalArgumentException("No data found for symbol: " + symbol);
            }

        }catch(Exception e) {
        e.printStackTrace();
        throw new IllegalArgumentException("Error fetching data for symbol: " + symbol, e);
    }

}
}


