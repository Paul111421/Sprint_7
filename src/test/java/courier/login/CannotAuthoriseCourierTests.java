package courier.login;

import courier.CourierCard;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class CannotAuthoriseCourierTests {

    @Before
    public void setUp(){
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    CourierCard courierOpeth = new CourierCard("super_cool_courier_10","777","Opeth");
    CourierCard courierOpethWrongLogin = new CourierCard("ultra_bad_courier_11","777");
    CourierCard courierOpethWrongPass = new CourierCard("super_cool_courier_10","666");
    CourierCard courierOpethNoLogOrPas = new CourierCard("777");
    CourierCard courierHank = new CourierCard("Hank_J_Wimbleton","2002");

    @Test
    @DisplayName("Проверка авторизации при неправильном логине")
    public void courierAuthorizationWrongLoginTest(){

        createNewCourier(courierOpeth);
        findNewCourier404(courierOpethWrongLogin);

    }

    @Test
    @DisplayName("Проверка авторизации при неправильном пароле")
    public void courierAuthorizationWrongPassTest(){

        createNewCourier(courierOpeth);
        findNewCourier404(courierOpethWrongPass);

    }

    @Test
    @DisplayName("Проверка авторизации при отсутствующем логине или пароле")
    @Description("Внимание - на данный момент выдаёт ошибку 504. Баг!")
    public void courierAuthorizationNoLogOrPassTest(){

        createNewCourier(courierOpeth);
        findNewCourierOnlyLog400(courierOpethNoLogOrPas);

    }

    @Test
    @DisplayName("Проверка авторизации при неправильном логине")
    public void nonExistentCourierAuthorizationTest(){

        findNewCourier404(courierHank);

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

    @Step("Найти только что созданного курьера и проверить статус (код 404)")
    public void findNewCourier404(CourierCard courierCard){

        Response responseFindNewCourier = given()
                .header("Content-Type","application/json")
                .and()
                .body(courierCard)
                .when()
                .post("/api/v1/courier/login");
        responseFindNewCourier.then().statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));

    }

    @Step("Запрос авторизации курьера без логина или пароля")
    public void findNewCourierOnlyLog400(CourierCard courierCard){

        Response responseFindNewCourier = given()
                .header("Content-Type","application/json")
                .and()
                .body(courierCard)
                .when()
                .post("/api/v1/courier/login");
        responseFindNewCourier.then().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));

    }

    @After
    @Step("Удалить нового курьера по его ID")
    public void deleteNewCourier(){

        Response responseFindNewCourier = given()
                .header("Content-Type","application/json")
                .and()
                .body(courierOpeth)
                .when()
                .post("/api/v1/courier/login");
        String idCourier = responseFindNewCourier.then().extract().path("id").toString();

        Response responseDeleteNewCourier = given()
                .when()
                .delete("/api/v1/courier/" + idCourier);
        responseDeleteNewCourier.then().statusCode(200).and().assertThat().body("ok", equalTo(true));

    }
}
