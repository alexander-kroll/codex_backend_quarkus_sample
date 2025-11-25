package com.codex.fight;

import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/api/fights")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Fight Resource", description = "Fight REST API")
public class FightResource {

    @Inject
    FightService fightService;

    @GET
    @Operation(summary = "Get all fights")
    @APIResponse(responseCode = "200", description = "List of fights", content = @Content(schema = @Schema(implementation = Fight.class)))
    public List<Fight> getAllFights() {
        Log.info("Getting all fights");
        return Fight.listAll();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get a fight by ID")
    @APIResponse(responseCode = "200", description = "Fight found", content = @Content(schema = @Schema(implementation = Fight.class)))
    @APIResponse(responseCode = "404", description = "Fight not found")
    public Response getFightById(@PathParam("id") Long id) {
        Log.infof("Getting fight with id: %d", id);
        Fight fight = Fight.findById(id);
        if (fight == null) {
            Log.warnf("Fight with id %d not found", id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(fight).build();
    }

    @POST
    @Operation(summary = "Start a fight between a hero and a villain")
    @APIResponse(responseCode = "200", description = "Fight completed", content = @Content(schema = @Schema(implementation = Fight.class)))
    @APIResponse(responseCode = "400", description = "Invalid input")
    public Response startFight(FightRequest request) {
        Log.infof("Starting fight between hero %d and villain %d", request.heroId, request.villainId);
        try {
            Fight fight = fightService.performFight(request.heroId, request.villainId);
            return Response.ok(fight).build();
        } catch (IllegalArgumentException e) {
            Log.errorf("Error starting fight: %s", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    public static class FightRequest {
        @NotNull
        public Long heroId;
        
        @NotNull
        public Long villainId;
    }
}
