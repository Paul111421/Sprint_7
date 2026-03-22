package order;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class CreateOrderTests {

    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;

    @Before
    public void setUp(){
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    static String[] black = {"BLACK"};
    static String[] grey = {"GREY"};
    static String[] blackAndGrey = {"BLACK", "GREY"};
    static String[] noColour = {};

    public CreateOrderTests(String[] color) {
        this.firstName = "Lemmy";
        this.lastName = "Kilmister";
        this.address = "Rainbow Grill and Bar";
        this.metroStation = 7;
        this.phone = "+7 911 420 50 70";
        this.rentTime = 5;
        this.deliveryDate = "2020-06-06";
        this.comment = "Jacks better be cold!";
        this.color = color;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Object[][] getColoursForOrderCards(){
        return new Object[][]{
                {noColour},
                {black},
                {grey},
                {blackAndGrey}
        };
    }

    @Test
    @DisplayName("Проверка создания нового заказа без цветов")

    //Мои полуторачасовые попытки заставить найти систему заказ по трекеру и айди ни к чему не привели, кроме спама 400 ошибкой.
    //Какая-то проблема со стороны сервиса - в Postman и отмена, и завершение не работают что на принятом, что не на принятом заказе.
    //Пока оставлю просто создание заказа, с этим проблем нет. Заготовки методов на поиск и удаление заказов оставлю на будущее.
    public void createOrderNoColourTest(){

        createNewOrder();

    }


    @Step("Создать новый заказ и получить его трек-номер")
    public int createNewOrder(){
        OrderCard orderCard = new OrderCard(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);

        Response responseNewOrder = given()
                .header("Content-Type","application/json")
                .and()
                .body(orderCard)
                .when()
                .post("/api/v1/orders");

        responseNewOrder.then().statusCode(201);

        int wat = responseNewOrder
                .then()
                .extract()
                .path("track");

        System.out.println(wat);
        return wat;
    }

    @Step("Find newly created Order (needed for aftertest cleanup)")
    public String findNewOrder(OrderCard orderCard){

        Response responseFindNewOrder = given()
                .header("Content-Type","application/json")
                .and()
                .body(orderCard)
                .when()
                .post("/api/v1/Order/login");
        String trackOrder = responseFindNewOrder.then().extract().path("track").toString();
        return trackOrder;

    }

    @Step("Cancel newly created Order")
    public void cancelNewOrder(int trackOrder){

        Response responseCancelByIdNewOrder = given()
                .header("Content-Type","application/json")
                .and()
                .body("{\"track\": " + trackOrder + "}")
                .when()
                .put("/api/v1/orders/cancel");
        responseCancelByIdNewOrder.then().statusCode(200);

        Response responseFindIdNewOrder = given()
                .when()
                .get("/api/v1/orders/track?t=" + trackOrder);

        String idOrder = responseFindIdNewOrder.then().extract().path("order.id").toString();

        Response responseDeleteNewOrder = given()
                .when()
                .put("/api/v1/orders/finish/" + idOrder);
        responseDeleteNewOrder.then().statusCode(200).and().assertThat().body("ok", equalTo(true));
    }
}
