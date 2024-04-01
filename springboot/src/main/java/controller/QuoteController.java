package controller;

import model.IexQuote;
import model.Quote;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequestMapping("/quote")
public class QuoteController {
    private QuoteService quoteService;

    @Autowired
    public QuoteController (QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping(path = "/iex/ticker/{ticker}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public IexQuote getQuote(@PathVariable String ticker) {
        try {
            IexQuote iexQuoteByTicker = quoteService.findIexQuoteByTicker(ticker);
            return iexQuoteByTicker;
        } catch (Exception e)
        {
            throw ResponseExceptionUtil.getResponseStaticException(e);
        }
    }

    @PutMapping(path = "/iexMarketData")
    @ResponseStatus(HttpStatus.OK)
    public void updateMarketData() {
        try {
            quoteService.updateMarketData();
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStaticException(e);
        }
    }

    @GetMapping(path = "/")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Quote putQuote(@RequestBody Quote quote) {
        try {
            return quoteService.saveQuote(quote);
        } catch (Exception e)
        {
            throw ResponseExceptionUtil.getResponseStaticException(e);
        }
    }

//    @PostMapping(path = "/tickerId/{tickerId}")
//    @ResponseStatus(HttpStatus.CREATED)
//    @ResponseBody
//    public Quote createQuote(@PathVariable String tickerId) {
//        try {
//            return quoteService.saveQuote(tickerId);
//        } catch(Exception e) {
//            throw ResponseExceptionUtil.getResponseStaticException(e);
//        }
//    }

    @GetMapping(path = "/dailyList")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Quote> getDailyList() {
        try {
            return quoteService.findAllQuotes();

        } catch (Exception e) {
            ResponseExceptionUtil.getResponseStaticException(e);
        }

        return quoteService.findAllQuotes();
    }
}
