package com.dor.couponssystem.model;

import com.dor.couponssystem.enums.Category;
import lombok.*;

import java.sql.Date;


//Class Get&Set,CTOR,ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Coupon {

    //Class Coupon Attributes:
    private long id;
    public long companyID;
    public Category category;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int amount;
    private double price;
    private String image;

    // Class Req args CTOR:
    public Coupon(final Long companyID,final Category category,final String title, final String description, final Date startDate, final Date endDate, final int amount, final double price, final String image) {
        this.companyID = companyID;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    public void purchase() {
        if (getAmount() > 0) {
            setAmount(getAmount() - 1);
        }
    }

    public void setCategory(final String category) {
        this.category = Category.valueOf((String)category);
    }
}


