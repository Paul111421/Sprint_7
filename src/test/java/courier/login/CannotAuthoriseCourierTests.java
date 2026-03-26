package courier.login;

import courier.CourierApi;
import courier.CourierBaseTest;
import courier.CourierTestValues;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

public class CannotAuthoriseCourierTests extends CourierBaseTest {

    @Test
    @DisplayName("Проверка авторизации при неправильном логине")
    @Description("Проверить, что при неправильном логине и правильном пароле курьер не найдётся (код 404)")
    public void courierAuthorizationWrongLoginTest(){

        CourierApi.createNewCourier201(CourierTestValues.courierValid);
        CourierApi.findNewCourier404(CourierTestValues.courierNotValidDifferentLogin);

    }

    @Test
    @DisplayName("Проверка авторизации при неправильном пароле")
    @Description("Проверить, что при правильном логине и неправильном пароле курьер не найдётся (код 404)")
    public void courierAuthorizationWrongPassTest(){

        CourierApi.createNewCourier201(CourierTestValues.courierValid);
        CourierApi.findNewCourier404(CourierTestValues.courierNotValidDifferentPass);

    }

    @Test
    @DisplayName("Проверка авторизации при отсутствующем логине или пароле")
    @Description("Проверить, что авторизовать курьера при отсутствующем логине или пароле невозможно\n" +
            "Внимание - на данный момент выдаёт ошибку 504. Баг!")
    public void courierAuthorizationNoLogOrPassTest(){

        CourierApi.createNewCourier201(CourierTestValues.courierValid);
        CourierApi.findNewCourier400(CourierTestValues.courierNotValidNoLoginOrPas);

    }

    @Test
    @DisplayName("Проверка авторизации несуществующего курьера")
    @Description("Проверить, что авторизовать курьера, не созданного в системе, по его логину и паролю невозможно " +
            "(курьера нет в системе - код 404)")
    public void nonExistentCourierAuthorizationTest(){

        CourierApi.findNewCourier404(CourierTestValues.courierValid);

    }

}
