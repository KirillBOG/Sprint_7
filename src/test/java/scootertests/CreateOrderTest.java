package scootertests;

import apirequest.OrderRequest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import Order.Order;
import static org.apache.http.HttpStatus.*;

import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class CreateOrderTest{
    Order order;

    public CreateOrderTest (Order order) {
        this.order = order;
    }

    @Before
    public void setUp(){
        orderRequest.setUp();
    }

    @Parameterized.Parameters
    public static Object[][] getTestData(){
        return new Object[][]{
                {new Order("Вера","Иванова","Дружбы Народов","Кутузовская","+79091210001",2,"2025-01-22","Позвонить за час",new String[]{"BLACK"})},
                {new Order("Надежда","Петрова","Мира пр-кт","Проспект Мира","89091210002",3,"2025-01-23","Позвонить за час",new String[]{"GREY"})},
                {new Order("Любовь","Сидорова","Лубянка 1","Лубянка","+79091210003",4,"2025-01-24","Позвонить за час",new String[]{"BLACK","GREY"})},
                {new Order("Алекс","Байкеров","Новый арбат","Арбатская","89091210004",5,"2025-01-25","Позвонить за час",new String[]{})}
        };
    }

    OrderRequest orderRequest = new OrderRequest();

    @Test
    @DisplayName("Создание заказа самоката с разным цветом")
    @Description("Проверка создания заказа самоката с разным цветом")
    public void checkCreateOrder(){
        orderRequest.setOrder(order);
        orderRequest.createOrderRequest()
                .then().statusCode(SC_CREATED)
                .and()
                .assertThat().body("track", notNullValue());
    }
}