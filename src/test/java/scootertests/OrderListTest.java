package scootertests;

import apirequest.OrderRequest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.notNullValue;

public class OrderListTest{
    OrderRequest orderRequest = new OrderRequest();
    @Before
    public void setUp(){
        orderRequest.setUp();}
    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка получения списка заказов")
    public void checkCreateOrder(){
        orderRequest.getOrdersRequest()
                .then().statusCode(SC_OK)
                .and()
                .assertThat().body("orders", notNullValue());
    }
}