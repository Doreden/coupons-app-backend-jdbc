package com.dor.couponssystem.db.dao;

import com.dor.couponssystem.db.connection.ConnectionPool;
import com.dor.couponssystem.enums.CrudOperation;
import com.dor.couponssystem.enums.EntityType;
import com.dor.couponssystem.exceptions.EntityCrudException;
import com.dor.couponssystem.model.Customer;
import com.dor.couponssystem.utills.ObjectExtractionUtil;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAO extends UserDAO<Long, Customer> {

    // make CompanyDAO singleton - Eager//
    public static final CustomerDAO instance = new CustomerDAO();
    private final ConnectionPool connectionPool;

    public CustomerDAO() {
        try {
            connectionPool = ConnectionPool.getInstance();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to establish connection to customer db");
        }
    }


    @Override
    public Long create(final Customer customer) throws EntityCrudException {
        Connection connection = null;
        final String sqlStatement = "INSERT INTO customers(first_name,last_name,email,password) VALUES(?,?,?,?)";
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, String.valueOf(customer.getPassword().hashCode()));
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
            return -1L;
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.CREATE, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Customer read(Long ID) throws EntityCrudException {
        Connection connection = null;
        Customer customer = null;
        final String sqlStatement = "select * from customers where id = ?";
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setLong(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return ObjectExtractionUtil.generateCustomerFromResultSet(resultSet);
            }
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.READ, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return customer;
    }

    @Override
    public void update(Customer customer) throws EntityCrudException {
        Connection connection = null;
        final String sqlStatement = "UPDATE customers set first_name = ?, last_name = ?, email = ?, password = ? where id = ?";
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, String.valueOf(customer.getPassword().hashCode()));
            preparedStatement.setLong(5, customer.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.UPDATE, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void delete(Long id) throws EntityCrudException {
        Customer customer = null;
        Connection connection = null;
        final String sqlStatement = "delete from customers where id = ?";
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.READ, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public ArrayList<Customer> readAll() throws EntityCrudException {
        Connection connection = null;
        final String sqlStatement = "select * from customers";
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            final ArrayList<Customer> customerList = new ArrayList<>();
            while (resultSet.next()) {
                customerList.add(ObjectExtractionUtil.generateCustomerFromResultSet(resultSet));
            }
            return customerList;
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.READ, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Customer readByEmail(String email) throws EntityCrudException {
        Connection connection = null;
        final String sqlStatement = "select * from customers where email = ?";
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            return ObjectExtractionUtil.generateCustomerFromResultSet(resultSet);

        } catch (Exception e) {
            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.READ, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }


    public boolean isExists(String email, String password) throws EntityCrudException {
        Connection connection = null;
        final String sqlStatement = "select * from customers where email = ? AND password = ?";
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, String.valueOf(password.hashCode()));
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.LOGIN, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public Long getCustomerId(String email) throws EntityCrudException {
        Connection connection = null;
        final String sqlStatement = "select * from customers where email = ?";
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("id");
            }
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.LOGIN, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return -1L;
    }

    public boolean isCustomerExistsByEmail(String customerEmail) throws EntityCrudException {
        Connection connection = null;
        final String sqlStatement = "select * from customers where email = ?";
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, customerEmail);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.IS_EXISTS, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public boolean isCouponPurchasedByCustomer(final long couponID, final long customerID) throws EntityCrudException {
        Connection connection = null;
        final String sqlStatement = "select * from customers_vs_coupons where coupon_id = ? AND customer_id = ?";
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setLong(1, couponID);
            preparedStatement.setLong(2, customerID);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.CUSTOMER_VS_COUPON, CrudOperation.LOGIN, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

}
