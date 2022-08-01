package com.dor.couponssystem.enums.mock;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public enum Customers {
    DOR("dor","eden","dor@gmail.com","dor123"),
    IDAN("idan","kakon","idan@gmail.com","idan123"),
    OR("or","chen","or@gmail.com","or123"),
    DANIEL("daniel","perez","daniel@gmail.com","daniel123"),
    OMER("omer","tito","omer@gmail.com","omer123"),
    DAVID("david","bar-yosef","david@gmail.com","david123"),
    MEITAL("meital","mor","meital@gmail.com","meital123"),
    EMIL("emilio","bugadev","emilio@gmail.com","emilio123"),
    NITAY("nitay","caspi","nitay@gmail.com","nitay123"),
    LIOR("lior","cohen","lior@gmial.com","lior123"),
    TANYA("tanya","kuper","tanya@gmail.com","tanya123");

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public static List<Customers> getList() {
        return (List<Customers>) Arrays.asList(Customers.values());
    }
}
