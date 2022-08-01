package com.dor.couponssystem.exceptions;

import com.dor.couponssystem.enums.DBEntityTypes;
import com.dor.couponssystem.enums.DBNames;
import com.dor.couponssystem.enums.DBOperations;

public class DatabaseException extends Exception {
        public DatabaseException(DBOperations dbOperation, DBNames dbName, DBEntityTypes dbEntityType) {
            super(String.format("Failed To %s %s %s",dbOperation.toString(),dbName.toString(),dbEntityType.toString()));
        }
    }

