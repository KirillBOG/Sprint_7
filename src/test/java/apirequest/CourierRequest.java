package apirequest;

import Courier.Courier;
import io.qameta.allure.Step;
import io.restassured.response.Response;


import static io.restassured.RestAssured.given;

public class CourierRequest extends Base {
    private Courier courier;
    private final static String CREATE_ENDPOINT_COURIER = "/api/v1/courier";
    private final static String LOGIN_ENDPOINT_COURIER ="/api/v1/courier/login";

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    @Step("Создать курьера, проверить код ответа")
    public Response createCourier(){
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when()
                        .post (CREATE_ENDPOINT_COURIER);
        return response;
    }

    @Step("Авторизоваться курьером, получить его id и проверить код ответа")
    public Response loginCourier(){
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when()
                        .post (LOGIN_ENDPOINT_COURIER);
        return response;
    }

    @Step("Удалить курьера")
    public void deleteCourier(){
        Integer id =
                given()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when()
                        .post (LOGIN_ENDPOINT_COURIER)
                        .then().extract().body().path("id");
        if (id != null) {
            given()
                    .delete (CREATE_ENDPOINT_COURIER+ "/{id}", id.toString());}
    }

}
