package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;


//Пересобрал проверку списка согласно рекомендациям по эндпоинтам и методам для ручек
public class GetOrderListTest extends OrderBaseTest{

    @Test
    @DisplayName("Проверка списка заказов")
    @Description("Проверить получение списка всех заказов по ручке GET и удостовериться, что список не пуст")
    public void getOrderListTest(){

        Response orderListResponse = OrderApi.getOrderList();

        OrderApi.assertOrderListNotEmpty(orderListResponse);

    };
}
