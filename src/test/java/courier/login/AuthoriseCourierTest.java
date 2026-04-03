package courier.login;

import courier.CourierApi;
import courier.CourierBaseTest;
import courier.CourierCard;
import courier.CourierTestValues;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;


public class AuthoriseCourierTest extends CourierBaseTest{

    private CourierCard validCourier;

    @Before
    public void initializeCouriers(){
        validCourier = CourierTestValues.courierValid;
    }

    @Test
    @DisplayName("Проверить авторизацию существующего курьера.")
    @Description("Проверить, успешно ли создаётся новый курьер и можно ли обнаружить его по логину (код 200).")
    public void authorizeCourierTest(){

        Response responseNewValidCourier = CourierApi.createNewCourier(validCourier);
        Response responseFindNewCourier = CourierApi.findNewCourier(validCourier);

        CourierApi.createNewCourier201(responseNewValidCourier);
        CourierApi.findNewCourier200(responseFindNewCourier);

    }

}
