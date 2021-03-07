package com.coffeemachine.core;

import com.coffeemachine.dao.Dao;
import com.coffeemachine.model.Beverage;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BeverageDao implements Dao<Beverage, Integer> {

    private static final Logger LOGGER = Logger.getLogger(BeverageDao.class.getName());
    private final Optional<Connection> connection;

    public BeverageDao() {
        this.connection = JdbcConnection.getConnection();
    }

    @Override
    public Optional<Beverage> get(int id) {
        return connection.flatMap(conn -> {
            Optional<Beverage> beverage = Optional.empty();
            String sql = "SELECT * FROM cmdata WHERE cm_id = " + id;

            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    String name = resultSet.getString("cm_type");
                    Integer quantity = resultSet.getInt("cm_qnty");


                    beverage = Optional.of(new Beverage(id, name, quantity));

                    LOGGER.log(Level.INFO, "Found {0} in database", beverage.get());
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }

            return beverage;
        });
    }

    @Override
    public Collection<Beverage> getAll() {
        Collection<Beverage> Beverages = new ArrayList<>();
        String sql = "SELECT * FROM cmdata";

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("cm_id");
                    String name = resultSet.getString("cm_type");
                    Integer quantity = resultSet.getInt("cm_qnty");


                    Beverage Beverage = new Beverage(id, name, quantity);

                    Beverages.add(Beverage);

                    LOGGER.log(Level.INFO, "Found {0} in database", Beverage);
                }

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });

        return Beverages;
    }

    @Override
    public Optional<Integer> save(Beverage beverage) {
        String message = "The Beverage to be added should not be null";
        Beverage nonNullBeverage = Objects.requireNonNull(beverage, message);
        String sql = "INSERT INTO "
                + "cmdata(cm_type, cm_qnty) "
                + "VALUES(?, ?)";

        return connection.flatMap(conn -> {
            Optional<Integer> generatedId = Optional.empty();

            try (PreparedStatement statement = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, nonNullBeverage.getName());
                statement.setInt(2, nonNullBeverage.getQuantity());


                int numberOfInsertedRows = statement.executeUpdate();

                //Retrieve the auto-generated id
                if (numberOfInsertedRows > 0) {
                    try (ResultSet resultSet = statement.getGeneratedKeys()) {
                        if (resultSet.next()) {
                            generatedId = Optional.of(resultSet.getInt(1));
                        }
                    }
                }

                LOGGER.log(Level.INFO, "{0} created successfully? {1}",
                        new Object[]{nonNullBeverage, numberOfInsertedRows > 0});
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }

            return generatedId;
        });
    }

    @Override
    public void update(Beverage beverage) {
        String message = "The Beverage to be updated should not be null";
        Beverage nonNullBeverage = Objects.requireNonNull(beverage, message);
        String sql = "UPDATE cmdata "
                + "SET "
                + "cm_type = ?, "
                + "cm_qnty = ? "
                + "WHERE "
                + "cm_id = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setString(1, nonNullBeverage.getName());
                statement.setInt(2, nonNullBeverage.getQuantity());
                statement.setInt(3, nonNullBeverage.getId());

                int numberOfUpdatedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the Beverage updated successfully? {0}",
                        numberOfUpdatedRows > 0);


            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void delete(Beverage beverage) {
        String message = "The Beverage to be deleted should not be null";
        Beverage nonNullBeverage = Objects.requireNonNull(beverage, message);
        String sql = "DELETE FROM cmdata WHERE cm_id = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setInt(1, nonNullBeverage.getId());

                int numberOfDeletedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the Beverage deleted successfully? {0}",
                        numberOfDeletedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

}