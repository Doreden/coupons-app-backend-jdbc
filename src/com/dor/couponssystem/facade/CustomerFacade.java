package com.dor.couponssystem.facade;

import com.dor.couponssystem.db.dao.CouponDAO;
import com.dor.couponssystem.db.dao.CustomerDAO;
import com.dor.couponssystem.enums.Category;
import com.dor.couponssystem.exceptions.*;
import com.dor.couponssystem.model.Coupon;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;

@Data
@NoArgsConstructor
public class CustomerFacade implements ClientFacade {

    public static CustomerFacade instance = new CustomerFacade();

    private final CouponDAO couponDAO = CouponDAO.instance;
    private final CustomerDAO customerDAO = CustomerDAO.instance;

    @Override
    public boolean login(String email, String password) throws AuthenticationException, EntityCrudException {
           if (customerDAO.isExists(email,password)){
                return true;
            }
            throw new AuthenticationException(email);
        }


    public void purchaseCoupon(final Long couponToBuyID, final Long customerID) throws CouponAlreadyPurchasedException, CouponIsOutStockException, CouponExpiredException, EntityCrudException {
        Coupon coupon = couponDAO.read(couponToBuyID);

        if (customerDAO.isCouponPurchasedByCustomer(couponToBuyID,customerID)){
            throw new CouponAlreadyPurchasedException(customerID,couponToBuyID);
        }

        if (coupon.getAmount() == 0){
            throw  new CouponIsOutStockException(coupon.getTitle(),couponToBuyID);
        }

        if (coupon.getEndDate().before(new Date())){
            throw new CouponExpiredException(coupon.getTitle(),couponToBuyID,coupon.getEndDate());
        }

        couponDAO.addCouponPurchase(couponToBuyID, customerID);

        coupon.setAmount(coupon.getAmount()-1);

        couponDAO.update(coupon);
    }

    public ArrayList<Coupon> getCustomerCoupons(Long customerID) throws EntityCrudException {
        return couponDAO.getCouponsForCustomer(customerID);
    }

    public ArrayList<Coupon> getCustomerCouponsByCategory(Long customerID,Category category) throws EntityCrudException {
        return couponDAO.getCouponsForCustomerByCategory(customerID, category);
    }

    public ArrayList<Coupon> getCustomerCouponsByPrice(final long customerID, final double maxCouponPrice) throws EntityCrudException {
        return couponDAO.getCouponsForCustomerByPrice(customerID,maxCouponPrice);
    }

    public void getCustomerDetails(final long customerID) throws EntityCrudException {
        System.out.println(customerDAO.read(customerID));
    }
}

