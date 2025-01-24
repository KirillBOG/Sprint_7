package scootertests;

import Courier.Courier;
import apirequest.CourierRequest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.apache.http.HttpStatus.*;

import static org.hamcrest.Matchers.*;

public class CreateCourierTest{

    Courier courier = new Courier("BikerAlex", "0000001", "Alex");
    CourierRequest courierRequest = new CourierRequest();
    @Before
    public void setUp(){
        courierRequest.setUp();
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Проверка успешного создания УЗ")
    public void checkCreateCourier(){
        courierRequest.setCourier(courier);
        courierRequest.createCourier()
                .then().assertThat().body("ok", is(true))
                .and()
                .statusCode(SC_CREATED);
    }

    // Баг, так как в Технической документации текст, при воспроизведении ошибки: "Этот логин уже используется", а система ожидает другой.
    @Test
    @DisplayName("Создание существующего курьера")
    @Description("Проверка запрета на создание курьера с повторяющимся логином")
    public void checkCreateDuplicateCourier(){
        courierRequest.setCourier(courier);
        courierRequest.createCourier();
        courierRequest.createCourier()
                .then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой.q"))
                .and()
                .statusCode(SC_CONFLICT);
    }

    @Test
    @DisplayName("Создание курьера без указания логина (негатив)")
    @Description("Проверка невозможности создать курьера без указания логин")
    public void checkCreateCourierWithoutLogin(){
        courierRequest.setCourier(new Courier("","000001","Alex"));
        courierRequest.createCourier()
                .then().statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat().body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }
    @Test
    @DisplayName("Создание курьера без указания пароля (негатив)")
    @Description("Проверка невозможности создать курьера без указания пароля")
    public void checkCreateCourierWithoutPassword(){
        courierRequest.setCourier(new Courier("BikerAlex","","Alex"));
        courierRequest.createCourier()
                .then().statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat().body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без указания имени (позитив)")
    @Description("Проверка возможности создать курьера без имени")
    public void checkCreateCourierWithoutName() {
        courierRequest.setCourier(new Courier("BikerAlex", "000001", ""));
        courierRequest.createCourier()
                .then().assertThat().body("ok", is(true))
                .and()
                .statusCode(SC_CREATED);
    }

    @After
    public void cleanData(){
        courierRequest.deleteCourier();
    }
}
