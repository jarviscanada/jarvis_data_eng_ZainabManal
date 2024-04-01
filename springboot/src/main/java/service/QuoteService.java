package service;

import dao.QuoteDao;
import model.IexQuote;
import model.Quote;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuoteService {

    private QuoteDao quoteDao;

    /**
     * Update quote table against IEX source
     *
     * - get all quotes from the db
     * - for each ticker get IexQuote
     * - convert IexQuote to Quote entity
     * - persist quote to db
     *
     * @throwsResourceNotFoundException if ticker is not found from IEX
     * @throwsDataAccessException if unable to retrieve data
     * @throwsIllegalArgumentException for invalid input
     */
    public void updateMarketData() {
        // List ticker, updated quotes, allDBquotes
        // Extract all the ticker symbols extracted from the dbQuotes list and add to tickerList

        // create a temporary quote
    }

    /**
     * Validate (against IEX) and save given tickers to quote table
     *
     * - get IexQuote(s)
     * - convert each IexQuote to Quote entity
     * - persist the quote to db
     *
     * @param tickers
     * @return list of converted quote entities
     * @throwsIllegalArgumentException if ticker is not found from IEX
     */
    public List<Quote> saveQuotes(List<String> tickers) {
        //TODO
        return null;
    }

    /**
     * Find an IexQuote from the given ticker
     *
     * @param ticker
     * @return corresponding IexQuote object
     * @throwsIllegalArgumentExcpetion if ticker is invalid
     */
    public IexQuote findIexQuoteByTicker(String ticker) {
        //TODO
        return null;
    }

    /**
     * Update a given quote to the quote table without validation
     *
     * @param quote entity to save
     * @return the saved quote entity
     */
    @Transactional
    public Quote saveQuote(Quote quote)
    {
        return quoteDao.save(quote);
    }


    /**
     * Find all quotes from the quote table
     *
     * @return a list of quotes
     */
    public List<Quote> findAllQuotes() {
        //TODO
        return null;
    }

}