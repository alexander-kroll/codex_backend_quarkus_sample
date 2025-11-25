package com.codex.fight;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class FightResourceTest {

    @Test
    public void testGetAllFights() {
        given()
            .when().get("/api/fights")
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void testStartFight() {
        FightResource.FightRequest request = new FightResource.FightRequest();
        request.heroId = 1L;
        request.villainId = 1L;

        given()
            .contentType(ContentType.JSON)
            .body(request)
            .when().post("/api/fights")
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("heroId", is(1))
                .body("villainId", is(1))
                .body("winnerId", notNullValue())
                .body("winnerType", notNullValue())
                .body("battleLog", notNullValue());
    }

    @Test
    public void testStartFightWithInvalidHero() {
        FightResource.FightRequest request = new FightResource.FightRequest();
        request.heroId = 99999L;
        request.villainId = 1L;

        given()
            .contentType(ContentType.JSON)
            .body(request)
            .when().post("/api/fights")
            .then()
                .statusCode(400);
    }
}
