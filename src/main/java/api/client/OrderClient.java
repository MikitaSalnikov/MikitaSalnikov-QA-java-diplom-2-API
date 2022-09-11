package api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class OrderClient {
    @Step("Создание заказа неавторизованным пользователем")
    public Response createOrder(String json) {
        RequestsUtil create = new RequestsUtil();
        return create.postReq(json, "/api/orders");
    }
    @Step("Создание заказа авторизованным пользователем")
    public Response createOrder(String accessToken, String json) {
        RequestsUtil create = new RequestsUtil();
        return create.postReq(accessToken, json, "/api/orders");
    }
    @Step("Получение списка заказов пользователя")
    public Response getCustomersOrders(String accessToken) {
        RequestsUtil create = new RequestsUtil();
        return create.getReq(accessToken, "/api/orders");
    }
}
