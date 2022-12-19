package com.dor.couponssystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer {

    //Attributes:
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
//    private ArrayList<Coupon> coupons;

    //Class CTOR:

    public Customer(final String firstName,final String lastName, final String email, final String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

//    public void setPassword(final String password) {
//        this.password = password.hashCode();
//    }
//
//    public int getPassword() {
//        return password;
//    }
}
