package com.dor.couponssystem.exceptions;

import java.sql.Date;

public class CouponExpiredException extends Exception {

    public CouponExpiredException(String couponName, Long couponID, Date couponExpirationDate) {
        super(String.format("coupon %s %d has been expired in - %s",couponName,couponID,couponExpirationDate.toString()));
    }
}
