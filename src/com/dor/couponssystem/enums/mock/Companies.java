package com.dor.couponssystem.enums.mock;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public enum Companies {
    AMPM("ampm", "service@ampm.com", "ampm123"),
    SUPERYUDA("superyuda","marketing@superyuda.co.il","yuda123"),
    DOMINOS("dominos","marketing@dominos.com","dominos123"),
    MCDONALDS("mcdonalds","sales@mcdonalds.com","mcdonalds123"),
    SHUPERSAL("shupersal","sales@shupersal.co.il","shupersal123"),
    OSHERAD("osherad","basad@osherad.co.il","osherad123"),
    IVORY("ivory","sales&marketing@ivory.co.il","ivory123"),
    KSP("ksp","sales&marketing@ksp.co.il","ksp123"),
    YELP("yelp","marketing@yelp.com","ksp123"),
    TRIPADVISOR("tripadvisor","sales@tripadvisor.com","tripadvisor123"),
    AIRBNB("airbnb","sales&housing@airbnb.com","airbnb123"),
    BOOKING("booking","sales&flight@booking.com","booking123"),
    HACEREM("hacerem","sales@hacerem.com","hacerem123"),
    COCACOLA("cocacola","drinks&fun@cocacola.com","cola123"),
    ADIDAS("adidas","sales&marketing@adidas.com","adidas123"),
    CASTRO("castro","marketing@castro.com","castro123");

    private final String name;
    private final String email;
    private final String password;

    public static List<Companies> getList(){
        return (List<Companies>)Arrays.asList(Companies.values());
    }
}


