package ca.jrvs.apps.stockquote.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PositionDao implements CrudDao<Position, String> {
    private Connection c;

    public PositionDao(Connection connection) {
        super();
    }

    @Override
    public Position save(Position entity) throws IllegalArgumentException {
        try (PreparedStatement statement = c.prepareStatement(
                "INSERT INTO position VALUES (?, ?, ?) " +
                        "ON CONFLICT (symbol) DO UPDATE SET " +
                        "number_of_shares = EXCLUDED.number_of_shares, " +
                        "value_paid = EXCLUDED.value_paid"
        )) {
            setPositionParameters(statement, entity);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error saving Position entity", e);
        }
        return entity;
    }

    @Override
    public Optional<Position> findById(String symbol) throws IllegalArgumentException {
        try (PreparedStatement statement = c.prepareStatement("SELECT * FROM position WHERE symbol = ?")) {
            statement.setString(1, symbol);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapToPosition(resultSet));
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error finding Position entity by ID", e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Position> findAll() {
        List<Position> positions = new ArrayList<>();
        try (Statement statement = c.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM position");
            while (resultSet.next()) {
                positions.add(mapToPosition(resultSet));
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error finding all Position entities", e);
        }
        return positions;
    }

    @Override
    public void deleteById(String symbol) throws IllegalArgumentException {
        try (PreparedStatement statement = c.prepareStatement("DELETE FROM position WHERE symbol = ?")) {
            statement.setString(1, symbol);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error deleting Position entity by ID", e);
        }
    }

    @Override
    public void deleteAll() {
        try (Statement statement = c.createStatement()) {
            statement.executeUpdate("DELETE FROM position");
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error deleting all Position entities", e);
        }
    }

    // Helper method to map ResultSet to Position entity
    private Position mapToPosition(ResultSet resultSet) throws SQLException {
        Position position = new Position();
        position.setTicker(resultSet.getString("symbol"));
        position.setNumOfShares(resultSet.getInt("number_of_shares"));
        position.setValuePaid(resultSet.getDouble("value_paid"));
        return position;
    }

    // Helper method to set parameters for the PreparedStatement
    private void setPositionParameters(PreparedStatement statement, Position position) throws SQLException {
        statement.setString(1, position.getTicker());
        statement.setInt(2, position.getNumOfShares());
        statement.setDouble(3, position.getValuePaid());
    }
}