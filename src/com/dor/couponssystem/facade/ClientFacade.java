package com.dor.couponssystem.facade;

import com.dor.couponssystem.exceptions.AuthenticationException;
import com.dor.couponssystem.exceptions.EntityCrudException;

public interface ClientFacade {
//    public final CompanyDAO companyDAO = new CompanyDAO();
//    public final CustomerDAO customerDAO = new CustomerDAO();
//    public final CouponDAO couponDAO = new CouponDAO();

    public boolean login(String email, String password) throws AuthenticationException, EntityCrudException;
}
