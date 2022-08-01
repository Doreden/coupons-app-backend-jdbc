package com.dor.couponssystem.exceptions;

public class CouponAlreadyCreatedByCompanyException extends Exception {

    public CouponAlreadyCreatedByCompanyException(String couponTitle ,Long companyID) {
        super(String.format("%s is already exists and created by %s",couponTitle,companyID));
    }
}
