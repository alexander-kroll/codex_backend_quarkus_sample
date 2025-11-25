package com.codex.ai;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class DescriptionGeneratorResourceTest {

    @Test
    public void testGenerateHeroDescription() {
        DescriptionGeneratorResource.DescriptionRequest request = new DescriptionGeneratorResource.DescriptionRequest();
        request.name = "Test Hero";

        given()
            .contentType(ContentType.JSON)
            .body(request)
            .when().post("/api/ai/hero/description")
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("description", notNullValue())
                .body("description", containsString("Test Hero"));
    }

    @Test
    public void testGenerateVillainDescription() {
        DescriptionGeneratorResource.DescriptionRequest request = new DescriptionGeneratorResource.DescriptionRequest();
        request.name = "Test Villain";

        given()
            .contentType(ContentType.JSON)
            .body(request)
            .when().post("/api/ai/villain/description")
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("description", notNullValue())
                .body("description", containsString("Test Villain"));
    }

    @Test
    public void testGenerateSpecialPower() {
        given()
            .when().get("/api/ai/hero/special-power")
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("power", notNullValue());
    }

    @Test
    public void testGenerateEvilPlan() {
        given()
            .when().get("/api/ai/villain/evil-plan")
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("plan", notNullValue());
    }
}
