package com.dor.couponssystem.facade;

import com.dor.couponssystem.db.dao.CompanyDAO;
import com.dor.couponssystem.db.dao.CouponDAO;
import com.dor.couponssystem.enums.Category;
import com.dor.couponssystem.exceptions.AuthenticationException;
import com.dor.couponssystem.exceptions.CouponAlreadyCreatedByCompanyException;
import com.dor.couponssystem.exceptions.EntityCrudException;
import com.dor.couponssystem.exceptions.UpdateException;
import com.dor.couponssystem.model.Company;
import com.dor.couponssystem.model.Coupon;

import java.util.ArrayList;

public class CompanyFacade implements ClientFacade {
    public static final CompanyFacade instance = new CompanyFacade();
    private final CouponDAO couponDAO = CouponDAO.instance;
    private final CompanyDAO companyDAO = CompanyDAO.instance;


    @Override
    public boolean login(String email, String password) throws AuthenticationException, EntityCrudException {
        if (companyDAO.isCompanyExists(email, password)) {
            return true;
        }
        throw new AuthenticationException(email);
    }

    public final Long addCouponByCompany(Long companyIDThatCreating,Coupon coupon) throws EntityCrudException, CouponAlreadyCreatedByCompanyException {
        if (couponDAO.isCouponExistsByCouponTitle(companyIDThatCreating,coupon.getTitle())) {
            throw new CouponAlreadyCreatedByCompanyException(coupon.getTitle(),companyIDThatCreating);
        }
      return couponDAO.create(coupon);
    }


    public void updateCouponByCompany(Coupon coupon, Long companyID) throws UpdateException, EntityCrudException {
        if (companyID != coupon.getCompanyID()) {
            throw new UpdateException("This coupon is not belongs the logged in company your company\n" +
                    "Coupon company_id = " + coupon.getCompanyID() +
                    "\n" +
                    "Logged in company id = " + companyID);
        }

        couponDAO.update(coupon);
    }


    public void deleteCoupon(final Long couponToDeleteByID) throws EntityCrudException {
        couponDAO.delete(couponToDeleteByID);
//        couponDAO.deleteCouponPurchasedByCouponID(couponToDeleteByID);
    }

    public ArrayList<Coupon> getCompanyCouponsByCompanyID(final Long companyID) throws EntityCrudException {
        return couponDAO.getCompanyCoupons(companyID);
    }

    public ArrayList<Coupon> getCompanyCouponsByCategory(final Long companyID, final Category category) throws EntityCrudException {
        return couponDAO.getCompanyCouponsByCategory(companyID, category);
    }

    public ArrayList<Coupon> getCompanyCouponsByPrice(final Long companyID, final Double couponMaxPrice) throws EntityCrudException {
        return couponDAO.getCompanyCouponsByMaxPrice(companyID, couponMaxPrice);
    }

    public Company getCompanyDetails(final Long companyID) throws EntityCrudException {
        return companyDAO.read(companyID);
    }

}





