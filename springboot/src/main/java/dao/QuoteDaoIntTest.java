package dao;

import model.Quote;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.NestedTestConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {NestedTestConfiguration.class})
@Sql({"classpath:schema.sql"})

public class QuoteDaoIntTest {
    @Autowired
    private QuoteDao quoteDao;
    private Quote savedQuote;

    @Before
    public void insertOne(){
        savedQuote.setAskPrice(10d);

    }

    @After
    public void deleteOne() {
        quoteDao.deleteById(savedQuote.getTicker());
    }
}
