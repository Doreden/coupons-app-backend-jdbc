package com.dor.couponssystem.db.dao;

import com.dor.couponssystem.db.connection.ConnectionPool;
import com.dor.couponssystem.enums.Category;
import com.dor.couponssystem.enums.CrudOperation;
import com.dor.couponssystem.enums.EntityType;
import com.dor.couponssystem.exceptions.EntityCrudException;
import com.dor.couponssystem.model.Coupon;
import com.dor.couponssystem.utills.ObjectExtractionUtil;
import java.sql.*;
import java.util.ArrayList;

public class CouponDAO implements CrudDAO<Long, Coupon> {

    // Attributes:
    public static final CouponDAO instance = new CouponDAO();
    private final ConnectionPool connectionPool;

    // Establish Connection to DB scheme
    public CouponDAO() {
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to establish connection with database",e);
        }
    }

    //-----------------------------------------------------Add Coupon -----------------------------------------------------//
    // Create a new Coupon in the Coupons SQL Table
    @Override
    public Long create(final Coupon coupon) throws EntityCrudException {
        Connection connection = null;
        final String sqlStatement = "INSERT into coupons(company_id,category,title,description,start_date,end_date,amount,price,image) values(?,?,?,?,?,?,?,?,?)";
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, coupon.getCompanyID());
            preparedStatement.setString(2, coupon.getCategory().toString());
            preparedStatement.setString(3, coupon.getTitle());
            preparedStatement.setString(4, coupon.getDescription());
            preparedStatement.setDate(5, coupon.getStartDate());
            preparedStatement.setDate(6, coupon.getEndDate());
            preparedStatement.setInt(7, coupon.getAmount());
            preparedStatement.setDouble(8, coupon.getPrice());
            preparedStatement.setString(9, coupon.getImage());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (!resultSet.next()) {
                return resultSet.getLong(1);
            }
            return -1L;
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.CREATE,e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    //-----------------------------------------------------Get One Coupon -----------------------------------------------------//
    @Override
    public Coupon read(Long id) throws EntityCrudException {
        Connection connection = null;
        Coupon couponToReturn = null;
        final String sqlStatement = "select * from Coupons where id = ?";
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return ObjectExtractionUtil.generateCouponFormResultSet(resultSet);
            }
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.READ,e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return couponToReturn;
    }

    //  -----------------------------------------------------Update Coupon-----------------------------------------------------//
    @Override
    public void update(Coupon coupon) throws EntityCrudException {
        Connection connection = null;
        final String sqlStatement = "UPDATE coupons set company_id = ?, category = ?, title = ?, description = ?, start_date = ?, end_date = ?, amount = ?, price = ?, image = ?";
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setLong(1, coupon.getCompanyID());
            preparedStatement.setString(2, coupon.getCategory().toString());
            preparedStatement.setString(3, coupon.getTitle());
            preparedStatement.setString(4, coupon.getDescription());
            preparedStatement.setDate(5, coupon.getStartDate());
            preparedStatement.setDate(6, coupon.getEndDate());
            preparedStatement.setInt(7, coupon.getAmount());
            preparedStatement.setDouble(8, coupon.getPrice());
            preparedStatement.setString(9, coupon.getImage());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.UPDATE,e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        System.out.println("Coupon Updated Successfully!");
    }

    //-----------------------------------------------------Delete Coupon-----------------------------------------------------//
    @Override
    public void delete(Long id) throws EntityCrudException {
        Connection connection = null;
        final String sqlStatement = "DELETE from coupons where id = ?";
        Coupon couponToDelete = null;
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.DELETE,e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        System.out.println("Coupon Deleted Successfully!");
    }

    //----------------------------------------------------- Get All Coupons-----------------------------------------------------//
    @Override
    public ArrayList<Coupon> readAll() throws EntityCrudException {
        Connection connection = null;
        final String sqlStatement = "SELECT * from coupons";
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            final ArrayList<Coupon> couponList = new ArrayList<>();
            while (resultSet.next()) {
                couponList.add(ObjectExtractionUtil.generateCouponFormResultSet(resultSet));
            }
            return couponList;
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.CREATE,e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
    //-----------------------------------------------------new new relevant methods -----------------------------------------------------//
    public boolean isCouponExistsByCouponTitle(final Long companyID,final String couponTitle) throws EntityCrudException {
        Connection connection = null;
        final String sqlStatement = "select * from coupons where company_id = ? AND title = ?";
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setLong(1, companyID);
            preparedStatement.setString(2,couponTitle );
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.IS_EXISTS,e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
    //-----------------------------------------------------Coupon For Customer DB relevant methods -----------------------------------------------------//
    //-----------------------------------------------------Read/Get Methods----------------------------------------------------------------
    /**
     * Get a coupons list by the customer id:
     *
     * @param customerID
     * @return an ArrayList of coupons by the specified Customer ID!
     * @throws EntityCrudException - If didn't succeed to get a list of to coupons
     *
     */
    public ArrayList<Coupon> getCouponsForCustomer(Long customerID) throws EntityCrudException {
        Connection connection = null;
            final String sqlStatemnet = "SELECT * FROM coupons JOIN customers_vs_coupons ON customer_id = ? AND coupon_id = id";
        try {
            connection = connectionPool.getConnection();
            //connection = ConnectionPool.getInstance().getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatemnet);
            preparedStatement.setLong(1, customerID);
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Coupon> customerToCoupon = new ArrayList<>();
            while (resultSet.next()) {
                customerToCoupon.add(ObjectExtractionUtil.generateCouponFormResultSet(resultSet));
            }
            return customerToCoupon;
        } catch (SQLException | InterruptedException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.READ,e);
        }
        finally {
            connectionPool.returnConnection(connection);
        }
    }

    public ArrayList<Coupon> getCouponsForCustomerByCategory(Long customerID, Category category) throws EntityCrudException {
        Connection connection = null;
            final String sqlStatement = "SELECT * FROM coupons JOIN customers_vs_coupons ON customer_id = ? AND coupon_id = id AND category = ?";
        try {
            connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setLong(1, customerID);
            preparedStatement.setString(2, category.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Coupon> couponsByCategoryList = new ArrayList<>();
            while (resultSet.next()) {
                couponsByCategoryList.add(ObjectExtractionUtil.generateCouponFormResultSet(resultSet));
            }
            return couponsByCategoryList;
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.READ,e);
        }
        finally {
            connectionPool.returnConnection(connection);
        }
    }

    public ArrayList<Coupon> getCouponsForCustomerByPrice(Long customerID, double couponMaxPrice) throws EntityCrudException {
        Connection connection = null;
            final String sqlStatement = "SELECT * From coupons JOIN customers_vs_coupons ON customer_id = ? AND coupon_id = id AND price < ?";
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setLong(1, customerID);
            preparedStatement.setDouble(2, couponMaxPrice);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Coupon> couponsPriceList = new ArrayList<>();
            while (resultSet.next()) {
                couponsPriceList.add(ObjectExtractionUtil.generateCouponFormResultSet(resultSet));
            }
            return couponsPriceList;
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.READ,e);
        }
        finally {
            connectionPool.returnConnection(connection);
        }
    }

    //----------------------------------------------------- Add/Create Methods -------------------------------------------------------//
    //----------------------------------------------------- Add Coupon Purchase -----------------------------------------------------//
    public void addCouponPurchase(Long couponID,Long customerID) throws EntityCrudException {
        Connection connection = null;
        final String sqlStatement = "INSERT INTO customers_vs_coupons(coupon_id,customer_id) values (?,?)";
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setLong(1, couponID);
            preparedStatement.setLong(2, customerID);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw  new EntityCrudException(EntityType.CUSTOMER_VS_COUPON,CrudOperation.CREATE,e);
        }
        finally {
            connectionPool.returnConnection(connection);
        }
    }

    //------------------------------------------------------------ Delete Methods ----------------------------------------------------------------------//
    //----------------------------------------------------- Delete Coupon Purchase by customer ID -----------------------------------------------------//
    public void deleteCouponPurchaseByCustomer(Long customerID, Long couponID) throws EntityCrudException {
        Connection connection = null;
        final String sqlStatement = "DELETE FROM customers_vs_coupons WHERE customer_id = ? AND coupon_id = ?";
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, customerID);
            preparedStatement.setLong(2, couponID);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.CUSTOMER_VS_COUPON, CrudOperation.DELETE,e);
        }

    }

    //----------------------------------------------------- Delete Coupon Purchase by coupon ID -----------------------------------------------------//
    public void deleteCouponPurchasedByCouponID(final Long couponID) throws EntityCrudException {
        Connection connection = null;
            final String sqlstatement = "DELETE FROM customers_vs_coupons WHERE coupon_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement);
            preparedStatement.setLong(1, couponID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.CUSTOMER_VS_COUPON, CrudOperation.DELETE,e);
        }
    }

    //----------------------------------------------------- Methods relevant for company DB using company id inside coupon -----------------------------------------------------//
    //------------------------------------------------------------------- Read/Get Methods -------------------------------------------------------------------------------------//
    public ArrayList<Coupon> getCompanyCoupons(final Long companyID) throws EntityCrudException {
        Connection connection = null;
        final String sqlStatement = "SELECT * FROM coupons WHERE company_id = ?";
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setLong(1,companyID);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Coupon> companyCouponsList = new ArrayList<>();
            while (resultSet.next()) {
                companyCouponsList.add(ObjectExtractionUtil.generateCouponFormResultSet(resultSet));
            }
            return companyCouponsList;
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COMPANY,CrudOperation.READ,e);
        }
        finally {
            connectionPool.returnConnection(connection);
        }
    }

    public ArrayList<Coupon> getCompanyCouponsByCategory(final Long companyID, final Category category) throws EntityCrudException {
        Connection connection = null;
        final String sqlStatement = "SELECT * FROM coupons WHERE company_id = ? AND category = ?";
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setLong(1,companyID);
            preparedStatement.setString(2,category.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Coupon>companyCouponsListByCategory = new ArrayList<>();
            while (resultSet.next()) {
                companyCouponsListByCategory.add(ObjectExtractionUtil.generateCouponFormResultSet(resultSet));
            }
            return companyCouponsListByCategory;
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COMPANY,CrudOperation.READ,e);
        }
        finally {
            connectionPool.returnConnection(connection);
        }
    }
    public ArrayList<Coupon> getCompanyCouponsByMaxPrice(final Long companyID, final Double price) throws EntityCrudException {
        Connection connection = null;
        final String sqlStatement = "SELECT * FROM coupons WHERE company_id = ? AND price <= ?";
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setLong(1,companyID);
            preparedStatement.setDouble(2,price);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Coupon>companyCouponsListByCategory = new ArrayList<>();
            while (resultSet.next()) {
                companyCouponsListByCategory.add(ObjectExtractionUtil.generateCouponFormResultSet(resultSet));
            }
            return companyCouponsListByCategory;
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COMPANY,CrudOperation.READ,e);
        }
        finally {
            connectionPool.returnConnection(connection);
        }
    }
    //-----------------------------------------------------11111Delete Coupon-----------------------------------------------------//
    public void deleteExpiredCoupons() throws EntityCrudException {
        Connection connection = null;
        final String sqlStatement = "DELETE FROM coupons WHERE end_date < CURRENT_TIME();";
        try {
            connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.DELETE,e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }


}
