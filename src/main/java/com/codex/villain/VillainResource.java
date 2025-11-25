package com.codex.villain;

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

@Path("/api/villains")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Villain Resource", description = "Villain REST API")
public class VillainResource {

    @GET
    @Operation(summary = "Get all villains")
    @APIResponse(responseCode = "200", description = "List of villains", content = @Content(schema = @Schema(implementation = Villain.class)))
    public List<Villain> getAllVillains() {
        Log.info("Getting all villains");
        return Villain.listAll();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get a villain by ID")
    @APIResponse(responseCode = "200", description = "Villain found", content = @Content(schema = @Schema(implementation = Villain.class)))
    @APIResponse(responseCode = "404", description = "Villain not found")
    public Response getVillainById(@PathParam("id") Long id) {
        Log.infof("Getting villain with id: %d", id);
        Villain villain = Villain.findById(id);
        if (villain == null) {
            Log.warnf("Villain with id %d not found", id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(villain).build();
    }

    @POST
    @Transactional
    @Operation(summary = "Create a new villain")
    @APIResponse(responseCode = "201", description = "Villain created", content = @Content(schema = @Schema(implementation = Villain.class)))
    @APIResponse(responseCode = "400", description = "Invalid input")
    public Response createVillain(@Valid Villain villain) {
        Log.infof("Creating villain: %s", villain.name);
        villain.persist();
        return Response.created(URI.create("/api/villains/" + villain.id)).entity(villain).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Update an existing villain")
    @APIResponse(responseCode = "200", description = "Villain updated", content = @Content(schema = @Schema(implementation = Villain.class)))
    @APIResponse(responseCode = "404", description = "Villain not found")
    public Response updateVillain(@PathParam("id") Long id, @Valid Villain updatedVillain) {
        Log.infof("Updating villain with id: %d", id);
        Villain villain = Villain.findById(id);
        if (villain == null) {
            Log.warnf("Villain with id %d not found", id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        villain.name = updatedVillain.name;
        villain.description = updatedVillain.description;
        villain.level = updatedVillain.level;
        villain.strength = updatedVillain.strength;
        villain.agility = updatedVillain.agility;
        villain.intelligence = updatedVillain.intelligence;
        villain.imageUrl = updatedVillain.imageUrl;
        villain.evilPlan = updatedVillain.evilPlan;
        
        villain.persist();
        return Response.ok(villain).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete a villain")
    @APIResponse(responseCode = "204", description = "Villain deleted")
    @APIResponse(responseCode = "404", description = "Villain not found")
    public Response deleteVillain(@PathParam("id") Long id) {
        Log.infof("Deleting villain with id: %d", id);
        boolean deleted = Villain.deleteById(id);
        if (!deleted) {
            Log.warnf("Villain with id %d not found", id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }

    @GET
    @Path("/random")
    @Operation(summary = "Get a random villain")
    @APIResponse(responseCode = "200", description = "Random villain", content = @Content(schema = @Schema(implementation = Villain.class)))
    public Response getRandomVillain() {
        Log.info("Getting random villain");
        Villain villain = Villain.findRandom();
        if (villain == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(villain).build();
    }
}
