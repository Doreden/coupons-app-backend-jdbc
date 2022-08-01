package com.dor.couponssystem.enums.mock;

import com.dor.couponssystem.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Date;

@AllArgsConstructor
@Getter

public enum Coupons {
    AMPM(1L,Category.BEVERAGE,"1+1 Milk","second milk carton for free",Date.valueOf("2022-03-29"),Date.valueOf("2022-03-31"),2,10.00,"MILK image"),
    AMPM2(1L,Category.FOOD,"1+1 Chocolate","second Chocolate box for free",Date.valueOf("2022-03-29"),Date.valueOf("2022-03-31"),2,5.00,"Chocolate image"),
    SUPERYUDA(2L,Category.FOOD,"1+1 Shampoo","second Shampoo bottle for free",Date.valueOf("2022-03-29"),Date.valueOf("2022-03-31"),2,20.00,"Shampoo image"),
    SUPERYUDA2(2L,Category.FOOD,"1+1 Soap","second Soap bottle for free",Date.valueOf("2022-03-29"),Date.valueOf("2022-03-31"),2,7.00,"Soap image"),
    DOMINOS(3L,Category.JUNKFOOD,"1+1 Pizza","second Pizza for free",Date.valueOf("2022-03-29"),Date.valueOf("2022-03-31"),2,50.00,"Pizza image"),
    MCDONALDS(4L,Category.JUNKFOOD,"1+1 Burger","second Burger for free",Date.valueOf("2022-03-29"),Date.valueOf("2022-03-31"),2,30,"Burger image"),
    SHUPERSAL(5L,Category.ELECTRICITY,"50% Off microwave","50% Off microwave above 200NIS",Date.valueOf("2022-03-29"),Date.valueOf("2022-03-31"),2,100.00,"micro image"),
    OSHERAD(6L,Category.BEVERAGE,"1+1 crystal","second crystal bottle for free",Date.valueOf("2022-03-29"),Date.valueOf("2022-03-31"),2,2.00,"crystal bottle image"),
    IVORY(7L,Category.ELECTRICITY,"20% off Keyboards","20% off all logitech Keyboards",Date.valueOf("2022-03-29"),Date.valueOf("2022-03-31"),2,50.00,"logitech keyboard image"),
    KSP(8L,Category.ELECTRICITY,"25% off Keyboards","25% off all logitech Keyboards",Date.valueOf("2022-03-29"),Date.valueOf("2022-03-31"),2,45.00,"logitech keyboard image"),
    YELP(9L,Category.RESTAURANT,"40% off tlv restaurants","40% off tlv restaurants only on 400NIS and above bill",Date.valueOf("2022-03-29"),Date.valueOf("2022-03-31"),2,100.00,"TLV image"),
    TRIPADVISOR(10L,Category.RESTAURANT,"40% off tlv restaurants","40% off tlv restaurants only on 300NIS and above bill",Date.valueOf("2022-03-29"),Date.valueOf("2022-03-31"),2,75.00,"TLV image"),
    AIRBNB(11L,Category.VACATION,"40% off tlv apartments","40% off tlv apartments only on south tlv",Date.valueOf("2022-03-29"),Date.valueOf("2022-03-31"),2,200.00,"TLV image"),
    BOOKING(12L,Category.VACATION,"40% off europe hotels","40% off europe hotels only on winter",Date.valueOf("2022-03-29"),Date.valueOf("2022-03-31"),2,300.00,"europe"),
    HACEREM(13L,Category.BEVERAGE,"1+1 beer","second beer bottle for free",Date.valueOf("2022-03-29"),Date.valueOf("2022-03-31"),2,7.00,"BEER image"),
    COCACOLA(14L,Category.BEVERAGE,"1+1 cola","second cola bottle for free",Date.valueOf("2022-03-29"),Date.valueOf("2022-03-31"),2,7.00,"COLA image"),
    ADIDAS(15L,Category.SHOES,"12% Off all the shoes","Discount apply only on running shoes!",Date.valueOf("2022-03-29"),Date.valueOf("2022-03-31"),2,20.00,"adidas running shoes image"),
    CASTRO(16L,Category.CLOTHING,"20% Off all the jeans pants","20% Off all the jeans pants above 200NIS",Date.valueOf("2022-03-29"),Date.valueOf("2022-03-31"),2,50.00,"jeans pants image");




    public long companyID;
    public Category category;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int amount;
    private double price;
    private String image;
}
