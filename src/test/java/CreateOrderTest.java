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

public class CreateOrderTest {
    private String accessToken;
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
        CustomerClient customer = new CustomerClient();
        Response ok = customer.createCustomer("correctRegistration.json");
        accessToken = ok.as(UserRegistrated.class).getAccessToken();
    }
    @After
    public void tearDown() {
        CustomerClient customer = new CustomerClient();
        customer.deleteCustomer(accessToken);
    }
    @Test
    @DisplayName("Оформление заказа авторизованным пользователем")
    public void makeOrderWithAuth() {
        OrderClient orderClient = new OrderClient();
        Response correctOrder = orderClient.createOrder(accessToken, "correctOrder.json");
        correctOrder.then().assertThat().statusCode(200).and().body("order.number", notNullValue()).and().body("success", is(true));
    }
    @Test
    @DisplayName("Оформление заказа неавторизованным пользователем")
    public void makeOrderWOAuth() {
        OrderClient orderClient = new OrderClient();
        Response correctOrder = orderClient.createOrder("correctOrder.json");
        correctOrder.then().assertThat().statusCode(200).and().body("order.number", notNullValue()).and().body("success", is(true));
    }
    @Test
    @DisplayName("Оформление заказ без указания начинки")
    public void makeOrderWOIngredients() {
        OrderClient orderClient = new OrderClient();
        Response correctOrder = orderClient.createOrder(accessToken, "orderWithoutIngredients.json");
        correctOrder.then().assertThat().statusCode(400).and().body("message", is("Ingredient ids must be provided")).and().body("success", is(false));
    }
    @Test
    @DisplayName("Оформление заказа с несуществующей начинкой")
    public void makeOrderWithWrongIngredients() {
        OrderClient orderClient = new OrderClient();
        Response correctOrder = orderClient.createOrder(accessToken, "orderWithWrongIngredients.json");
        correctOrder.then().assertThat().statusCode(500);
    }
}
