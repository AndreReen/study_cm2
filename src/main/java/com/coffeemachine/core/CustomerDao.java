package com.coffeemachine.core;

import com.coffeemachine.dao.Dao;
import com.coffeemachine.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public  class CustomerDao implements Dao<Customer, Integer> {


    private static final Logger LOGGER = Logger.getLogger(PostgreSqlDao.class.getName());
    private final Optional<Connection> connection;

    public CustomerDao() {
        this.connection = JdbcConnection.getConnection();
    }

    @Override
    public Optional<Customer> get(int id) {
        return connection.flatMap(conn -> {
            Optional<Customer> customer = Optional.empty();
            String sql = "SELECT * FROM cmcustomer WHERE id = " + id;

            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    String name = resultSet.getString("name");



                     customer = Optional.of(new Customer(id, name));

                    LOGGER.log(Level.INFO, "Found {0} in database", customer.get());
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }

            return customer;
        });
    }

    @Override
    public Collection<Customer> getAll() {
        Collection<Customer> Customers = new ArrayList<>();
        String sql = "SELECT * FROM cmdata";

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("cm_id");
                    String name = resultSet.getString("cm_type");
                    Integer quantity = resultSet.getInt("cm_qnty");


                    Customer Customer = new Customer(id, name);

                    Customers.add(Customer);

                    //LOGGER.log(Level.INFO, "Found {0} in database", Customer);
                }

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });

        return Customers;
    }

    @Override
    public Optional<Integer> save(Customer customer) {
        String message = "The Customer to be added should not be null";
        Customer nonNullCustomer = Objects.requireNonNull(customer, message);
        String sql = "INSERT INTO "
                + "cmcustomer(name) "
                + "VALUES(?)";

        return connection.flatMap(conn -> {
            Optional<Integer> generatedId = Optional.empty();

            try (PreparedStatement statement = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, nonNullCustomer.getName());



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
    public void update(Customer customer) {
        String message = "The Customer to be updated should not be null";
        Customer nonNullCustomer = Objects.requireNonNull(customer, message);
        String sql = "UPDATE cmcustomer "
                + "SET "
                + "name = ?, "
                + "WHERE "
                + "id = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setString(1, nonNullCustomer.getName());
                statement.setInt(2, nonNullCustomer.getId());

                int numberOfUpdatedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the Customer updated successfully? {0}",
                        numberOfUpdatedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void delete(Customer customer) {
        String message = "The Customer to be deleted should not be null";
        Customer nonNullCustomer = Objects.requireNonNull(customer, message);
        String sql = "DELETE FROM cmdata WHERE cm_id = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setInt(1, nonNullCustomer.getId());

                int numberOfDeletedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the Customer deleted successfully? {0}",
                        numberOfDeletedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }



}
