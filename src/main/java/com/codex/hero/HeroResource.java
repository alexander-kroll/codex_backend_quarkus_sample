package com.codex.hero;

import io.quarkus.logging.Log;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;
import java.util.List;

@Path("/api/heroes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Hero Resource", description = "Hero REST API")
public class HeroResource {

    @GET
    @Operation(summary = "Get all heroes")
    @APIResponse(responseCode = "200", description = "List of heroes", content = @Content(schema = @Schema(implementation = Hero.class)))
    public List<Hero> getAllHeroes() {
        Log.info("Getting all heroes");
        return Hero.listAll();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get a hero by ID")
    @APIResponse(responseCode = "200", description = "Hero found", content = @Content(schema = @Schema(implementation = Hero.class)))
    @APIResponse(responseCode = "404", description = "Hero not found")
    public Response getHeroById(@PathParam("id") Long id) {
        Log.infof("Getting hero with id: %d", id);
        Hero hero = Hero.findById(id);
        if (hero == null) {
            Log.warnf("Hero with id %d not found", id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(hero).build();
    }

    @POST
    @Transactional
    @Operation(summary = "Create a new hero")
    @APIResponse(responseCode = "201", description = "Hero created", content = @Content(schema = @Schema(implementation = Hero.class)))
    @APIResponse(responseCode = "400", description = "Invalid input")
    public Response createHero(@Valid Hero hero) {
        Log.infof("Creating hero: %s", hero.name);
        hero.persist();
        return Response.created(URI.create("/api/heroes/" + hero.id)).entity(hero).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Update an existing hero")
    @APIResponse(responseCode = "200", description = "Hero updated", content = @Content(schema = @Schema(implementation = Hero.class)))
    @APIResponse(responseCode = "404", description = "Hero not found")
    public Response updateHero(@PathParam("id") Long id, @Valid Hero updatedHero) {
        Log.infof("Updating hero with id: %d", id);
        Hero hero = Hero.findById(id);
        if (hero == null) {
            Log.warnf("Hero with id %d not found", id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        hero.name = updatedHero.name;
        hero.description = updatedHero.description;
        hero.level = updatedHero.level;
        hero.strength = updatedHero.strength;
        hero.agility = updatedHero.agility;
        hero.intelligence = updatedHero.intelligence;
        hero.imageUrl = updatedHero.imageUrl;
        hero.specialPower = updatedHero.specialPower;
        
        hero.persist();
        return Response.ok(hero).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete a hero")
    @APIResponse(responseCode = "204", description = "Hero deleted")
    @APIResponse(responseCode = "404", description = "Hero not found")
    public Response deleteHero(@PathParam("id") Long id) {
        Log.infof("Deleting hero with id: %d", id);
        boolean deleted = Hero.deleteById(id);
        if (!deleted) {
            Log.warnf("Hero with id %d not found", id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }

    @GET
    @Path("/random")
    @Operation(summary = "Get a random hero")
    @APIResponse(responseCode = "200", description = "Random hero", content = @Content(schema = @Schema(implementation = Hero.class)))
    public Response getRandomHero() {
        Log.info("Getting random hero");
        Hero hero = Hero.findRandom();
        if (hero == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(hero).build();
    }
}
