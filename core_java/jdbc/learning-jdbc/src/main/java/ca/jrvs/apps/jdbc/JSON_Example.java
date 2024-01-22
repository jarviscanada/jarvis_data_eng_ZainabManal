package ca.jrvs.apps.jdbc;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class JSON_Example {
     /* String symbol = "AAPL";
      String apiKey = "6adb62f13cmshe99279fab639d71p129e6bjsn61cf973eb184";
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create("https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol="+symbol+"&datatype=json"))
          .header("X-RapidAPI-Key", apiKey)
          .header("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
          .method("GET", HttpRequest.BodyPublishers.noBody())
          .build();

      try {
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (JsonMappingException e) {
        e.printStackTrace();
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } */

      public static String toJson(Object object, boolean prettyJson, boolean includeNullValues)
        throws JsonProcessingException {
        ObjectMapper m = new ObjectMapper();

        if(!includeNullValues) {
          m.setSerializationInclusion(Include.NON_NULL);
        }

        if(prettyJson){
          m.enable(SerializationFeature.INDENT_OUTPUT);
        }

        return m.writeValueAsString(object);
      }

      public static <T> T toObjectFromJson(String json, Class<T> clazz) throws IOException {
        ObjectMapper m = new ObjectMapper();

        return (T) m.readValue(json, clazz);
      }

      public static void main(String[] args) throws IOException {
        Company company = toObjectFromJson(companyStr, Company.class);
        System.out.println(toJson(company, true, true));
      }

      //JSON string is provided(you can copy and paste)
      public static final String companyStr = "{\n"
          + "   \"symbol\":\"AAPL\",\n"
          + "   \"companyName\":\"Apple Inc.\",\n"
          + "   \"exchange\":\"Nasdaq Global Select\",\n"
          + "   \"description\":\"Apple Inc is designs, manufactures and markets mobile communication and media devices and personal computers, and sells a variety of related software, services, accessories, networking solutions and third-party digital content and applications.\",\n"
          + "   \"CEO\":\"Timothy D. Cook\",\n"
          + "   \"sector\":\"Technology\",\n"
          + "   \"financials\":[\n"
          + "      {\n"
          + "         \"reportDate\":\"2018-12-31\",\n"
          + "         \"grossProfit\":32031000000,\n"
          + "         \"costOfRevenue\":52279000000,\n"
          + "         \"operatingRevenue\":84310000000,\n"
          + "         \"totalRevenue\":84310000000,\n"
          + "         \"operatingIncome\":23346000000,\n"
          + "         \"netIncome\":19965000000\n"
          + "      },\n"
          + "      {\n"
          + "         \"reportDate\":\"2018-09-30\",\n"
          + "         \"grossProfit\":24084000000,\n"
          + "         \"costOfRevenue\":38816000000,\n"
          + "         \"operatingRevenue\":62900000000,\n"
          + "         \"totalRevenue\":62900000000,\n"
          + "         \"operatingIncome\":16118000000,\n"
          + "         \"netIncome\":14125000000\n"
          + "      }\n"
          + "   ],\n"
          + "   \"dividends\":[\n"
          + "      {\n"
          + "         \"exDate\":\"2018-02-09\",\n"
          + "         \"paymentDate\":\"2018-02-15\",\n"
          + "         \"recordDate\":\"2018-02-12\",\n"
          + "         \"declaredDate\":\"2018-02-01\",\n"
          + "         \"amount\":0.63\n"
          + "      },\n"
          + "      {\n"
          + "         \"exDate\":\"2017-11-10\",\n"
          + "         \"paymentDate\":\"2017-11-16\",\n"
          + "         \"recordDate\":\"2017-11-13\",\n"
          + "         \"declaredDate\":\"2017-11-02\",\n"
          + "         \"amount\":0.63\n"
          + "      }\n"
          + "   ]\n"
          + "}";
    }
