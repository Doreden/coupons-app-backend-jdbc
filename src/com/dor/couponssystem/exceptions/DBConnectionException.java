package com.dor.couponssystem.exceptions;

import com.dor.couponssystem.enums.DBEntityTypes;
import com.dor.couponssystem.enums.DBNames;

public class DBConnectionException extends Exception{

    public DBConnectionException(DBNames dbNames, DBEntityTypes dbEntityType) {
        super(String.format("Failed to Establish a connection with %s %s",dbNames,dbEntityType));
    }
}
