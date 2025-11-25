package com.codex.hero;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HeroResourceTest {

    private static Long createdHeroId;

    @Test
    @Order(1)
    public void testGetAllHeroes() {
        given()
            .when().get("/api/heroes")
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(greaterThan(0)));
    }

    @Test
    @Order(2)
    public void testCreateHero() {
        Hero hero = new Hero();
        hero.name = "Test Hero";
        hero.description = "This is a test hero for testing purposes";
        hero.level = 5;
        hero.strength = 50;
        hero.agility = 60;
        hero.intelligence = 70;
        hero.specialPower = "Test Power";

        String location = given()
            .contentType(ContentType.JSON)
            .body(hero)
            .when().post("/api/heroes")
            .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("name", is("Test Hero"))
                .body("level", is(5))
                .extract().header("Location");

        // Extract ID from location header
        createdHeroId = Long.parseLong(location.substring(location.lastIndexOf('/') + 1));
    }

    @Test
    @Order(3)
    public void testGetHeroById() {
        if (createdHeroId != null) {
            given()
                .when().get("/api/heroes/" + createdHeroId)
                .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body("name", is("Test Hero"))
                    .body("level", is(5));
        }
    }

    @Test
    @Order(4)
    public void testUpdateHero() {
        if (createdHeroId != null) {
            Hero updatedHero = new Hero();
            updatedHero.name = "Updated Test Hero";
            updatedHero.description = "This is an updated test hero";
            updatedHero.level = 10;
            updatedHero.strength = 80;
            updatedHero.agility = 90;
            updatedHero.intelligence = 85;
            updatedHero.specialPower = "Updated Power";

            given()
                .contentType(ContentType.JSON)
                .body(updatedHero)
                .when().put("/api/heroes/" + createdHeroId)
                .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body("name", is("Updated Test Hero"))
                    .body("level", is(10));
        }
    }

    @Test
    @Order(5)
    public void testGetRandomHero() {
        given()
            .when().get("/api/heroes/random")
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("name", notNullValue());
    }

    @Test
    @Order(6)
    public void testDeleteHero() {
        if (createdHeroId != null) {
            given()
                .when().delete("/api/heroes/" + createdHeroId)
                .then()
                    .statusCode(204);
        }
    }

    @Test
    @Order(7)
    public void testGetNonExistentHero() {
        given()
            .when().get("/api/heroes/99999")
            .then()
                .statusCode(404);
    }

    @Test
    @Order(8)
    public void testCreateInvalidHero() {
        Hero hero = new Hero();
        hero.name = "AB"; // Too short
        hero.description = "Short"; // Too short

        given()
            .contentType(ContentType.JSON)
            .body(hero)
            .when().post("/api/heroes")
            .then()
                .statusCode(400);
    }
}
