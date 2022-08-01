package com.dor.couponssystem.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ClientType {

    ADMINISTRATOR(1, "admin"),
    COMPANY(2, "company"),
    CUSTOMER(3, "");

    private int code;
    private String viewName;

    public static ClientType getById(int id){
        return ClientType.values()[id-1];
    }
}
