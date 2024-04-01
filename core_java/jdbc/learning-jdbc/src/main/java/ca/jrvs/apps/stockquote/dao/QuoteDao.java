package ca.jrvs.apps.stockquote.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuoteDao implements CrudDao<Quote, String> {

    final Logger logger = LoggerFactory.getLogger(CrudDao.class);
    private Connection c;

    public QuoteDao(Connection c) {
        this.c = c;
    }

    @Override
    public Quote save(Quote entity) throws IllegalArgumentException {
        if (entity == null) {
            logger.error("Quote does not exist");
        }

        else if (findById(entity.getSymbol()).isEmpty()) {
            String sqlQuery = "INSERT INTO quote (symbol, open, high, low, price, volume, latest_trading_day, previous_close, change, change_percent, timestamp) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = c.prepareStatement(sqlQuery)) {
                preparedStatement.setString(1, entity.getSymbol());
                preparedStatement.setDouble(2, entity.getOpen());
                preparedStatement.setDouble(3, entity.getHigh());
                preparedStatement.setDouble(4, entity.getLow());
                preparedStatement.setDouble(5, entity.getPrice());
                preparedStatement.setInt(6, entity.getVolume());
                preparedStatement.setDate(7, entity.getLatestTradingDay());
                preparedStatement.setDouble(8, entity.getPreviousClose());
                preparedStatement.setDouble(9, entity.getChange());
                preparedStatement.setString(10, entity.getChangePercent());
                preparedStatement.setTimestamp(11, entity.getTimestamp());

                preparedStatement.executeUpdate();

                return entity;

            } catch (SQLException e) {
                logger.error("SQLException Occurred. Could not save quote. Reason:" + e.getMessage());
            }
        } else {
            String sqlQuery = "UPDATE quote SET symbol = ?, open = ?, high = ?, low = ?, price = ?, volume = ?, " +
                    "latest_trading_day = ?, previous_close = ?, change = ?, change_percent = ?, timestamp = ? WHERE symbol = ?";
            try (PreparedStatement preparedStatement = c.prepareStatement(sqlQuery)) {
                preparedStatement.setString(1, entity.getSymbol());
                preparedStatement.setDouble(2, entity.getOpen());
                preparedStatement.setDouble(3, entity.getHigh());
                preparedStatement.setDouble(4, entity.getLow());
                preparedStatement.setDouble(5, entity.getPrice());
                preparedStatement.setInt(6, entity.getVolume());
                preparedStatement.setDate(7, entity.getLatestTradingDay());
                preparedStatement.setDouble(8, entity.getPreviousClose());
                preparedStatement.setDouble(9, entity.getChange());
                preparedStatement.setString(10, entity.getChangePercent());
                preparedStatement.setTimestamp(11, entity.getTimestamp());
                preparedStatement.setString(12, entity.getSymbol());

                preparedStatement.executeUpdate();

                return entity;
            } catch (SQLException e) {
                logger.error("SQLException Occurred. Could not save quote:" + e.getMessage());
            }
        }
        return entity;
    }

    @Override
    public Optional<Quote> findById(String symbol) throws IllegalArgumentException {
        if (symbol == null) {
            logger.error("Ticker is null or missing");
        }

        try (PreparedStatement preparedStatement = c.prepareStatement("SELECT * FROM quote WHERE symbol = ?")) {
            preparedStatement.setString(1, symbol);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                logger.info("Result set " + resultSet.getString("ticker"));
                return Optional.of(mapToQuote(resultSet));
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            logger.error("SQLException Occurred. Could not find quote. Reason:" + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Iterable<Quote> findAll() {
        List<Quote> quotes = new ArrayList<>();
        try (Statement statement = c.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM quote");

            while (resultSet.next()) {
                quotes.add(mapToQuote(resultSet));
            }

        } catch (SQLException e) {
            logger.error("SQLException - Error finding all entities. " + e.getMessage());
        }
        return quotes;
    }


    @Override
    public void deleteById(String symbol) throws IllegalArgumentException {
        if (symbol == null) {
            logger.error("ID is null or missing");
        }

        try (PreparedStatement preparedStatement = c.prepareStatement("DELETE FROM quote WHERE symbol = ?")) {
            preparedStatement.setString(1, symbol);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting Quote entity by ID:" + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        try (Statement statement = c.createStatement()) {
            statement.executeUpdate("DELETE FROM quote");
        } catch (SQLException e) {
            logger.error("Error deleting all Quote entities: " + e.getMessage());
        }
    }

    // Helper method to map ResultSet to Quote entity
    private Quote mapToQuote(ResultSet resultSet) throws SQLException {
        Quote quote = new Quote();
        quote.setSymbol(resultSet.getString("symbol"));
        quote.setOpen(resultSet.getDouble("open"));
        quote.setHigh(resultSet.getDouble("high"));
        quote.setLow(resultSet.getDouble("low"));
        quote.setPrice(resultSet.getDouble("price"));
        quote.setVolume(resultSet.getInt("volume"));
        quote.setLatestTradingDay(resultSet.getDate("latest_trading_day"));
        quote.setPreviousClose(resultSet.getDouble("previous_close"));
        quote.setChange(resultSet.getDouble("change"));
        quote.setChangePercent(resultSet.getString("change_percent"));
        quote.setTimestamp(resultSet.getTimestamp("timestamp"));
        return quote;
    }

}
