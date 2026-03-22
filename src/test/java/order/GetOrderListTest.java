package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class GetOrderListTest {

    @Before
    public void setUp(){
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Проверка появления списка заказов и их наполненности")
    public void getOrderListTest(){
        Response response = given()
                .when()
                .get("api/v1/orders");

        response.then()
                .statusCode(200)
                .and()
                .assertThat()
                .body("orders", Matchers.notNullValue())
                .body("orders", Matchers.instanceOf(List.class))
                .body("orders.size()", Matchers.greaterThan(0));
    };
}
