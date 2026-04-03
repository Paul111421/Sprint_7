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

public class CannotAuthoriseCourierTests extends CourierBaseTest {

    private CourierCard validCourier;
    private CourierCard differentLoginCourier;
    private CourierCard differentPasswordCourier;
    private CourierCard noLogOrPassCourier;

    @Before
    public void initializeCouriers(){
        validCourier = CourierTestValues.courierValid;
        differentLoginCourier = CourierTestValues.courierNotValidDifferentLogin;
        differentPasswordCourier = CourierTestValues.courierNotValidDifferentPass;
        noLogOrPassCourier = CourierTestValues.courierNotValidNoLoginOrPas;
    }

    @Test
    @DisplayName("Проверка авторизации при неправильном логине")
    @Description("Проверить, что при неправильном логине и правильном пароле курьер не найдётся (код 404)")
    public void courierAuthorizationWrongLoginTest(){

        Response responseNewValidCourier = CourierApi.createNewCourier(validCourier);
        Response responseFindNewCourier = CourierApi.findNewCourier(differentLoginCourier);

        CourierApi.createNewCourier201(responseNewValidCourier);

        CourierApi.findNewCourier404(responseFindNewCourier);
        CourierApi.findNewCourier404Message(responseFindNewCourier);

    }

    @Test
    @DisplayName("Проверка авторизации при неправильном пароле")
    @Description("Проверить, что при правильном логине и неправильном пароле курьер не найдётся (код 404)")
    public void courierAuthorizationWrongPassTest(){

        Response responseNewValidCourier = CourierApi.createNewCourier(validCourier);
        Response responseFindNewCourier = CourierApi.findNewCourier(differentPasswordCourier);

        CourierApi.createNewCourier201(responseNewValidCourier);

        CourierApi.findNewCourier404(responseFindNewCourier);
        CourierApi.findNewCourier404Message(responseFindNewCourier);

    }

    @Test
    @DisplayName("Проверка авторизации при отсутствующем логине или пароле")
    @Description("Проверить, что авторизовать курьера при отсутствующем логине или пароле невозможно\n" +
            "Внимание - на данный момент выдаёт ошибку 504. Баг!")
    public void courierAuthorizationNoLogOrPassTest(){

        Response responseNewValidCourier = CourierApi.createNewCourier(validCourier);
        Response responseFindNewCourier = CourierApi.findNewCourier(noLogOrPassCourier);

        CourierApi.createNewCourier201(responseNewValidCourier);

        CourierApi.findNewCourier400(responseFindNewCourier);
        CourierApi.findNewCourier400Message(responseFindNewCourier);

    }

    @Test
    @DisplayName("Проверка авторизации несуществующего курьера")
    @Description("Проверить, что авторизовать курьера, не созданного в системе, по его логину и паролю невозможно " +
            "(курьера нет в системе - код 404)")
    public void nonExistentCourierAuthorizationTest(){

        Response responseFindNewCourier = CourierApi.findNewCourier(validCourier);

        CourierApi.findNewCourier404(responseFindNewCourier);

    }

}
