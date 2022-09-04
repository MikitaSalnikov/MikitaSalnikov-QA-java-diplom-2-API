import api.client.CustomerClient;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.UserRegistrated;

import static org.hamcrest.CoreMatchers.is;

public class LoginCustomerTest {
    private String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
        CustomerClient customer = new CustomerClient();
        Response ok = customer.createCustomer("correctRegistration.json");
        accessToken = ok.as(UserRegistrated.class).getAccessToken();
    }
    @After
    public void tearDown(){
        CustomerClient customer = new CustomerClient();
        customer.deleteCustomer(accessToken);
    }

    @Test
    public void checkLogin(){
        CustomerClient customer = new CustomerClient();
        Response login = customer.loginCustomer("correctAuth.json");
        login.then().assertThat().statusCode(200).and().body("success", is(true));
    }
    @Test
    public void noLogin(){
        CustomerClient customer = new CustomerClient();
        Response noLogin = customer.loginCustomer("wrongLoginAuth.json");
        noLogin.then().assertThat().statusCode(401).and().body("success", is(false)).body("message",is("email or password are incorrect"));
    }
    @Test
    public void noPassword(){
        CustomerClient customer = new CustomerClient();
        Response noLogin = customer.loginCustomer("wrongPasswordAuth.json");
        noLogin.then().assertThat().statusCode(401).and().body("success", is(false)).body("message",is("email or password are incorrect"));
    }
}
