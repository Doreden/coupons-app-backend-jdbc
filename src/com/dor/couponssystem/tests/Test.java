package com.dor.couponssystem.tests;

import com.dor.couponssystem.auth.LoginManager;
import com.dor.couponssystem.db.dao.CompanyDAO;
import com.dor.couponssystem.db.dao.CustomerDAO;
import com.dor.couponssystem.db.utils.DatabaseInitializer;
import com.dor.couponssystem.enums.Category;
import com.dor.couponssystem.enums.ClientType;
import com.dor.couponssystem.enums.mock.Companies;
import com.dor.couponssystem.enums.mock.Coupons;
import com.dor.couponssystem.enums.mock.Customers;
import com.dor.couponssystem.exceptions.*;
import com.dor.couponssystem.facade.AdminFacade;
import com.dor.couponssystem.facade.CompanyFacade;
import com.dor.couponssystem.facade.CustomerFacade;
import com.dor.couponssystem.model.Company;
import com.dor.couponssystem.model.Coupon;
import com.dor.couponssystem.model.Customer;
import com.dor.couponssystem.tasks.CouponExpirationDailyJob;

import java.sql.Date;
import java.util.Scanner;

public class Test {
    private CouponExpirationDailyJob dailyJob = new CouponExpirationDailyJob();
    private Thread thread;
    private static ClientType type;
    private static final Scanner scanner = new Scanner(System.in);


    private void startDailyJob() {
        thread = new Thread(dailyJob);
        thread.start();
    }

    private void cleanUp() {
//        dailyJob.stop();
//        thread.interrupt();
    }


