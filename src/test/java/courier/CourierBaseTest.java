package courier;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;

import static org.apache.http.HttpStatus.SC_OK;

public class CourierBaseTest {

    @Before
    public void setUp(){
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @After
    @Step("Найти и удалить курьера")
    public void findAndDeleteNewCourier(){

        Response responseFindNewCourier = CourierApi.findNewCourier(CourierTestValues.courierValid);

        if (responseFindNewCourier.getStatusCode() == SC_OK){

            CourierApi.deleteNewCourier(CourierTestValues.courierValid);

        } else {
            System.out.println("Новый курьер не найден - заканчиваю тест.");
        }
    }
}
