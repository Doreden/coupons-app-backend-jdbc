package com.dor.couponssystem.utills;

import com.dor.couponssystem.model.Company;
import com.dor.couponssystem.model.Coupon;
import com.dor.couponssystem.model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ObjectExtractionUtil {

    public static Company generateCompanyFromResultSet(ResultSet resultSet) throws SQLException {
        Company company = new Company();
        company.setId(resultSet.getLong("id"));
        company.setName(resultSet.getString("name"));
        company.setEmail(resultSet.getString("email"));
        company.setPassword(resultSet.getString("password"));
        return company;
    }

    public static Customer generateCustomerFromResultSet(ResultSet resultSet) throws SQLException{
        Customer customer = new Customer();
        customer.setId(resultSet.getLong("id"));
        customer.setFirstName(resultSet.getString("first_name"));
        customer.setLastName(resultSet.getString("last_name"));
        customer.setEmail(resultSet.getString("email"));
        customer.setPassword(resultSet.getString("password"));
        return customer;
    }

    public static Coupon generateCouponFormResultSet(ResultSet resultSet) throws SQLException{
        Coupon coupon = new Coupon();
        coupon.setId(resultSet.getLong("id"));
        coupon.setCompanyID(resultSet.getLong("company_id"));
        coupon.setCategory(resultSet.getString("category"));
        coupon.setTitle(resultSet.getString("title"));
        coupon.setDescription(resultSet.getString("description"));
        coupon.setStartDate(resultSet.getDate("start_date"));
        coupon.setEndDate(resultSet.getDate("end_date"));
        coupon.setAmount(resultSet.getInt("amount"));
        coupon.setPrice(resultSet.getDouble("price"));
        coupon.setImage(resultSet.getString("image"));
        return coupon;
    }
}

