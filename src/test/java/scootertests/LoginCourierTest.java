package scootertests;

import apirequest.CourierRequest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import Courier.Courier;
import static org.apache.http.HttpStatus.*;

import static org.hamcrest.Matchers.*;

public class LoginCourierTest{
    CourierRequest courierRequest = new CourierRequest();
    Courier courier = new Courier ("BikerAlex","00001");

    @Before
    public void setUp(){
        courierRequest.setUp();
    }
    @Test
    @DisplayName("Авторизация курьера в системе")
    @Description("Проверка авторизации с корректными логином и паролем")
    public void checkLoginCourier(){
        courierRequest.setCourier(courier);
        courierRequest.createCourier();
        courierRequest.loginCourier()
                .then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Авторизация без логина")
    @Description("Проверка блока на авторизацию без указания логина")
    public void checkLoginWithoutLogin(){
        courierRequest.setCourier(new Courier("","00001"));
        courierRequest.loginCourier()
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Авторизация без пароля")
    @Description("Проверка блока на авторизацию без указания пароля")
    public void checkLoginWithoutPassword(){
        courierRequest.setCourier(new Courier("BikerAlex",""));
        courierRequest.loginCourier()
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Авторизация c незарегистрированным пользователем")
    @Description("Проверка авторизации с незарегестрированным курьером")
    public void checkLoginNonExisting(){
        courierRequest.setCourier(courier);
        courierRequest.loginCourier()
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Авторизация с указанием неверного логина")
    @Description("Проверка авторизации с вводом неверного логина")
    public void checkLoginIncorrectLogin() {
        courierRequest.setCourier(courier);
        courierRequest.createCourier();
        courierRequest.setCourier(new Courier("FatherBikerAlex","00001"));
        courierRequest.loginCourier()
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
        courierRequest.setCourier(courier);
    }

    @Test
    @DisplayName("Авторизация с указанием неверного пароля")
    @Description("Проверка авторизации с вводом неверного пароля")
    public void checkLoginIncorrectPassword() {
        courierRequest.setCourier(courier);
        courierRequest.createCourier();
        courierRequest.setCourier(new Courier("BikerAlex","Qwerty123"));
        courierRequest.loginCourier()
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
        courierRequest.setCourier(courier);
    }
    @After
    public void cleanData(){
        courierRequest.deleteCourier();
    }

}