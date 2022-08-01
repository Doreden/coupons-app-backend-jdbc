package com.dor.couponssystem.facade;

import com.dor.couponssystem.db.dao.CompanyDAO;
import com.dor.couponssystem.db.dao.CouponDAO;
import com.dor.couponssystem.db.dao.CustomerDAO;
import com.dor.couponssystem.enums.EntityType;
import com.dor.couponssystem.exceptions.EmailAlreadyExistsException;
import com.dor.couponssystem.exceptions.EntityCrudException;
import com.dor.couponssystem.exceptions.NameAlreadyExistsException;
import com.dor.couponssystem.exceptions.UpdateException;
import com.dor.couponssystem.model.Company;
import com.dor.couponssystem.model.Customer;

import java.util.ArrayList;

public class AdminFacade implements ClientFacade {

    public static AdminFacade instance = new AdminFacade();
    private final CustomerDAO customerDAO = CustomerDAO.instance;
    private final CompanyDAO companyDAO = CompanyDAO.instance;
    private final CouponDAO couponDAO = CouponDAO.instance;

    private static final String ADMIN_EMAIL = "admin@admin.com";
    private static final String ADMIN_PASSWORD = "admin";

    @Override
    public boolean login(String email, String password) {
        return ADMIN_EMAIL.equals(email) && ADMIN_PASSWORD.equals(password);
    }

    public void addCompany(Company companyToAdd) throws EntityCrudException, NameAlreadyExistsException, EmailAlreadyExistsException {
        if (companyDAO.isCompanyExistsByName(companyToAdd.getName())) {
            throw new NameAlreadyExistsException(companyToAdd.getName(), EntityType.COMPANY);
        }
        if (companyDAO.isCompanyExistsByEmail(companyToAdd.getEmail())){
            throw new EmailAlreadyExistsException(companyToAdd.getEmail(),EntityType.COMPANY);
        }
        companyDAO.create(companyToAdd);
        System.out.println("Successfully created new company in DB");
    }

    public void updateCompany(Company companyToUpdate) throws EntityCrudException, UpdateException, EmailAlreadyExistsException {
        Company companyDB = companyDAO.read(companyToUpdate.getId());
        if (!companyDB.getName().equals(companyToUpdate.getName())) {
            throw new UpdateException("update the company name is not allowed");
        }
        companyDAO.update(companyToUpdate);
    }


    public void deleteCompany(Long companyIDToDeleteBy) throws EntityCrudException {
        // foreign key cascade will delete the purchased history
        companyDAO.delete(companyIDToDeleteBy);
    }

    public ArrayList<Company> getAllCompanies() throws EntityCrudException {
        return companyDAO.readAll();
    }

    public Company getOneCompany(Long companyToGetByID) throws EntityCrudException {
        return companyDAO.read(companyToGetByID);
    }

    public void addCustomer(Customer customerToAdd) throws EntityCrudException, EmailAlreadyExistsException {
        if (customerDAO.isCustomerExistsByEmail(customerToAdd.getEmail())) {
            throw new EmailAlreadyExistsException(customerToAdd.getEmail(),EntityType.CUSTOMER);
        }
        customerDAO.create(customerToAdd);
    }

    public void updateCustomer(Customer customerToUpdate) throws EntityCrudException, EmailAlreadyExistsException {
        customerDAO.update(customerToUpdate);
    }

    public void deleteCustomer(Long customerToDeleteByID) throws EntityCrudException {
        // foreign key cascade will delete the purchased history
        customerDAO.delete(customerToDeleteByID);
    }

    public ArrayList<Customer> getAllCustomers() throws EntityCrudException {
        return customerDAO.readAll();
    }

    public Customer getOneCustomer(Long customerToGetByID) throws EntityCrudException {
        return customerDAO.read(customerToGetByID);

    }
}
