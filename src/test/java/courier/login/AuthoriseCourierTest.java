package courier.login;

import courier.CourierApi;
import courier.CourierBaseTest;
import courier.CourierTestValues;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;


public class AuthoriseCourierTest extends CourierBaseTest{

    @Test
    @DisplayName("Проверить авторизацию существующего курьера.")
    @Description("Проверить, успешно ли создаётся новый курьер и можно ли обнаружить его по логину (код 200).")
    public void authorizeCourierTest(){

        CourierApi.createNewCourier(CourierTestValues.courierValid);
        CourierApi.findNewCourier200(CourierTestValues.courierValid);

    }

}
