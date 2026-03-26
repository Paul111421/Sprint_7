package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CreateOrderTests extends OrderBaseTest{

    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;


    public CreateOrderTests(String[] color) {
        this.firstName = OrderTestValues.firstName;
        this.lastName = OrderTestValues.lastName;
        this.address = OrderTestValues.address;
        this.metroStation = OrderTestValues.metroStation;
        this.phone = OrderTestValues.phone;
        this.rentTime = OrderTestValues.rentTime;
        this.deliveryDate = OrderTestValues.deliveryDate;
        this.comment = OrderTestValues.comment;
        this.color = color;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Object[][] getColoursForOrderCards(){
        return new Object[][]{
                {OrderTestValues.noColour},
                {OrderTestValues.black},
                {OrderTestValues.grey},
                {OrderTestValues.blackAndGrey}
        };
    }

    @Test
    @DisplayName("Проверка создания нового заказа")
    @Description("Проверить возможность создать новый заказ с цветами и без.")
    //Мои полуторачасовые попытки заставить найти в системе заказ по трекеру и айди ни к чему не привели, кроме спама 400 ошибкой.
    //Какая-то проблема со стороны сервиса - в Postman и отмена, и завершение не работают что на принятом, что не на принятом заказе.
    //Пока оставлю просто создание заказа, с этим проблем нет. Заготовки методов на поиск и удаление заказов оставлю на будущее.
    public void createOrderTest(){

        OrderCard orderCardForTest = new OrderCard(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);

        OrderApi.createNewOrder(orderCardForTest);

    }

}
