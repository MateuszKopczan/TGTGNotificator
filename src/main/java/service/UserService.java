package service;

import utils.JsonUtils;
import okhttp3.*;
import request.ListOfferRequest;
import response.ListOfferResponse;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserService extends BaseService {

    private final OkHttpClient client = super.getClient();
    private final String ITEMS_ENDPOINT = "item/v7/";


    public ListOfferResponse getActiveOffers() throws IOException {
        ListOfferRequest listOfferRequest = getListOfferRequest();

        Request request = super.getRequest(ITEMS_ENDPOINT,
                                            listOfferRequest,
                                            System.getProperty("accessToken"));

        Response response = client.newCall(request).execute();

        ListOfferResponse listOfferResponse = null;
        if(response.code() == 200)
            listOfferResponse = JsonUtils.getListOfferResponse(Objects.requireNonNull(response.body()).string());
        else {
            System.err.println("[ERROR] Undefined error");
            System.exit(-1);
        }

        return filterActiveOffers(listOfferResponse);
    }

    private ListOfferRequest getListOfferRequest(){
        return ListOfferRequest.builder()
                .userId(System.getProperty("userId"))
                .origin(ListOfferRequest.Origin.builder()
                        .latitude(System.getProperty("latitude"))
                        .longitude(System.getProperty("longitude"))
                        .build())
                .radius(System.getProperty("radius"))
                .pageSize("400")
                .page("1")
                .favoritesOnly(false)
                .build();
    }

    private ListOfferResponse filterActiveOffers(ListOfferResponse response){
        ListOfferResponse activeOffers = new ListOfferResponse();
        if (response == null) return activeOffers;
        activeOffers.setItems(
                response.getItems().stream()
                        .filter(offer -> offer.getItemsAvailable() > 0)
                        .collect(Collectors.toList())
        );
       return activeOffers;
    }
}
