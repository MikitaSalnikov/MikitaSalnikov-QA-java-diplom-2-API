import api.client.CustomerClient;
import api.client.OrderClient;
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
    public void makeOrderWithAuth(){
        OrderClient orderClient = new OrderClient();
        Response correctOrder = orderClient.createOrder(accessToken, "correctOrder.json");
        correctOrder.then().assertThat().statusCode(200).and().body("order.number", notNullValue()).and().body("success", is(true));
    }
    @Test
    public void makeOrderWOAuth(){
        OrderClient orderClient = new OrderClient();
        Response correctOrder = orderClient.createOrder("correctOrder.json");
        correctOrder.then().assertThat().statusCode(200).and().body("order.number", notNullValue()).and().body("success", is(true));
    }
    @Test
    public void makeOrderWOIngredients(){
        OrderClient orderClient = new OrderClient();
        Response correctOrder = orderClient.createOrder(accessToken, "orderWithoutIngredients.json");
        correctOrder.then().assertThat().statusCode(400).and().body("message", is("Ingredient ids must be provided")).and().body("success", is(false));
    }
    @Test
    public void makeOrderWithWrongIngredients(){
        OrderClient orderClient = new OrderClient();
        Response correctOrder = orderClient.createOrder(accessToken, "orderWithWrongIngredients.json");
        correctOrder.then().assertThat().statusCode(500);
    }
}
