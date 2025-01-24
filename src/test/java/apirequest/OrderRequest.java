package apirequest;

import Order.Order;
import io.qameta.allure.Step;
import io.restassured.response.Response;


import static io.restassured.RestAssured.given;

public class OrderRequest extends Base{
    private Order order;
    private final static String ENDPOINT_ORDERS = "/api/v1/orders";

    public void setOrder(Order order) {
        this.order = order;
    }

    @Step("Создать заказ, проверить код ответа и номер заказа(track)")
    public Response createOrderRequest(){
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(order)
                        .when()
                        .post (ENDPOINT_ORDERS);
        return response;
    }

    @Step("Получить список заказов, проверить что он не пустой и код ответа")
    public Response getOrdersRequest(){
        Response response =
                given()
                        .get (ENDPOINT_ORDERS);
        return  response;
    }
}