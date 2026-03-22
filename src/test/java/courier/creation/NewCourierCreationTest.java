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

public class NewCourierCreationTest {

    @Before
    public void setUp(){
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    CourierCard courierOpeth = new CourierCard("super_cool_courier_10","777","Opeth");

    @Test
    @DisplayName("Courier creation test")
    public void newCourierCreationTest(){

        createNewCourier();

        String idCourier = findNewCourier();

        deleteNewCourier(idCourier);
    }

    @Step("Create a new courier")
    public void createNewCourier(){

        Response responseNewCourier = given()
                .header("Content-Type","application/json")
                .and()
                .body(courierOpeth)
                .when()
                .post("/api/v1/courier");
        responseNewCourier.then().statusCode(201).and().assertThat().body("ok", equalTo(true));

    }

    @Step("Find newly created courier (needed for aftertest cleanup)")
    public String findNewCourier(){

        Response responseFindNewCourier = given()
                .header("Content-Type","application/json")
                .and()
                .body(courierOpeth)
                .when()
                .post("/api/v1/courier/login");
        String idCourier = responseFindNewCourier.then().extract().path("id").toString();
        return idCourier;

    }

    @Step("Delete newly created courier")
    public void deleteNewCourier(String idCourier){
        Response responseDeleteNewCourier = given()
                .when()
                .delete("/api/v1/courier/" + idCourier);
        responseDeleteNewCourier.then().statusCode(200).and().assertThat().body("ok", equalTo(true));
    }
}
