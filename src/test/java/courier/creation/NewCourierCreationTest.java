package courier.creation;

import courier.CourierApi;
import courier.CourierBaseTest;
import courier.CourierCard;
import courier.CourierTestValues;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

public class NewCourierCreationTest extends CourierBaseTest {

    private CourierCard validCourier;

    @Before
    public void initializeCouriers(){
        validCourier = CourierTestValues.courierValid;
    }

    @Test
    @DisplayName("Проверка создания курьера")
    @Description("Проверить успешность создания нового курьера в системе при вводе подходящих данных.")
    public void newCourierCreationTest(){
        Response responseCreateNewCourier = CourierApi.createNewCourier(validCourier);
        CourierApi.createNewCourier201(responseCreateNewCourier);

    }

}