    public void testAll() {
        try {
            databaseInitializer.createAllTables();
            startDailyJob();
            printLogin();
            switch (type) {
                case ADMINISTRATOR:
                    testAdmin();
                    break;

                case COMPANY:
                    testCompany();
                    break;

                case CUSTOMER:
                    testCustomer();
                    break;


            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


    private void printLogin() throws AuthenticationException, EntityCrudException {
        int userChoose = -1;
        do {
            System.out.println("Hello!\n" +
                    "Please choose your client type: \n" +
                    "press 1 to log in as admin\n" +
                    "press 2 to log in as company\n" +
                    "press 3 to log in as customer");
            userChoose = scanner.nextInt();
            String userLoginEmail = null, userLoginPassword = null;
            if (userChoose > 0 && userChoose < 4) {
                type = ClientType.getById(userChoose);
                System.out.printf("please enter your %s Email address: ", type.getViewName());
                userLoginEmail = scanner.next();
                System.out.printf("please enter your %s password:", type.getViewName());
                userLoginPassword = scanner.next();
                loginManager.login(
                        userLoginEmail,
                        userLoginPassword,
                        type
                );
            } else {
                System.err.println("you entered invalid number! \r" +
                        "choose only valid number!");
                System.out.println("please try again ");
            }
        } while (userChoose > 3 || userChoose < 1);


    }

    //-------------------------------Coupon-system Instances-------------------------------//
    public static Test instance = new Test();
    DatabaseInitializer databaseInitializer = DatabaseInitializer.instance;
    LoginManager loginManager = LoginManager.instance;
    AdminFacade adminFacade = AdminFacade.instance;
    CompanyFacade companyFacade = CompanyFacade.instance;
    CustomerFacade customerFacade = CustomerFacade.instance;

    //-------------------------------Admin Facade All Methods Tests-------------------------------//
    private void testAdmin() {
        //--->>>>||1.
        //--->>>> |Creating all coupon-system tables| <<<<---//
        try {
            createAllTables();
        } catch (Exception e) {
            System.err.println("An Error accrued:" + e.getMessage());
        }
        //***************************************************//

        //--->>>>||2.
        //--->>>> |Creating all companies | <<<<---//
        Companies.getList().forEach((mockCompany) -> {
                    try {
                        adminFacade.addCompany(
                                new Company(mockCompany.getName(),
                                        mockCompany.getEmail(),
                                        mockCompany.getPassword()
                                ));
                    } catch (Exception e) {
                        System.err.println("Error have been occurred : \n" +
                                e.getMessage());
                    }
                }

        );
        //***************************************************//

        //--->>>>||3.
        //--->>>> |Creating all customers | <<<<---//
        Customers.getList().forEach((mockCustomer) -> {
                    try {
                        adminFacade.addCustomer(
                                new Customer(mockCustomer.getFirstName(),
                                        mockCustomer.getLastName(),
                                        mockCustomer.getEmail(),
                                        mockCustomer.getPassword()
                                ));
                    } catch (Exception e) {
                        System.err.println("Error have been occurred : \n" +
                                e.getMessage());
                    }
                }
        );
        //***************************************************//

        //--->>>>||4.
        //--->>>> |Getting & print all companies | <<<<---//
        try {
            System.out.println(adminFacade.getAllCompanies().toString());
        } catch (Exception e) {
            System.err.println("Error have been occurred : \n" +
                    e.getMessage());
        }

        //***************************************************//

        //--->>>>||4.
        //--->>>> |Getting & print all customers | <<<<---//
        try {
            System.out.println(adminFacade.getAllCustomers().toString());
        } catch (Exception e) {
            System.err.println("Error have been occurred : \n" +
                    e.getMessage());
        }
    }
    //********************************************************//

    //-------------------------------Company All Methods Tests-------------------------------//
    private void testCompany() {
        try {
            //--->>>>||1.
            //--->>>> |Authenticate Login by company email | <<<<---//
            long companyId = CompanyDAO.instance.getCompanyId(loginManager.authenticatedUser.get(0));
            //--->>>>||2.
            //--->>>> |Print the company menus & implements all the company facade methods| <<<<---//
            //--->>>> company password is hashed so its template will be company name in lowerCase + 123 --> ex: company123
            companyMenu();
        } catch (Exception e) {
            System.err.println("Error have been occurred : \n" +
                    e.getMessage());
        }
    }
    //***************************************************************************************//

    //-------------------------------Customer All Methods Tests-------------------------------//
    private void testCustomer() {
        try {
            //--->>>>||1.
            //--->>>> |Authenticate Login by customer email (using customer_id) | <<<<---//
            long customerId = CustomerDAO.instance.getCustomerId(loginManager.authenticatedUser.get(0));
            //--->>>>||2.
            //--->>>> |Print the customer menus & implements all the customer facade methods| <<<<---//
            //--->>>> customer password is hashed so its template will be customer name in lowerCase + 123 --> ex: customer123
            customerMenu();
            // Catch all the exception:
        } catch (Exception e) {
            // Print the Error + exception thrown message:
            System.err.println("Error have been occurred : \n" +
                    e.getMessage());
        }


    }
    //***************************************************************************************//

    //-------------------------------Company Menu Method-------------------------------//
    private void companyMenu() throws CouponAlreadyCreatedByCompanyException, EntityCrudException {
        Scanner companyScanner = new Scanner(System.in);
        int companyChoose = -1;

        //--->>>>||1. Print the companyTest Menu
        System.out.println("Hello Company!\n" +
                "Please Select a company to create coupon for : \n" +
                "1 - AMPM\n" +
                "2 - SUPERYUDA\n" +
                "3 - DOMINOS\n" +
                "4 - MCDONALDS\n" +
                "5 - SHUPERSAL\n" +
                "6 - OSHERAD\n" +
                "7 - IVORY\n" +
                "8 - KSP\n" +
                "9 - YELP\n" +
                "10 - TRIPADVISOR\n" +
                "11 - AIRBNB\n" +
                "12 - BOOKING\n" +
                "13 - HACEREM\n" +
                "14 - COCA-COLA\n" +
                "15 - ADIDAS\n" +
                "16 - CASTRO\n" +
                "17 - EXIT this menu"
        );
        companyChoose = companyScanner.nextInt();

        //--->>>>||2. Switch cases for creating coupon from which company
        //--->>>>||* all the company coupons are already prepared in-- enums/mocks/Companies
        switch (companyChoose) {
            case 1:
                companyFacade.addCouponByCompany(1L, new Coupon(
                        Coupons.AMPM.getCompanyID(),
                        Coupons.AMPM.getCategory(),
                        Coupons.AMPM.getTitle(),
                        Coupons.AMPM.getDescription(),
                        Coupons.AMPM.getStartDate(),
                        Coupons.AMPM.getEndDate(),
                        Coupons.AMPM.getAmount(),
                        Coupons.AMPM.getPrice(),
                        Coupons.AMPM.getImage()
                ));
                System.out.println("AMPM Created a new coupon");

                companyFacade.addCouponByCompany(1L, new Coupon(
                        Coupons.AMPM2.getCompanyID(),
                        Coupons.AMPM2.getCategory(),
                        Coupons.AMPM2.getTitle(),
                        Coupons.AMPM2.getDescription(),
                        Coupons.AMPM2.getStartDate(),
                        Coupons.AMPM2.getEndDate(),
                        Coupons.AMPM2.getAmount(),
                        Coupons.AMPM2.getPrice(),
                        Coupons.AMPM2.getImage()
                ));
                System.out.println("AMPM Created a new coupon");
                break;

            case 2:
                companyFacade.addCouponByCompany(2L, new Coupon(
                        Coupons.SUPERYUDA.getCompanyID(),
                        Coupons.SUPERYUDA.getCategory(),
                        Coupons.SUPERYUDA.getTitle(),
                        Coupons.SUPERYUDA.getDescription(),
                        Coupons.SUPERYUDA.getStartDate(),
                        Coupons.SUPERYUDA.getEndDate(),
                        Coupons.SUPERYUDA.getAmount(),
                        Coupons.SUPERYUDA.getPrice(),
                        Coupons.SUPERYUDA.getImage()
                ));
                System.out.println("SUPERYUDA Created a new coupon");
                companyFacade.addCouponByCompany(2L, new Coupon(
                        Coupons.SUPERYUDA2.getCompanyID(),
                        Coupons.SUPERYUDA2.getCategory(),
                        Coupons.SUPERYUDA2.getTitle(),
                        Coupons.SUPERYUDA2.getDescription(),
                        Coupons.SUPERYUDA2.getStartDate(),
                        Coupons.SUPERYUDA2.getEndDate(),
                        Coupons.SUPERYUDA2.getAmount(),
                        Coupons.SUPERYUDA2.getPrice(),
                        Coupons.SUPERYUDA2.getImage()
                ));
                System.out.println("SUPERYUDA Created a new coupon");
                break;

            case 3:
                companyFacade.addCouponByCompany(3L, new Coupon(
                        Coupons.DOMINOS.getCompanyID(),
                        Coupons.DOMINOS.getCategory(),
                        Coupons.DOMINOS.getTitle(),
                        Coupons.DOMINOS.getDescription(),
                        Coupons.DOMINOS.getStartDate(),
                        Coupons.DOMINOS.getEndDate(),
                        Coupons.DOMINOS.getAmount(),
                        Coupons.DOMINOS.getPrice(),
                        Coupons.DOMINOS.getImage()
                ));

                System.out.println("DOMINOS Created a new coupon");
                break;

            case 4:
                companyFacade.addCouponByCompany(4L, new Coupon(
                        Coupons.MCDONALDS.getCompanyID(),
                        Coupons.MCDONALDS.getCategory(),
                        Coupons.MCDONALDS.getTitle(),
                        Coupons.MCDONALDS.getDescription(),
                        Coupons.MCDONALDS.getStartDate(),
                        Coupons.MCDONALDS.getEndDate(),
                        Coupons.MCDONALDS.getAmount(),
                        Coupons.MCDONALDS.getPrice(),
                        Coupons.MCDONALDS.getImage()
                ));
                System.out.println("MCDONALDS Created a new coupon");
                break;

            case 5:
                companyFacade.addCouponByCompany(5L, new Coupon(
                        Coupons.SHUPERSAL.getCompanyID(),
                        Coupons.SHUPERSAL.getCategory(),
                        Coupons.SHUPERSAL.getTitle(),
                        Coupons.SHUPERSAL.getDescription(),
                        Coupons.SHUPERSAL.getStartDate(),
                        Coupons.SHUPERSAL.getEndDate(),
                        Coupons.SHUPERSAL.getAmount(),
                        Coupons.SHUPERSAL.getPrice(),
                        Coupons.SHUPERSAL.getImage()
                ));
                System.out.println("SHUPERSAL Created a new coupon");
                break;

            case 6:
                companyFacade.addCouponByCompany(6L, new Coupon(
                        Coupons.OSHERAD.getCompanyID(),
                        Coupons.OSHERAD.getCategory(),
                        Coupons.OSHERAD.getTitle(),
                        Coupons.OSHERAD.getDescription(),
                        Coupons.OSHERAD.getStartDate(),
                        Coupons.OSHERAD.getEndDate(),
                        Coupons.OSHERAD.getAmount(),
                        Coupons.OSHERAD.getPrice(),
                        Coupons.OSHERAD.getImage()
                ));
                System.out.println("OSHERAD Created a new coupon");
                break;

            case 7:
                companyFacade.addCouponByCompany(7L, new Coupon(
                        Coupons.IVORY.getCompanyID(),
                        Coupons.IVORY.getCategory(),
                        Coupons.IVORY.getTitle(),
                        Coupons.IVORY.getDescription(),
                        Coupons.IVORY.getStartDate(),
                        Coupons.IVORY.getEndDate(),
                        Coupons.IVORY.getAmount(),
                        Coupons.IVORY.getPrice(),
                        Coupons.IVORY.getImage()
                ));
                System.out.println("IVORY Created a new coupon");
                break;

            case 8:
                companyFacade.addCouponByCompany(8L, new Coupon(
                        Coupons.KSP.getCompanyID(),
                        Coupons.KSP.getCategory(),
                        Coupons.KSP.getTitle(),
                        Coupons.KSP.getDescription(),
                        Coupons.KSP.getStartDate(),
                        Coupons.KSP.getEndDate(),
                        Coupons.KSP.getAmount(),
                        Coupons.KSP.getPrice(),
                        Coupons.KSP.getImage()
                ));
                System.out.println("KSP Created a new coupon");
                break;

            case 9:
                companyFacade.addCouponByCompany(9L, new Coupon(
                        Coupons.YELP.getCompanyID(),
                        Coupons.YELP.getCategory(),
                        Coupons.YELP.getTitle(),
                        Coupons.YELP.getDescription(),
                        Coupons.YELP.getStartDate(),
                        Coupons.YELP.getEndDate(),
                        Coupons.YELP.getAmount(),
                        Coupons.YELP.getPrice(),
                        Coupons.YELP.getImage()
                ));
                System.out.println("YELP Created a new coupon");
                break;

            case 10:
                companyFacade.addCouponByCompany(10L, new Coupon(
                        Coupons.TRIPADVISOR.getCompanyID(),
                        Coupons.TRIPADVISOR.getCategory(),
                        Coupons.TRIPADVISOR.getTitle(),
                        Coupons.TRIPADVISOR.getDescription(),
                        Coupons.TRIPADVISOR.getStartDate(),
                        Coupons.TRIPADVISOR.getEndDate(),
                        Coupons.TRIPADVISOR.getAmount(),
                        Coupons.TRIPADVISOR.getPrice(),
                        Coupons.TRIPADVISOR.getImage()
                ));
                System.out.println("TRIPADVISOR Created a new coupon");
                break;

            case 11:
                companyFacade.addCouponByCompany(11L, new Coupon(
                        Coupons.AIRBNB.getCompanyID(),
                        Coupons.AIRBNB.getCategory(),
                        Coupons.AIRBNB.getTitle(),
                        Coupons.AIRBNB.getDescription(),
                        Coupons.AIRBNB.getStartDate(),
                        Coupons.AIRBNB.getEndDate(),
                        Coupons.AIRBNB.getAmount(),
                        Coupons.AIRBNB.getPrice(),
                        Coupons.AIRBNB.getImage()
                ));
                System.out.println("OSHERAD Created a new coupon");
                break;

            case 12:
                companyFacade.addCouponByCompany(12L, new Coupon(
                        Coupons.BOOKING.getCompanyID(),
                        Coupons.BOOKING.getCategory(),
                        Coupons.BOOKING.getTitle(),
                        Coupons.BOOKING.getDescription(),
                        Coupons.BOOKING.getStartDate(),
                        Coupons.BOOKING.getEndDate(),
                        Coupons.BOOKING.getAmount(),
                        Coupons.BOOKING.getPrice(),
                        Coupons.BOOKING.getImage()
                ));
                System.out.println("BOOKING Created a new coupon");
                break;

            case 13:
                companyFacade.addCouponByCompany(13L, new Coupon(
                        Coupons.HACEREM.getCompanyID(),
                        Coupons.HACEREM.getCategory(),
                        Coupons.HACEREM.getTitle(),
                        Coupons.HACEREM.getDescription(),
                        Coupons.HACEREM.getStartDate(),
                        Coupons.HACEREM.getEndDate(),
                        Coupons.HACEREM.getAmount(),
                        Coupons.HACEREM.getPrice(),
                        Coupons.HACEREM.getImage()
                ));
                System.out.println("HACEREM Created a new coupon");
                break;

            case 14:
                companyFacade.addCouponByCompany(14L, new Coupon(
                        Coupons.COCACOLA.getCompanyID(),
                        Coupons.COCACOLA.getCategory(),
                        Coupons.COCACOLA.getTitle(),
                        Coupons.COCACOLA.getDescription(),
                        Coupons.COCACOLA.getStartDate(),
                        Coupons.COCACOLA.getEndDate(),
                        Coupons.COCACOLA.getAmount(),
                        Coupons.COCACOLA.getPrice(),
                        Coupons.COCACOLA.getImage()
                ));
                System.out.println("COCA-COLA Created a new coupon");

            case 15:
                companyFacade.addCouponByCompany(15L, new Coupon(
                        Coupons.ADIDAS.getCompanyID(),
                        Coupons.ADIDAS.getCategory(),
                        Coupons.ADIDAS.getTitle(),
                        Coupons.ADIDAS.getDescription(),
                        Coupons.ADIDAS.getStartDate(),
                        Coupons.ADIDAS.getEndDate(),
                        Coupons.ADIDAS.getAmount(),
                        Coupons.ADIDAS.getPrice(),
                        Coupons.ADIDAS.getImage()
                ));
                System.out.println("ADIDAS Created a new coupon");
                break;

            case 16:
                companyFacade.addCouponByCompany(16L, new Coupon(
                        Coupons.CASTRO.getCompanyID(),
                        Coupons.CASTRO.getCategory(),
                        Coupons.CASTRO.getTitle(),
                        Coupons.CASTRO.getDescription(),
                        Coupons.CASTRO.getStartDate(),
                        Coupons.CASTRO.getEndDate(),
                        Coupons.CASTRO.getAmount(),
                        Coupons.CASTRO.getPrice(),
                        Coupons.CASTRO.getImage()
                ));
                System.out.println("CASTRO Created a new coupon");
                break;

            case 17:
                break;

        }

        //--->>>>||3. Print viewing menu for company:
        System.out.println("if you'll liked to view your coupons list\n" +
                " please select between the following options: \n" +
                "1 - view by company id\n" +
                "2 - view by Catagory\n" +
                "3 - view by Price\n" +
                "4 - View your company details\n" +
                "5 - Exit this menu"

        );
        companyChoose = companyScanner.nextInt();
        long companyID = -1;
        double companyPrice = -1.00;

        //--->>>>||4. Switch case for viewing company coupons:
        switch (companyChoose) {

            //--->>>>||* view company coupons by companyID:
            case 1:
                System.out.println("please enter your company id to view all your coupons list: ");
                System.out.println(companyFacade.getCompanyCouponsByCompanyID(companyScanner.nextLong()));
                break;

            //--->>>>||* view company coupons by companyID & Category:
            case 2:
                System.out.println("please enter your company id : ");
                companyID = companyScanner.nextLong();

                //--->>>>||* print Coupon Category Types:
                System.out.println("Please enter a category from the following option: " +
                        "FOOD,\n" +
                        "ELECTRICITY\n" +
                        "RESTAURANT\n" +
                        "JUNKFOOD\n" +
                        "VACATION\n" +
                        "BEVERAGE\n" +
                        "CLOTHING\n" +
                        "SHOES\n" +
                        "Type here: "
                );
                Category typedCategory = Category.valueOf(companyScanner.next());
                System.out.println(companyFacade.getCompanyCouponsByCategory(companyID, typedCategory));
                break;

            //--->>>>||* view company coupons by companyID & MaxPrice:
            case 3:
                System.out.println("please enter your company id : ");
                companyID = companyScanner.nextLong();
                System.out.println("please enter max price in order to view your coupons list by : ");
                companyPrice = companyScanner.nextDouble();
                System.out.println(companyFacade.getCompanyCouponsByPrice(companyID, companyPrice));
                break;

            //--->>>>||* view company Details:
            case 4:
                System.out.println("please enter your company id : ");
                companyID = scanner.nextLong();
                System.out.println(companyFacade.getCompanyDetails(companyID));
                break;
            case 5:
                break;
        }

        //--->>>>||* Company delete/Update menu
        System.out.println("Would you'll liked to delete or update one of your coupon?\n" +
                "1 - update company coupon\n" +
                "2 - Delete company coupon\n" +
                "3 - Exit this menu\n" +
                ""
        );
        companyChoose = companyScanner.nextInt();
        long couponid = -1;
        Category updateCategory = null;
        String updateTitle = null;
        String updateDescription = null;
        java.sql.Date updateStartDate = null;
        Date updateEndDate = null;
        int updateAmount = -1;
        double updatePrice = -1.00;
        String updateImage = null;


        switch (companyChoose) {

            case 1:
                System.out.println("Please enter your company id: ");
                companyID = companyScanner.nextLong();
                System.out.println("Please enter coupon updated category: ");
                updateCategory = Category.valueOf(companyScanner.next());
                System.out.println("Please enter coupon updated title: ");
                updateTitle = companyScanner.next();
                System.out.println("Please enter coupon updated description: ");
                updateTitle = companyScanner.next();
                System.out.println("Please enter coupon updated Start Date: ");
                updateStartDate = java.sql.Date.valueOf(companyScanner.next());
                System.out.println("Please enter coupon updated End Date: ");
                updateEndDate = java.sql.Date.valueOf(companyScanner.next());
                System.out.println("Please enter coupon updated Amount: ");
                updateAmount = companyScanner.nextInt();
                System.out.println("Please enter coupon updated Price: ");
                updatePrice = companyScanner.nextDouble();
                System.out.println("Please enter coupon updated Image: ");
                updateImage = companyScanner.next();

                try {
                    companyFacade.updateCouponByCompany(new Coupon(
                                    companyID, updateCategory, updateTitle,
                                    updateDescription, updateStartDate, updateEndDate,
                                    updateAmount, updatePrice, updateImage)
                            , companyID);
                } catch (Exception e) {
                    System.err.println("Error have been occurred : \n" +
                            e.getMessage());
                }
                break;

            case 2:
                System.out.println("please enter the coupon id to delete by ");
                couponid = companyScanner.nextLong();
                companyFacade.deleteCoupon(couponid);
                break;

            case 3:
                break;
        }
    }
    //***************************************************************************************//

    private void customerMenu() {
        Scanner customerScanner = new Scanner(System.in);
        int customerChoice = -1;
        long customerID = -1;
        long couponID = -1;


        System.out.println("Hi Bro/Gal Welcome to our coupon buying program!\n" +
                "We have couples of options for you\n" +
                "Please choose your favourite one!\n" +
                "1 - Purchase a new coupon\n" +
                "2 - view my coupons list \n" +
                "3 - view my details \n" +
                "4 - Exit\n"
        );

        customerChoice = customerScanner.nextInt();
        switch (customerChoice) {
            case 1:
                System.out.println("Please enter the coupon ID of the coupon that you'd like to buy : ");
                couponID = customerScanner.nextLong();
                System.out.println("Please enter your Customer - ID that we can identified that its actually you : ");
                customerID = customerScanner.nextLong();
                try {
                    customerFacade.purchaseCoupon(couponID, customerID);
                } catch (Exception e) {
                    System.err.println("Error have been occurred : \n" +
                            e.getMessage());
                }
                break;

            case 2:
                System.out.println("you have multiple options to view your coupons list: \n" +
                        "1 - By Customer - ID \n" +
                        "2 - By Category \n" +
                        "3 - By Price - ID \n" +
                        "4 - Exit \n" +
                        " "
                );
                customerChoice = customerScanner.nextInt();
                long viewCustomerID = -1;
                Category customerViewCategory = null;
                double viewCouponPrice = -1.00;

                switch (customerChoice) {

                    case 1:
                        System.out.println("Please enter your customer-ID");
                        viewCustomerID = customerScanner.nextLong();
                        try {
                            System.out.println(customerFacade.getCustomerCoupons(viewCustomerID).toString());
                        } catch (Exception e) {
                            System.err.println("Error have been occurred : \n" +
                                    e.getMessage());
                        }
                        break;

                    case 2:
                        System.out.println("Please enter your customer-ID:");
                        viewCustomerID = customerScanner.nextLong();
                        System.out.println("Please enter a category from the following option: " +
                                "FOOD,\n" +
                                "ELECTRICITY\n" +
                                "RESTAURANT\n" +
                                "JUNKFOOD\n" +
                                "VACATION\n" +
                                "BEVERAGE\n" +
                                "CLOTHING\n" +
                                "SHOES\n" +
                                "Type here: "
                        );
                        customerViewCategory = Category.valueOf(customerScanner.next());

                        try {
                            System.out.println(customerFacade.getCustomerCouponsByCategory(viewCustomerID, customerViewCategory).toString());
                        } catch (Exception e) {
                            System.err.println("Error have been occurred : \n" +
                                    e.getMessage());
                        }
                        break;

                    case 3:
                        System.out.println("Please enter your customer-ID:");
                        viewCustomerID = customerScanner.nextLong();
                        System.out.println("Please enter coupon max price to view by: ");
                        viewCouponPrice = customerScanner.nextDouble();
                        try {
                            System.out.println(customerFacade.getCustomerCouponsByPrice(viewCustomerID, viewCouponPrice).toString());
                        } catch (Exception e) {
                            System.err.println("Error have been occurred : \n" +
                                    e.getMessage());
                        }
                        break;
                    case 4:
                        break;

                }

                break;

            case 3:
                System.out.println("Please enter your Customer ID : ");
                customerID = customerScanner.nextLong();
                try {
                    customerFacade.getCustomerDetails(customerID);
                } catch (Exception e) {
                    System.err.println("Error have been occurred : \n" +
                            e.getMessage());
                }
                break;

            case 4:
                System.out.println(" (: Thank U (: ");
                break;

        }
    }

    //-------------------------------Database Methods Tests-------------------------------//
    //-------------------------------Delete All coupon-system tables -------------------------------//
    public void deleteAllTables() {
        try {
            databaseInitializer.dropAllTables();
        } catch (DatabaseException e) {
            System.err.println(e.getMessage());
        }
    }

    //-------------------------------Create All coupon-system tables -------------------------------//
    public void createAllTables() {
        try {
            databaseInitializer.createAllTables();
        } catch (Exception e) {
            System.err.println("Error have been occurred : \n" +
                    e.getMessage());
        }
    }

}
