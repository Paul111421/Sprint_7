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

public class CannotCreateCourierTests extends CourierBaseTest {

    private CourierCard validCourier;
    private CourierCard emptyLoginCourier;
    private CourierCard emptyPasswordCourier;
    private CourierCard noLogOrPassCourier;

    @Before
    public void initializeCouriers(){
        validCourier = CourierTestValues.courierValid;
        emptyLoginCourier = CourierTestValues.courierNotValidEmptyLogin;
        emptyPasswordCourier = CourierTestValues.courierNotValidEmptyPassword;
        noLogOrPassCourier = CourierTestValues.courierNotValidNoLoginOrPas;
    }

    @Test
    @DisplayName("Проверка создания двух одинаковых курьеров")
    @Description("Проверить, что создание двух одинаковых курьеров по одной " +
            "и той же карточке невозможно (код 409)")
    public void twoSameCouriersTest(){
        CourierApi.assertCannotCreateTwoSameCouriers409(validCourier);
        CourierApi.assertCannotCreateTwoSameCouriers409Message(validCourier);
    }

    @Test
    @DisplayName("Проверка создания курьера с пустым логином")
    @Description("Проверить, что создать курьера при пустом поле \"Логин\" невозможно (код 400)")
    public void courierCreationWithEmptyLoginTest(){

        Response responseNewCourier = CourierApi.createNewCourier(emptyLoginCourier);

        CourierApi.createNewCourier400(responseNewCourier);
        CourierApi.createNewCourier400Message(responseNewCourier);
    }

    @Test
    @DisplayName("Проверка создания курьера с пустым паролем")
    @Description("Проверить, что создать курьера при пустом поле \"Пароль\" невозможно (код 400)")
    public void courierCreationWithEmptyPasswordTest(){

        Response responseNewCourier = CourierApi.createNewCourier(emptyPasswordCourier);

        CourierApi.createNewCourier400(responseNewCourier);
        CourierApi.createNewCourier400Message(responseNewCourier);
    }

    @Test
    @DisplayName("Проверка создания курьера с отсутствующим логином/паролем")
    @Description("Проверить, что создать курьера при отсутствующем поле \"Логин\" или поле \"Пароль\" невозможно (код 400)")
    public void courierCreationWithNoLoginOrPasTest(){

        Response responseNewCourier = CourierApi.createNewCourier(noLogOrPassCourier);

        CourierApi.createNewCourier400(responseNewCourier);
        CourierApi.createNewCourier400Message(responseNewCourier);

    }

}
