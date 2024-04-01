package ca.jrvs.apps.stockquote;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        BasicConfigurator.configure();
        StockQuoteController.startClient(args);
    }

}