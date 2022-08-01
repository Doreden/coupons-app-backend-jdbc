package com.dor.couponssystem.auth;

import com.dor.couponssystem.enums.ClientType;
import com.dor.couponssystem.exceptions.AuthenticationException;
import com.dor.couponssystem.exceptions.EntityCrudException;
import com.dor.couponssystem.facade.AdminFacade;
import com.dor.couponssystem.facade.ClientFacade;
import com.dor.couponssystem.facade.CompanyFacade;
import com.dor.couponssystem.facade.CustomerFacade;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginManager {
    public final CompanyFacade companyFacade = CompanyFacade.instance;
    public final CustomerFacade customerFacade = CustomerFacade.instance;
    private final AdminFacade adminFacade = AdminFacade.instance;
    public static final LoginManager instance = new LoginManager();

    public final List<String> authenticatedUser = new ArrayList<>();

    public ClientFacade login(final String email, final String password, final ClientType clientType) throws AuthenticationException, EntityCrudException {
        ClientFacade clientFacade = null;
        boolean isAuthenticated = false;


        switch (clientType) {

            case COMPANY:
                clientFacade = companyFacade;
                companyFacade.login(email, password);
                System.out.println("Company Success to logged in!");
                break;

            case CUSTOMER:
                clientFacade = customerFacade;
                customerFacade.login(email, password);
                System.out.println("Customer Success to logged in!");


                break;

            case ADMINISTRATOR:
                clientFacade = adminFacade;
                adminFacade.login(email, password);
                System.out.println("Admin Success to logged in!");

                break;

            default:
                throw new AuthenticationException(email);
        }
        isAuthenticated = clientFacade.login(email, password);
        if (isAuthenticated) {
            authenticatedUser.add(email);
            return clientFacade;
        }

        throw new AuthenticationException(email);
    }
}


