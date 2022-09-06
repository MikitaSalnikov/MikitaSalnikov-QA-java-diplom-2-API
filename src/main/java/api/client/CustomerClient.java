package api.client;

import io.restassured.response.Response;

public class CustomerClient {
    public Response createCustomer(String json){
        RequestsUtil create = new RequestsUtil();
        return create.postReq(json,"/api/auth/register");
    }
    public Response loginCustomer(String json){
        RequestsUtil login = new RequestsUtil();
        return login.postReq(json,"api/auth/login");
    }
    public Response getCustomer(String accessToken){
        RequestsUtil get = new RequestsUtil();
        return get.getReq(accessToken,"api/auth/user");
    }
    public Response editCustomer(String accessToken, String json){
        RequestsUtil patch = new RequestsUtil();
        return patch.patchReq(accessToken, json,"api/auth/user");
    }
    public Response deleteCustomer(String accessToken){
        RequestsUtil delete = new RequestsUtil();
        return delete.deleteReq(accessToken, "/api/auth/user");
    }
}
