package order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.hamcrest.Matchers;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;

public class OrderApi {

    @Step("Создать новый заказ и получить его трек-номер")
    protected static Response createNewOrder(OrderCard orderCard){

        Response responseNewOrder = given()
                .header("Content-Type","application/json")
                .and()
                .body(orderCard)
                .when()
                .post(OrderEndpoints.ordersBasicEndpoint);

        responseNewOrder.then().statusCode(SC_CREATED);

        return responseNewOrder;

    }

    @Step("Найти трек-номер заказа")
    protected static String getOrderTrack (Response responseOrder){
        return responseOrder
                .then()
                .extract()
                .path("track");
    }

    @Step("Найти существующий в системе заказ")
    protected static Response findOrder(OrderCard orderCard){
        return given()
                .header("Content-Type","application/json")
                .and()
                .body(orderCard)
                .when()
                .post(OrderEndpoints.orderLoginEndpoint);
    }

    @Step("Найти трек-номер существующего в системе заказа")
    protected static String findOrderTrack(OrderCard orderCard){

        Response responseFindOrder = findOrder(orderCard);
        return responseFindOrder.then().extract().path("track").toString();

    }

    @Step("Delete newly created Order")
    public void deleteNewOrder(OrderCard orderCard){

        Response responseFindOrder = findOrder(orderCard);

        if (responseFindOrder.statusCode() == 200) {

            String trackOrder = findOrderTrack(orderCard);

            cancelByIdOrder(trackOrder);

            String idOrder = findOrderId(trackOrder);

            deleteOrderById(idOrder);
        }
    }

    @Step("Отменить заказ по ID")
    protected static void cancelByIdOrder(String trackOrder){
        Response responseCancelByIdNewOrder = given()
                .header("Content-Type","application/json")
                .and()
                .body("{\"track\": " + trackOrder + "}")
                .when()
                .put("/api/v1/orders/cancel");
        responseCancelByIdNewOrder.then().statusCode(SC_OK);
    }

    @Step("Найти ID заказа")
    protected static String findOrderId(String trackOrder){
        Response responseFindIdNewOrder = given()
                .when()
                .get("/api/v1/orders/track?t=" + trackOrder);

        return responseFindIdNewOrder.then().extract().path("order.id").toString();

    }

    @Step("Удалить заказ по его ID")
    protected static void deleteOrderById(String idOrder){
        Response responseDeleteNewOrder = given()
                .when()
                .put("/api/v1/orders/finish/" + idOrder);
        responseDeleteNewOrder.then().statusCode(SC_OK);
    }
    @Step("Получить список заказов")
    protected static Response getOrderList(){
        return given()
                .when()
                .get(OrderEndpoints.ordersBasicEndpoint);

    }

    @Step("Проверить, что список заказов не пустой")
    protected static void assertOrderListNotEmpty(Response orderListResponse){
        orderListResponse.then()
                .statusCode(SC_OK)
                .and()
                .assertThat()
                .body("orders", Matchers.notNullValue())
                .body("orders", Matchers.instanceOf(List.class))
                .body("orders.size()", Matchers.greaterThan(0));
    }
}
