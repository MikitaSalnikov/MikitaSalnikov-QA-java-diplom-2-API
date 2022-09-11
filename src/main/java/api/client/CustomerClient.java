package api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class CustomerClient {
    @Step("Регистрация пользователя")
    public Response createCustomer(String json){
        RequestsUtil create = new RequestsUtil();
        return create.postReq(json,"/api/auth/register");
    }
    @Step("Авторизация пользователя")
    public Response loginCustomer(String json){
        RequestsUtil login = new RequestsUtil();
        return login.postReq(json,"api/auth/login");
    }
    @Step("Получение данных пользователя")
    public Response getCustomer(String accessToken){
        RequestsUtil get = new RequestsUtil();
        return get.getReq(accessToken,"api/auth/user");
    }
    @Step("Редактирование данных пользователя")
    public Response editCustomer(String accessToken, String json){
        RequestsUtil patch = new RequestsUtil();
        return patch.patchReq(accessToken, json,"api/auth/user");
    }
    @Step("Удаление пользователя")
    public Response deleteCustomer(String accessToken){
        RequestsUtil delete = new RequestsUtil();
        return delete.deleteReq(accessToken, "/api/auth/user");
    }
}
