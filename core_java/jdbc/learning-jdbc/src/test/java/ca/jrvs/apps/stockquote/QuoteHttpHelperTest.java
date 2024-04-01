//package ca.jrvs.apps.stockquote;
//
//import ca.jrvs.apps.stockquote.dao.Quote;
//import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
//public class QuoteHttpHelperTest {
//    private QuoteHttpHelper quoteHttpHelper = new QuoteHttpHelper("6adb62f13cmshe99279fab639d71p129e6bjsn61cf973eb184", client);
//
//    @Test
//    public void testAPICall_Success() {
//        try {
//            String symbol = "MSFT";
//
//            Quote quote = quoteHttpHelper.fetchQuoteInfo(symbol);
//
//            // Assertions to check if the API call was successful
//            assertNotNull(quote);
//            assertEquals(symbol, quote.getTicker());
//
//            assertNotNull(quote.getOpen());
//            assertNotNull(quote.getHigh());
//            assertNotNull(quote.getLow());
//            assertNotNull(quote.getPrice());
//            assertNotNull(quote.getVolume());
//            assertNotNull(quote.getLatestTradingDay());
//            assertNotNull(quote.getPreviousClose());
//            assertNotNull(quote.getChange());
//            assertNotNull(quote.getChangePercent());
//            //assertNotNull(quote.getTimestamp());
//        } catch (Exception e) {
//            fail("Exception not expected for a successful API call: " + e.getMessage());
//        }
//    }
//
//    //FIX THESE TESTS
//    @Test(expected = IllegalArgumentException.class)
//    public void testFetchQuoteInfo_InvalidSymbol() {
//        quoteHttpHelper = new QuoteHttpHelper("6adb62f13cmshe99279fab639d71p129e6bjsn61cf973eb", client);
//        quoteHttpHelper.fetchQuoteInfo("MSFF");
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void testFetchQuoteInfo_HttpFailure() {
//        quoteHttpHelper = new QuoteHttpHelper("6adb62f13cmshe99279fab639d71p129e6bjsn61cf973eb", client);
//        quoteHttpHelper.fetchQuoteInfo("AAPL");
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void testFetchQuoteInfo_InvalidApiKey() {
//        quoteHttpHelper = new QuoteHttpHelper("i6adb62f13cmshe99279fab639d71p129e6bjsn61cf973eb", client);
//        quoteHttpHelper.fetchQuoteInfo("AAPL");
//    }
//}
