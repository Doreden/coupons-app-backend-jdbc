package com.dor.couponssystem.tasks;

import com.dor.couponssystem.db.dao.CouponDAO;

import java.util.concurrent.TimeUnit;

public class CouponExpirationDailyJob implements Runnable {
    private final CouponDAO couponDAO = CouponDAO.instance;
    public static boolean isRunning = true;

    @Override
    public void run() {
        while (isRunning){

            try {
                couponDAO.deleteExpiredCoupons();
                Thread.sleep(TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    public void stop() {
//        isRunning = false;
//    }
}
