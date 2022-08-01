package com.dor.couponssystem.db.dao;

import com.dor.couponssystem.db.connection.ConnectionPool;
import com.dor.couponssystem.enums.CrudOperation;
import com.dor.couponssystem.enums.EntityType;
import com.dor.couponssystem.exceptions.EntityCrudException;
import com.dor.couponssystem.model.Company;
import com.dor.couponssystem.utills.ObjectExtractionUtil;

import java.sql.*;
import java.util.ArrayList;

public class CompanyDAO extends UserDAO<Long, Company> {

    // make CompanyDAO singleton - Eager//
    public static final CompanyDAO instance = new CompanyDAO();
    private final ConnectionPool connectionPool;


    public CompanyDAO() {
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException("error occurred while trying to get an instance of connectionPool from CompanyDAO", e);
        }
    }

    @Override
    public Long create(final Company company) throws EntityCrudException {
        Connection connection = null;
        final String sqlStatement = "INSERT INTO companies(name, email, password) VALUES(?,?,?) ";
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, company.getName().toString());
            preparedStatement.setString(2, company.getEmail());
            preparedStatement.setString(3, String.valueOf(company.getPassword().hashCode()));
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
            return -1L;
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.CREATE, e);
        } finally {
            connectionPool.returnConnection(connection);

        }
    }

    @Override
    public Company read(Long id) throws EntityCrudException {
        Connection connection = null;
        final String sqlStatement = "select * from companies where id = ?";
        Company company = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) { // if statement block check if the cursor moved to a new line //
                return ObjectExtractionUtil.generateCompanyFromResultSet(resultSet);
            }
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.READ, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return company;
    }

    @Override
    public void update(Company company) throws EntityCrudException {
        Connection connection = null;
        final String sqlStatement = "update companies set name = ? ,email = ?, password = ? where id = ?";
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, company.getName());
            preparedStatement.setString(2, company.getEmail());
            preparedStatement.setString(3, String.valueOf(company.getPassword().hashCode()));
            preparedStatement.setLong(4, company.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.UPDATE, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void delete(Long id) throws EntityCrudException {
        Connection connection = null;
        final String sqlStatement = "delete from companies where id = ?";
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.DELETE, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public ArrayList<Company> readAll() throws EntityCrudException {
        Connection connection = null;
        final String sqlStatement = "select * from companies";
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            final ArrayList<Company> companiesList = new ArrayList<>();
            while (resultSet.next()) {
                companiesList.add(ObjectExtractionUtil.generateCompanyFromResultSet(resultSet));
            }
            return companiesList;

        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.READ, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Company readByEmail(String email) throws EntityCrudException {
        Connection connection = null;
        final String sqlStatement = "select * from companies where email = ?";
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            return ObjectExtractionUtil.generateCompanyFromResultSet(resultSet);
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.READ, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    //@Override
    public boolean isCompanyExists(final String companyEmail, final String companyPassword) throws EntityCrudException {
        Connection connection = null;
        final String sqlStatement = "select * from companies where email = ? AND password = ?";
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, companyEmail);
            preparedStatement.setString(2, String.valueOf(companyPassword.hashCode()));
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.LOGIN, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public final Long getCompanyId(final String companyEmail) throws EntityCrudException {
        Connection connection = null;
        final String sqlStatement = "select * from companies where email = ?";
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, companyEmail);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("id");
            }
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.LOGIN, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return -1L;
    }

    public boolean isCompanyExistsByName(final String companyName) throws EntityCrudException {
        Connection connection = null;
        final String sqlStatement = "select * from companies where name = ?";
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, companyName);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.IS_EXISTS, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public boolean isCompanyExistsByEmail(final String companyEmail) throws EntityCrudException {
        Connection connection = null;
        final String sqlStatement = "select * from companies where email = ?";
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, companyEmail);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COMPANY, CrudOperation.IS_EXISTS, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }


}
