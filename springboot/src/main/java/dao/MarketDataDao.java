package dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.IexQuote;

import java.net.http.HttpClient;
import java.util.List;
import java.util.Optional;

@Repository
public interface MarketDataDao extends JpaRepository<IexQuote, String> {
    Optional<IexQuote> findById(String ticker);
    List<IexQuote> findAllById(Iterable<String> tickers);
    Optional<String> executeHttpGet(String url);

    HttpClient getHttpClient();
    boolean existsById(String s);
    List<IexQuote> findAll();

    long count();
    void deleteById(String s);
    void delete(IexQuote entity);
    void deleteAll(Iterable<? extends IexQuote> entities);
    void deleteAll();

    IexQuote save(IexQuote entity);
    List<IexQuote> saveAll(List<IexQuote> entities);

}