package com.dor.couponssystem.exceptions;

public class CouponAlreadyPurchasedException extends Exception{
    public CouponAlreadyPurchasedException(Long customerID,Long couponID) {
        super(String.format("Customer %d Coupon %d already purchased",customerID,couponID));

    }
}
