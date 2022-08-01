package com.dor.couponssystem;

import com.dor.couponssystem.tests.Test;

public class Program {

    public static void main(String[] args) {
        Test test = Test.instance;
//        test.deleteAllTables();
        test.testAll();


    }
}
