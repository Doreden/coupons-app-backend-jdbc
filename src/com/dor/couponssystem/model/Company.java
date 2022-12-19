package com.dor.couponssystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Company {
    //Company Class Attributes:
    private Long id;
    private String name;
    private String email;
    private String password;
//    private ArrayList<Coupon> coupons;

    // Class CTOR:
    public Company(final String name, final String email, final String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }


//    public void setPassword(final String password){
//    }

//    public int getPassword() {
//        return password;
//    }
}
