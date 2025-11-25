package com.codex.villain;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VillainResourceTest {

    private static Long createdVillainId;

    @Test
    @Order(1)
    public void testGetAllVillains() {
        given()
            .when().get("/api/villains")
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(greaterThan(0)));
    }

    @Test
    @Order(2)
    public void testCreateVillain() {
        Villain villain = new Villain();
        villain.name = "Test Villain";
        villain.description = "This is a test villain for testing purposes";
        villain.level = 5;
        villain.strength = 50;
        villain.agility = 60;
        villain.intelligence = 70;
        villain.evilPlan = "Test Evil Plan";

        String location = given()
            .contentType(ContentType.JSON)
            .body(villain)
            .when().post("/api/villains")
            .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("name", is("Test Villain"))
                .body("level", is(5))
                .extract().header("Location");

        createdVillainId = Long.parseLong(location.substring(location.lastIndexOf('/') + 1));
    }

    @Test
    @Order(3)
    public void testGetVillainById() {
        if (createdVillainId != null) {
            given()
                .when().get("/api/villains/" + createdVillainId)
                .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body("name", is("Test Villain"))
                    .body("level", is(5));
        }
    }

    @Test
    @Order(4)
    public void testUpdateVillain() {
        if (createdVillainId != null) {
            Villain updatedVillain = new Villain();
            updatedVillain.name = "Updated Test Villain";
            updatedVillain.description = "This is an updated test villain";
            updatedVillain.level = 10;
            updatedVillain.strength = 80;
            updatedVillain.agility = 90;
            updatedVillain.intelligence = 85;
            updatedVillain.evilPlan = "Updated Evil Plan";

            given()
                .contentType(ContentType.JSON)
                .body(updatedVillain)
                .when().put("/api/villains/" + createdVillainId)
                .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body("name", is("Updated Test Villain"))
                    .body("level", is(10));
        }
    }

    @Test
    @Order(5)
    public void testGetRandomVillain() {
        given()
            .when().get("/api/villains/random")
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("name", notNullValue());
    }

    @Test
    @Order(6)
    public void testDeleteVillain() {
        if (createdVillainId != null) {
            given()
                .when().delete("/api/villains/" + createdVillainId)
                .then()
                    .statusCode(204);
        }
    }

    @Test
    @Order(7)
    public void testGetNonExistentVillain() {
        given()
            .when().get("/api/villains/99999")
            .then()
                .statusCode(404);
    }
}
