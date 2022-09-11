import api.client.CustomerClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.UserRegistrated;

import static org.hamcrest.core.Is.is;

public class EditCustomerTest {
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
    @DisplayName("Редактирование имени пользователя")
    public void editName() {
        CustomerClient customer = new CustomerClient();
        Response editName = customer.editCustomer(accessToken, "editUserName.json");
        editName.then().assertThat().statusCode(200).and().body("user.name", is("Nikita"));
    }
    @Test
    @DisplayName("Редактирование email пользователя")
    public void editEmail() {
        CustomerClient customer = new CustomerClient();
        Response editName = customer.editCustomer(accessToken, "editUserEmail.json");
        editName.then().assertThat().statusCode(200).and().body("user.email", is("somenew@email.com"));
    }
    @Test
    @DisplayName("Редактирование пароля пользователя")
    public void editWOAuth() {
        CustomerClient customer = new CustomerClient();
        Response editName = customer.editCustomer("", "editUserEmail.json");
        editName.then().assertThat().statusCode(401).and().body("success", is(false)).and().body("message", is("You should be authorised"));
    }
}
