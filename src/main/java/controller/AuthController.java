package controller;

//import properties.Properties;
import exceptions.RegisterException;
import service.AuthService;

import java.io.IOException;

public class AuthController {

    private final AuthService authService = new AuthService();


    public void register() throws IOException {
        try {
            authService.register();
        }catch (RegisterException e){
            System.err.println(e.getMessage());
        }
    }

    public void login() throws IOException {
        authService.login();
    }
}
