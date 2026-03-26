package courier.creation;

import courier.CourierApi;
import courier.CourierBaseTest;
import courier.CourierTestValues;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

public class CannotCreateCourierTests extends CourierBaseTest {

    @Test
    @DisplayName("Проверка создания двух одинаковых курьеров")
    @Description("Проверить, что создание двух одинаковых курьеров по одной " +
            "и той же карточке невозможно (код 409)")
    public void twoSameCouriersTest(){
        CourierApi.assertCannotCreateTwoSameCouriers409(CourierTestValues.courierValid);
    }

    @Test
    @DisplayName("Проверка создания курьера с пустым логином")
    @Description("Проверить, что создать курьера при пустом поле \"Логин\" невозможно (код 400)")
    public void courierCreationWithEmptyLoginTest(){
        CourierApi.createNewCourier400(CourierTestValues.courierNotValidEmptyLogin);
    }


    @Test
    @DisplayName("Проверка создания курьера с пустым паролем")
    @Description("Проверить, что создать курьера при пустом поле \"Пароль\" невозможно (код 400)")
    public void courierCreationWithEmptyPasswordTest(){
        CourierApi.createNewCourier400(CourierTestValues.courierNotValidEmptyPassword);
    }


    @Test
    @DisplayName("Проверка создания курьера с отсутствующим логином/паролем")
    @Description("Проверить, что создать курьера при отсутствующем поле \"Логин\" или поле \"Пароль\" невозможно (код 400)")
    public void courierCreationWithNoLoginOrPasTest(){
        CourierApi.createNewCourier400(CourierTestValues.courierNotValidNoLoginOrPas);
    }

}
