package com.dor.couponssystem.exceptions;

public class CouponIsOutStockException extends Exception {

    public CouponIsOutStockException(String couponTitle, Long couponID) {
        super(String.format("The Coupon %s %d is out of stock",couponTitle,couponID));
    }
}
