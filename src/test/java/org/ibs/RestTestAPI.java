package org.ibs;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import io.restassured.response.Response;

public class RestTestAPI {

        @Test
        void addNewFood(){
            Response response = RestAssured.given()
                    .baseUri("http://localhost:8080")
                    .contentType("application/json")
                    .body("{\n" +
                            " \"name\": \"Дуриан\", \n" +
                            "\"type\": \"FRUIT\", \n" +
                            "\"exotic\": true" +
                            "}")
                    .basePath("/api/food")
                    .when()
                    .post()
                    .then()
                    .log().all()
                    .extract().response();

            Assertions.assertEquals(200, response.getStatusCode(), "Ошибка при создании новой пищевой продукции");
        }

        @Test
        void verifyNewFoodAdded() {
            Response response = RestAssured.given()
                    .baseUri("http://localhost:8080")
                    .get("/api/food");

            Assertions.assertEquals("Дуриан", response.jsonPath().getString("name"), "Неверное название пищевой продукции");
            Assertions.assertEquals("FRUIT", response.jsonPath().getString("type"), "Неверный тип пищевой продукции");
            Assertions.assertTrue(response.jsonPath().getBoolean("exotic"), "Пищевая продукция не является экзотической");

        }

        @Test
        void dataReset(){

            Response response = RestAssured.post("/api/data/reset");

            Assertions.assertEquals(200, response.getStatusCode(), "Ошибка при сбросе данных");
        }
}
