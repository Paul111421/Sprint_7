package courier.creation;

import courier.CourierCard;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class CannotCreateCourierTests {

    @Before
    public void setUp(){
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    CourierCard courierOpeth = new CourierCard("super_cool_courier_10","777","Opeth");
    CourierCard courierMegaEmptyLogin = new CourierCard("","333","Mega");
    CourierCard courierMegaEmptyPassword = new CourierCard("eisen_herz_1880","","Mega");
    CourierCard courierMegaNoLoginOrPas = new CourierCard("333");


    @Test
    @DisplayName("Проверить, что создание двух одинаковых курьеров невозможно")
    public void twoSameCouriersTest(){

        createNewCourier(courierOpeth);
        assertCannotCreateTwoSameCouriers(courierOpeth);

        String idCourier = findNewCourier(courierOpeth);
        deleteNewCourier(idCourier);

    }

    @Test
    @DisplayName("Проверить получение ошибки при создании курьера с пустым логином")
    public void courierCreationWithEmptyLoginTest(){
        createNewCourierEmptyLoginCheck(courierMegaEmptyLogin);
    }


    @Test
    @DisplayName("Проверить получение ошибки при создании курьера с пустым паролем")
    public void courierCreationWithEmptyPasswordTest(){
        createNewCourierEmptyLoginCheck(courierMegaEmptyPassword);
    }


    @Test
    @DisplayName("Проверить получение ошибки при создании курьера с отсутствующим логином или паролем")
    public void courierCreationWithNoLoginOrPasTest(){
        createNewCourierEmptyLoginCheck(courierMegaNoLoginOrPas);
    }

    @Step("Создать нового курьера")
    public void createNewCourier(CourierCard courierCard){

        Response responseNewCourier = given()
                .header("Content-Type","application/json")
                .and()
                .body(courierCard)
                .when()
                .post("/api/v1/courier");
        responseNewCourier.then().statusCode(201).and().assertThat().body("ok", equalTo(true));

    }

    @Step("Найти курьера")
    public String findNewCourier(CourierCard courierCard){

        Response responseFindNewCourier = given()
                .header("Content-Type","application/json")
                .and()
                .body(courierCard)
                .when()
                .post("/api/v1/courier/login");
        String idCourier = responseFindNewCourier.then().extract().path("id").toString();
        return idCourier;

    }

    @Step("Удалить недавно созданного курьера")
    public void deleteNewCourier(String idCourier){
        Response responseDeleteNewCourier = given()
                .when()
                .delete("/api/v1/courier/" + idCourier);
        responseDeleteNewCourier.then().statusCode(200).and().assertThat().body("ok", equalTo(true));
    }

    @Step("Получить ошибку о создании курьера при введении данных уже существующего курьера")
    public void assertCannotCreateTwoSameCouriers(CourierCard courierCard){

        Response responseNewCourier = given()
                .header("Content-Type","application/json")
                .and()
                .body(courierCard)
                .when()
                .post("/api/v1/courier");

        responseNewCourier.then().statusCode(409)
                .and()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

    }

    @Step("Создание профиля с пустым логином или паролем (или что-либо из них отсутствует)")
    public void createNewCourierEmptyLoginCheck(CourierCard courierCard){

        Response responseNewCourierEmptyLogin = given()
                .header("Content-Type","application/json")
                .and()
                .body(courierCard)
                .when()
                .post("/api/v1/courier");

        responseNewCourierEmptyLogin.then().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
