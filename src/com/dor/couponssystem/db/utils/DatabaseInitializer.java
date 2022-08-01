package com.dor.couponssystem.db.utils;

import com.dor.couponssystem.db.connection.ConnectionPool;
import com.dor.couponssystem.enums.DBEntityTypes;
import com.dor.couponssystem.enums.DBNames;
import com.dor.couponssystem.enums.DBOperations;
import com.dor.couponssystem.exceptions.DBConnectionException;
import com.dor.couponssystem.exceptions.DatabaseException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DatabaseInitializer {
    public static DatabaseInitializer instance = new DatabaseInitializer();
    private static Connection connection;

    static {
        try {
            connection = ConnectionPool.getInstance().getConnection();
        } catch (Exception e) {
            try {
                throw new DBConnectionException(DBNames.COUPONS_SYSTEM, DBEntityTypes.DATABASE);
            } catch (DBConnectionException ex) {
            }
        }
    }

    public void createAllTables() throws DatabaseException {
        createCompaniesTable();
        createCategoriesTable();
        createCouponTable();
        createCustomerTable();
        createCustomersVsCouponsTable();
    }

    public void dropAllTables() throws DatabaseException {
        dropCustomersVSCouponsTable();
        dropCouponsTable();
        dropCustomersTable();
        dropCompaniesTable();
        dropCategoriesTable();
        System.out.println("Deleted all Tables in coupon-system scheme");

    }
        //------------------------------------------------Creating tables METHODS:------------------------------------------------//
//------------------------------------------------Create CATEGORIES table :------------------------------------------------//
        public void createCategoriesTable () throws DatabaseException {
            String sqlStatement = "CREATE TABLE IF NOT EXISTS `categories` (\n" +
                    "  `id` bigint NOT NULL,\n" +
                    "  `name` varchar(45) NOT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  UNIQUE KEY `name_UNIQUE` (`name`),\n" +
                    "  UNIQUE KEY `id_UNIQUE` (`id`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DatabaseException(DBOperations.CREATE, DBNames.CATEGORIES, DBEntityTypes.TABLE);
            }
        }

        //------------------------------------------------Create COMPANIES table :------------------------------------------------//
        public void createCompaniesTable () throws DatabaseException {
            String sqlStatement = "CREATE TABLE IF NOT EXISTS `coupons_system`.`companies` (\n" +
                    "  `id` bigint NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` varchar(45) NOT NULL,\n" +
                    "  `email` varchar(45) NOT NULL,\n" +
                    "  `password` bigint NOT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  UNIQUE KEY `id_UNIQUE` (`id`),\n" +
                    "  UNIQUE KEY `name_UNIQUE` (`name`),\n" +
                    "  UNIQUE KEY `email_UNIQUE` (`email`)\n" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
            try {

                PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                throw new DatabaseException(DBOperations.CREATE, DBNames.COMPANIES, DBEntityTypes.TABLE);
            }
        }

        //------------------------------------------------Create CUSTOMER table :------------------------------------------------//
        public void createCustomerTable () throws DatabaseException {
            String sqlStatement = "CREATE TABLE IF NOT EXISTS `customers` (\n" +
                    "  `id` bigint NOT NULL AUTO_INCREMENT,\n" +
                    "  `first_name` varchar(45) NOT NULL,\n" +
                    "  `last_name` varchar(45) NOT NULL,\n" +
                    "  `email` varchar(45) NOT NULL,\n" +
                    "  `password` int NOT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  UNIQUE KEY `email_UNIQUE` (`email`)\n" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DatabaseException(DBOperations.CREATE, DBNames.CUSTOMERS, DBEntityTypes.TABLE);
            }
        }

        //------------------------------------------------Create COUPONS table :------------------------------------------------//
        public void createCouponTable () throws DatabaseException {
            String sqlStatement = "CREATE TABLE IF NOT EXISTS `coupons` (\n" +
                    "  `id` bigint NOT NULL AUTO_INCREMENT,\n" +
                    "  `company_id` bigint NOT NULL,\n" +
                    "  `category` varchar(45) NOT NULL,\n" +
                    "  `title` varchar(45) NOT NULL,\n" +
                    "  `description` varchar(45) DEFAULT NULL,\n" +
                    "  `start_date` date NOT NULL,\n" +
                    "  `end_date` date NOT NULL,\n" +
                    "  `amount` int NOT NULL,\n" +
                    "  `price` double NOT NULL,\n" +
                    "  `image` varchar(120) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  UNIQUE KEY `id_UNIQUE` (`id`),\n" +
                    "  UNIQUE KEY `company_id_UNIQUE` (`company_id`),\n" +
                    "  KEY `company_id_idx` (`company_id`),\n" +
                    "  CONSTRAINT `company_id` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DatabaseException(DBOperations.CREATE, DBNames.COUPONS, DBEntityTypes.TABLE);
            }
        }

        //------------------------------------------------Create CUSTOMERS_VS_COUPONS table :------------------------------------------------//
        public void createCustomersVsCouponsTable () throws DatabaseException {

            String sqlStatement = "CREATE TABLE IF NOT EXISTS `customers_vs_coupons` (\n" +
                    "  `customer_id` bigint NOT NULL,\n" +
                    "  `coupon_id` bigint NOT NULL,\n" +
                    "  KEY `coupon_id_idx` (`coupon_id`),\n" +
                    "  CONSTRAINT `coupon_id` FOREIGN KEY (`coupon_id`) REFERENCES `coupons` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                    "  CONSTRAINT `customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                throw new DatabaseException(DBOperations.CREATE, DBNames.CUSTOMERS_VS_COUPONS, DBEntityTypes.TABLE);
            }
        }
        //------------------------------------------------Create CUSTOMERS_VS_COUPONS table :------------------------------------------------//

        public void dropCategoriesTable () throws DatabaseException {
            String sqlStatement = "DROP TABLE `categories`";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DatabaseException(DBOperations.DROP, DBNames.CATEGORIES, DBEntityTypes.TABLE);

            }
        }

        public void dropCompaniesTable () throws DatabaseException {
            String sqlStatement = "DROP TABLE `coupons_system`.`companies`";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                throw new DatabaseException(DBOperations.DROP, DBNames.COMPANIES, DBEntityTypes.TABLE);
            }
        }

        public void dropCouponsTable () throws DatabaseException {
            String sqlStatement = "DROP TABLE `coupons_system`.`coupons`";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                throw new DatabaseException(DBOperations.DROP, DBNames.COMPANIES, DBEntityTypes.TABLE);
            }
        }

        public void dropCustomersVSCouponsTable () throws DatabaseException {
            String sqlStatement = "DROP TABLE `coupons_system`.`customers_vs_coupons`";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                throw new DatabaseException(DBOperations.DROP, DBNames.COMPANIES, DBEntityTypes.TABLE);
            }
        }

        public void dropCustomersTable () throws DatabaseException {
            String sqlStatement = "DROP TABLE `coupons_system`.`customers`";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                throw new DatabaseException(DBOperations.DROP, DBNames.COMPANIES, DBEntityTypes.TABLE);
            }
        }
    }
