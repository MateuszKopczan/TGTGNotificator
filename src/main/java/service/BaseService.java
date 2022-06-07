package service;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import utils.JsonUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class BaseService {

    private final OkHttpClient client;
    private final Headers headers;
    private final String BASE_URL = "https://apptoogoodtogo.com/api/";

    public BaseService(){
        headers = Headers.of(
                new HashMap<>() {{
                    put("Cookie", "datadome=XG4nyt0P_fjK3NHMyfR-Y-X1weijXHjYsP4g7g2LDRWXKeY_RsYG53elvJfE-7O5zUQTZDzHbMIxrhx_o8xfP7a26irKEIiuzLv4WGST9X3u8ImkTrAIkNq0PArYL.j; Path=/; Domain=apptoogoodtogo.com; secure");
                    put("Content-Type", "application/json");
                    put("Host", "apptoogoodtogo.com");
                    put("Connection", "keep-alive");
                    put("Accept", "*/*");
                    put("User-Agent", "Mozilla/5.0 (Linux; Android 11; Samsung SM-A025G)");
                    put("Accept-Language", "pl, en-UK");
                }}
        );
        client = new OkHttpClient();
    }

    public OkHttpClient getClient() {
        return client;
    }

    public Request getRequest(String endpoint, request.Request requestBody){
        String requestBodyJson = JsonUtils.getJson(requestBody);
        return new Request.Builder()
                .url(BASE_URL + endpoint)
                .headers(headers)
                .header("Content-Length", String.valueOf(requestBodyJson.length()))
                .post(RequestBody.create(requestBodyJson.getBytes(StandardCharsets.UTF_8)))
                .build();
    }

    public Request getRequest(String endpoint, request.Request requestBody, String accessToken){
        String requestBodyJson = JsonUtils.getJson(requestBody);
        return new Request.Builder()
                .url(BASE_URL + endpoint)
                .headers(headers)
                .header("Content-Length", String.valueOf(requestBodyJson.length()))
                .header("Authorization", "Bearer " + accessToken)
                .post(RequestBody.create(requestBodyJson.getBytes(StandardCharsets.UTF_8)))
                .build();

    }
}
