package courier;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CourierApi {

    @Step("Создать нового курьера (общий метод для других)")
    public static Response createNewCourier(CourierCard courierCard){

        return given()
                .header("Content-Type","application/json")
                .and()
                .body(courierCard)
                .when()
                .post(CourierEndpoints.courierBasicEndpoint);

    }

    @Step("Найти курьера по определённому значению без проверок кода")
    public static Response findNewCourier(CourierCard courierCard){
        return given()
                .header("Content-Type","application/json")
                .and()
                .body(courierCard)
                .when()
                .post(CourierEndpoints.courierLoginEndpoint);
    }

    @Step("Удалить нового курьера по его ID")
    public static void deleteNewCourier(CourierCard courierCard){

        Response responseFindNewCourier = findNewCourier(courierCard);

        String idCourier = responseFindNewCourier.then().extract().path("id").toString();

        Response responseDeleteNewCourier = given()
                .when()
                .delete(CourierEndpoints.courierBasicEndpoint + idCourier);
        responseDeleteNewCourier.then().statusCode(SC_OK);

    }

    @Step("Создать нового курьера (общий метод для других)")
    public static void createNewCourier201(Response responseCreateNewCourier){

        responseCreateNewCourier.then().statusCode(SC_CREATED);

    }

    @Step("Создание профиля с пустым логином или паролем (или что-либо из них отсутствует)")
    public static void createNewCourier400(Response responseCreateNewCourier){

        responseCreateNewCourier.then().statusCode(SC_BAD_REQUEST);

    }

    @Step("Проверка сообщения об ошибке создания профиля с пустым логином или паролем")
    public static void createNewCourier400Message(Response responseCreateNewCourier){

        responseCreateNewCourier.then().assertThat().body("message",equalTo("Недостаточно данных для создания учетной записи"));

    }

    @Step("Найти курьера по определённому значению с проверкой кода ответа (код 200)")
    public static void findNewCourier200(Response responseFindNewCourier){

        responseFindNewCourier.then().statusCode(SC_OK);

    }

    @Step("Найти курьера по определённому значению с проверкой кода ответа (код 400)")
    public static void findNewCourier400(Response responseFindNewCourier){

        responseFindNewCourier.then().statusCode(SC_BAD_REQUEST);

    }

    @Step("Проверка сообщения об ошибке поиска курьера по определённому значению (код 400)")
    public static void findNewCourier400Message(Response responseFindNewCourier){

        responseFindNewCourier.then().assertThat().body("message",equalTo("Недостаточно данных для входа"));

    }

    @Step("Найти курьера по определённому значению с проверкой кода ответа (код 404)")
    public static void findNewCourier404(Response responseFindNewCourier){

        responseFindNewCourier.then().statusCode(SC_NOT_FOUND);

    }

    @Step("Найти курьера по определённому значению с проверкой кода ответа (код 404)")
    public static void findNewCourier404Message(Response responseFindNewCourier){

        responseFindNewCourier.then().assertThat().body("message",equalTo("Учетная запись не найдена"));

    }

    @Step("Получить ошибку о создании курьера при введении данных уже существующего курьера")
    public static void assertCannotCreateTwoSameCouriers409(CourierCard courierCard){

        Response responseFirstCourier = createNewCourier(courierCard);
        Response responseSecondCourier = given()
                .header("Content-Type","application/json")
                .and()
                .body(courierCard)
                .when()
                .post(CourierEndpoints.courierBasicEndpoint);
        responseSecondCourier.then().statusCode(SC_CONFLICT);

    }

    //Дополнение в конце "Попробуйте другой" выглядит лучше, чем просто ошибка, но дока есть дока - буду проверять по ней
    @Step("Проверка содержания ошибки о создании курьера при введении данных уже существующего курьера")
    public static void assertCannotCreateTwoSameCouriers409Message(CourierCard courierCard){

        Response responseSecondCourier = given()
                .header("Content-Type","application/json")
                .and()
                .body(courierCard)
                .when()
                .post(CourierEndpoints.courierBasicEndpoint);
        responseSecondCourier.then().assertThat().body("message",equalTo("Этот логин уже используется"));

    }
}
