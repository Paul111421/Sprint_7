package courier.login;

import courier.CourierCard;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class AuthoriseCourierTest {

    @Before
    public void setUp(){
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    CourierCard courierOpeth = new CourierCard("super_cool_courier_10","777","Opeth");

    @Test
    @DisplayName("Проверить авторизацию существующего курьера")
    public void authorizeCourierTest(){

        createNewCourier(courierOpeth);
        findNewCourier200(courierOpeth);

        deleteNewCourier(courierOpeth);
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

    @Step("Найти только что созданного курьера и проверить статус (код 200)")
    public void findNewCourier200(CourierCard courierCard){

        Response responseFindNewCourier = given()
                .header("Content-Type","application/json")
                .and()
                .body(courierCard)
                .when()
                .post("/api/v1/courier/login");
        responseFindNewCourier.then().statusCode(200);
    }

    @Step("Удалить нового курьера по его ID")
    public void deleteNewCourier(CourierCard courierCard){

        Response responseFindNewCourier = given()
                .header("Content-Type","application/json")
                .and()
                .body(courierCard)
                .when()
                .post("/api/v1/courier/login");
        String idCourier = responseFindNewCourier.then().extract().path("id").toString();

        Response responseDeleteNewCourier = given()
                .when()
                .delete("/api/v1/courier/" + idCourier);
        responseDeleteNewCourier.then().statusCode(200).and().assertThat().body("ok", equalTo(true));

    }
}
