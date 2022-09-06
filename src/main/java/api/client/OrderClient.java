package api.client;

import io.restassured.response.Response;

public class OrderClient {
    public Response createOrder(String json) {
        RequestsUtil create = new RequestsUtil();
        return create.postReq(json, "/api/orders");
    }
    public Response createOrder(String accessToken, String json) {
        RequestsUtil create = new RequestsUtil();
        return create.postReq(accessToken, json, "/api/orders");
    }
    public Response getCustomersOrders(String accessToken) {
        RequestsUtil create = new RequestsUtil();
        return create.getReq(accessToken, "/api/orders");
    }
}
