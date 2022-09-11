import api.client.CustomerClient;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.UserRegistrated;

import static org.hamcrest.CoreMatchers.is;

public class CreateCustomerTest {
    private String accessToken;
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
    }
    @After
    public void tearDown() {
        CustomerClient customer = new CustomerClient();
        try {
            customer.deleteCustomer(accessToken);
        } catch (Exception e) {
        }
    }
    @Test
    public void checkCreateCustomer() {
        CustomerClient create = new CustomerClient();
        Response ok = create.createCustomer("correctRegistration.json");
        accessToken = ok.as(UserRegistrated.class).getAccessToken();
        ok.then().assertThat().statusCode(200).and().body("success", is(true));
    }
    @Test
    public void customerExists() {
        CustomerClient create = new CustomerClient();
        Response duplicate = create.createCustomer("correctRegistration.json");
        accessToken = duplicate.as(UserRegistrated.class).getAccessToken();
        duplicate = create.createCustomer("correctRegistration.json");
        try {
            accessToken = duplicate.as(UserRegistrated.class).getAccessToken();
        } catch (Exception e) {
        }
        duplicate.then().assertThat().statusCode(403).and().body("success", is(false)).body("message", is("User already exists"));
    }
    @Test
    public void noEmail() {
        CustomerClient create = new CustomerClient();
        Response noEmail = create.createCustomer("noEmailRegister.json");
        try {
            accessToken = noEmail.as(UserRegistrated.class).getAccessToken();
        } catch (Exception e) {
        }
        noEmail.then().assertThat().statusCode(403).and().body("success", is(false)).body("message", is("Email, password and name are required fields"));
    }
    @Test
    public void noPassword() {
        CustomerClient create = new CustomerClient();
        Response noPassword = create.createCustomer("noPasswordRegister.json");
        try {
            accessToken = noPassword.as(UserRegistrated.class).getAccessToken();
        } catch (Exception e) {
        }
        noPassword.then().assertThat().statusCode(403).and().body("success", is(false)).body("message", is("Email, password and name are required fields"));
    }
    @Test
    public void noName() {
        CustomerClient create = new CustomerClient();
        Response noName = create.createCustomer("noNameRegister.json");
        try {
            accessToken = noName.as(UserRegistrated.class).getAccessToken();
        } catch (Exception e) {
        }
        noName.then().assertThat().statusCode(403).and().body("success", is(false)).body("message", is("Email, password and name are required fields"));
    }
}
