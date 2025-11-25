package com.codex.ai;

import com.codex.hero.Hero;
import com.codex.villain.Villain;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/api/ai")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "AI Generator", description = "Creative description generation API")
public class DescriptionGeneratorResource {

    @Inject
    DescriptionGeneratorService generatorService;

    @POST
    @Path("/hero/description")
    @Operation(summary = "Generate a creative description for a hero")
    public Response generateHeroDescription(DescriptionRequest request) {
        Log.infof("Generating hero description for: %s", request.name);
        String description = generatorService.generateHeroDescription(request.name);
        return Response.ok(new DescriptionResponse(description)).build();
    }

    @POST
    @Path("/villain/description")
    @Operation(summary = "Generate a creative description for a villain")
    public Response generateVillainDescription(DescriptionRequest request) {
        Log.infof("Generating villain description for: %s", request.name);
        String description = generatorService.generateVillainDescription(request.name);
        return Response.ok(new DescriptionResponse(description)).build();
    }

    @GET
    @Path("/hero/special-power")
    @Operation(summary = "Generate a random special power for a hero")
    public Response generateSpecialPower() {
        Log.info("Generating special power");
        String power = generatorService.generateSpecialPower();
        return Response.ok(new PowerResponse(power)).build();
    }

    @GET
    @Path("/villain/evil-plan")
    @Operation(summary = "Generate a random evil plan for a villain")
    public Response generateEvilPlan() {
        Log.info("Generating evil plan");
        String plan = generatorService.generateEvilPlan();
        return Response.ok(new PlanResponse(plan)).build();
    }

    public static class DescriptionRequest {
        public String name;
    }

    public static class DescriptionResponse {
        public String description;

        public DescriptionResponse(String description) {
            this.description = description;
        }
    }

    public static class PowerResponse {
        public String power;

        public PowerResponse(String power) {
            this.power = power;
        }
    }

    public static class PlanResponse {
        public String plan;

        public PlanResponse(String plan) {
            this.plan = plan;
        }
    }
}
