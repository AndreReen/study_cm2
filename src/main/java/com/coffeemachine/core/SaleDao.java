package com.coffeemachine.core;

import com.coffeemachine.dao.Dao;
import com.coffeemachine.model.Beverage;
import com.coffeemachine.model.Sale;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaleDao implements Dao<Sale, Integer> {



    private static final Logger LOGGER = Logger.getLogger(SaleDao.class.getName());
    private final Optional<Connection> connection;

    public SaleDao() {
        this.connection = JdbcConnection.getConnection();
    }

    @Override
    public Optional<Sale> get(int id) { return Optional.empty();}

    @Override
    public Collection<Sale> getAll() {
        Collection<Sale> sales = new ArrayList<>();
        return sales;}

    @Override
    public Optional<Integer> save(Sale sale) {
        String message = "The Sales to be added should not be null";
        Sale nonNullCustomer = Objects.requireNonNull(sale, message);
        String sql = "INSERT INTO "
                + "sale(customer, beverage) "
                + "VALUES(?, ?)";

        return connection.flatMap(conn -> {
            Optional<Integer> generatedId = Optional.empty();

            try (PreparedStatement statement = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, nonNullCustomer.getCustomer());
                statement.setString(2, nonNullCustomer.getBeverage());



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
                        new Object[]{nonNullCustomer, numberOfInsertedRows > 0});
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }

            return generatedId;
        });
    }

    @Override
    public void update(Sale sale) {}

    @Override
    public void delete(Sale sale){};

}
