import api.client.CustomerClient;
import api.client.OrderClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.UserRegistrated;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrdersTest {
    private String accessToken;
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
        CustomerClient customer = new CustomerClient();
        Response ok = customer.createCustomer("correctRegistration.json");
        accessToken = ok.as(UserRegistrated.class).getAccessToken();
        OrderClient orderClient = new OrderClient();
        orderClient.createOrder(accessToken, "correctOrder.json");
        orderClient.createOrder(accessToken, "correctOrder.json");
    }
    @After
    public void tearDown() {
        CustomerClient customer = new CustomerClient();
        customer.deleteCustomer(accessToken);
    }
    @Test
    @DisplayName("Получение списка заказов без авторизации")
    public void getOrderWOAuth() {
        OrderClient orderClient = new OrderClient();
        Response orderList = orderClient.getCustomersOrders("");
        orderList.then().assertThat().statusCode(401).and().body("message", is("You should be authorised"));
    }
    @Test
    @DisplayName("Получение списка заказов авторизованного пользователя")
    public void getOrderAuth() {
        OrderClient orderClient = new OrderClient();
        Response orderList = orderClient.getCustomersOrders(accessToken);
        orderList.then().assertThat().statusCode(200).body("orders._id", notNullValue());
    }
}
