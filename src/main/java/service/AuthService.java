package service;

import properties.Properties;
import utils.JsonUtils;
import exceptions.RegisterException;
import okhttp3.*;
import request.LoginRequest;
import request.RefreshTokenRequest;
import request.RegisterRequest;
import response.ErrorsResponse;
import response.LoginResponse;
import response.PollingResponse;
import response.RegisterResponse;
import validation.InputValidator;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class AuthService extends BaseService{

    private final Scanner scanner = new Scanner(System.in);
    private final OkHttpClient client = super.getClient();
    private final String SIGN_UP_BY_EMAIL_ENDPOINT = "auth/v3/signUpByEmail";
    private final String AUTH_BY_EMAIL_ENDPOINT = "auth/v3/authByEmail/";
    private final String REFRESH_TOKEN_ENDPOINT = "auth/v3/token/refresh";

    public void register() throws IOException, RegisterException {
        RegisterRequest registerRequest = getRegisterRequest();

        Request request = super.getRequest(SIGN_UP_BY_EMAIL_ENDPOINT, registerRequest);

        Response response = client.newCall(request).execute();
        RegisterResponse registerResponse = null;

        if(response.code() == 200) {
            registerResponse = JsonUtils.getRegisterResponse(Objects.requireNonNull(response.body()).string());
            System.out.println("Successful registration! Now you can login.");
            Properties.saveConfigProperty("userId", registerResponse.getLoginResponse().getStartupData().getUser().getUserId());
            Properties.saveConfigProperty("email", registerResponse.getLoginResponse().getStartupData().getUser().getEmail());
            Properties.saveConfigProperty("accessToken", registerResponse.getLoginResponse().getAccessToken());
            Properties.saveConfigProperty("refreshToken", registerResponse.getLoginResponse().getRefreshToken());


//            System.setProperty("userId", registerResponse.getLoginResponse().getStartupData().getUser().getUserId());
//            System.setProperty("email", registerResponse.getLoginResponse().getStartupData().getUser().getEmail());
//            System.setProperty("accessToken", registerResponse.getLoginResponse().getAccessToken());
//            System.setProperty("refreshToken", registerResponse.getLoginResponse().getRefreshToken());
        }
        else {
            ErrorsResponse errorsResponse = JsonUtils.getErrorsResponse(Objects.requireNonNull(response.body()).string());
            throw new RegisterException(errorsResponse.getErrors().toString());
        }
    }

    public void login() throws IOException {
        if(System.getProperty("accessToken") == null || System.getProperty("refreshToken") == null ||
                System.getProperty("userId") == null || System.getProperty("email") == null){
            System.err.println("[ERROR] No login details. Register or fill user.properties file");
            System.exit(-1);
        }
        LoginRequest loginRequest = getLoginRequest(System.getProperty("email"));

        Request request = super.getRequest(AUTH_BY_EMAIL_ENDPOINT, loginRequest, System.getProperty("accessToken"));

        Response response = client.newCall(request).execute();
        LoginResponse loginResponse = null;

        if(response.code() == 200){
            loginResponse = JsonUtils.getLoginResponse(Objects.requireNonNull(response.body()).string());
            if(loginResponse.getState().equals("WAIT"))
                System.out.println("[INFO] Successful login.");
            else if(loginResponse.getState().equals("TERMS")){
                System.err.println("[ERROR] Invalid email. Register or complete the configuration files");
                System.exit(-1);
            }
            else{
                System.err.println("[ERROR] Undefined error");
                System.exit(-1);
            }
        }
        else if(response.code() == 401)
            refreshToken(System.getProperty("refreshToken"));
        else if(response.code() == 400){
            System.err.println("[ERROR] No login details. Register or fill user.properties file");
            System.exit(0);
        }
        else{
            System.err.println("[ERROR] Undefined error");
            System.exit(-1);
        }

    }

    public void refreshToken(String refreshToken) throws IOException {
        RefreshTokenRequest refreshTokenRequest = getRefreshTokenRequest(refreshToken);

        Request request = super.getRequest(REFRESH_TOKEN_ENDPOINT, refreshTokenRequest);

        Response response = client.newCall(request).execute();
        PollingResponse refreshTokenResponse = null;

        if(response.code() == 200){
            refreshTokenResponse = JsonUtils.getPollingResponse(response.body().string());
            Properties.saveConfigProperty("accessToken", refreshTokenResponse.getAccessToken());
            Properties.saveConfigProperty("refreshToken", refreshTokenResponse.getRefreshToken());
//            System.setProperty("accessToken", refreshTokenResponse.getAccessToken());
//            System.setProperty("refreshToken", refreshTokenResponse.getRefreshToken());
        }
        else{
            System.err.println("[ERROR] Your access or refresh token is invalid. Try again later.");
            System.exit(0);
        }
    }

    private RegisterRequest getRegisterRequest(){
        String email = "", name = "", countryId = "";
        while(true){
            System.out.println("Input email: ");
            email = scanner.nextLine();
            if(!InputValidator.isValidEmail(email))
                System.err.println("[ERROR] Invalid email. Try again.");
            else
                break;
        }

        System.out.println("Input name: ");
        name = scanner.nextLine();

        while(true) {
            System.out.println("Input country code in ISO standard (e.g. GB - Great Britain): ");
            countryId = scanner.nextLine();
            if(!InputValidator.isValidISOCountry(countryId))
                System.err.println("[ERROR] Invalid country code. Try again.");
            else
                break;
        }

        return RegisterRequest.builder()
                .countryId(countryId)
                .deviceType("ANDROID")
                .email(email)
                .name(name)
                .newsletter(false)
                .pushNotification(true)
                .build();
    }

    private LoginRequest getLoginRequest(String email){
        return LoginRequest.builder()
                .deviceType("ANDROID")
                .email(email)
                .build();
    }

    private RefreshTokenRequest getRefreshTokenRequest(String refreshToken){
        return RefreshTokenRequest.builder()
                .refreshToken(refreshToken)
                .build();
    }
}
