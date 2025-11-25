package com.codex.upload;

import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@Path("/api/upload")
@Tag(name = "Image Upload", description = "Image upload API")
public class ImageUploadResource {

    @Inject
    ImageUploadService uploadService;

    @POST
    @Path("/image")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Upload an image")
    public Response uploadImage(@RestForm("file") FileUpload file) {
        Log.infof("Uploading image: %s", file.fileName());
        
        try {
            // Validate file type
            String contentType = file.contentType();
            if (!contentType.startsWith("image/")) {
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Only image files are allowed"))
                    .build();
            }

            // Validate file size (max 10MB)
            long fileSize = Files.size(file.filePath());
            if (fileSize > 10 * 1024 * 1024) {
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("File size must not exceed 10MB"))
                    .build();
            }

            try (InputStream inputStream = Files.newInputStream(file.filePath())) {
                String imageUrl = uploadService.uploadImage(inputStream, file.fileName());
                return Response.ok(new UploadResponse(imageUrl)).build();
            }
        } catch (IOException e) {
            Log.errorf("Error uploading image: %s", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Failed to upload image: " + e.getMessage()))
                .build();
        }
    }

    public static class UploadResponse {
        public String imageUrl;

        public UploadResponse(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }

    public static class ErrorResponse {
        public String error;

        public ErrorResponse(String error) {
            this.error = error;
        }
    }
}
