package courier.creation;

import courier.CourierApi;
import courier.CourierBaseTest;
import courier.CourierTestValues;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

public class NewCourierCreationTest extends CourierBaseTest {

    @Test
    @DisplayName("Проверка создания курьера")
    @Description("Проверить успешность создания нового курьера в системе при вводе подходящих данных.")
    public void newCourierCreationTest(){

        CourierApi.createNewCourier201(CourierTestValues.courierValid);

    }

}
