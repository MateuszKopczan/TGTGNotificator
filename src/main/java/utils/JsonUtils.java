package utils;

import request.Request;
import response.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtils {

    public static String getJson(Request request){
        ObjectMapper objectMapper = new ObjectMapper();

        try{
            return objectMapper.writeValueAsString(request);
        } catch (IOException ex){
            return "";
        }
    }

    public static RegisterResponse getRegisterResponse(String jsonResponse){
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            return objectMapper.readValue(jsonResponse, RegisterResponse.class);
        } catch (IOException e){
            e.printStackTrace();
            return new RegisterResponse();
        }
    }

    public static ErrorsResponse getErrorsResponse(String jsonResponse){
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            return objectMapper.readValue(jsonResponse, ErrorsResponse.class);
        } catch (IOException e){
            e.printStackTrace();
            return new ErrorsResponse();
        }
    }

    public static LoginResponse getLoginResponse(String jsonResponse){
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            return objectMapper.readValue(jsonResponse, LoginResponse.class);
        } catch (IOException e){
            e.printStackTrace();
            return new LoginResponse();
        }
    }

    public static PollingResponse getPollingResponse(String jsonResponse){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonResponse, PollingResponse.class);
        } catch (IOException e){
            e.printStackTrace();
            return new PollingResponse();
        }
    }

    public static ListOfferResponse getListOfferResponse(String jsonResponse){
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            return objectMapper.readValue(jsonResponse, ListOfferResponse.class);
        } catch (IOException e){
            e.printStackTrace();
            return new ListOfferResponse();
        }
    }

}
