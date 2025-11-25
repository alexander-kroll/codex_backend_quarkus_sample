package com.codex.upload;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@ApplicationScoped
public class ImageUploadService {

    @ConfigProperty(name = "quarkus.http.body.uploads-directory", defaultValue = "/tmp/uploads")
    String uploadDirectory;

    public String uploadImage(InputStream inputStream, String fileName) throws IOException {
        Log.infof("Uploading image: %s", fileName);
        
        // Create uploads directory if it doesn't exist
        File uploadDir = new File(uploadDirectory);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
            Log.infof("Created upload directory: %s", uploadDirectory);
        }

        // Generate unique filename to avoid collisions
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i);
        }
        
        String uniqueFileName = UUID.randomUUID().toString() + extension;
        Path filePath = Paths.get(uploadDirectory, uniqueFileName);

        // Copy the uploaded file to the destination
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        
        Log.infof("Image uploaded successfully: %s", uniqueFileName);
        
        // Return the relative URL that can be used to access the image
        return "/uploads/" + uniqueFileName;
    }

    public boolean deleteImage(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return false;
        }

        try {
            // Extract filename from URL
            String fileName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
            Path filePath = Paths.get(uploadDirectory, fileName);
            
            boolean deleted = Files.deleteIfExists(filePath);
            if (deleted) {
                Log.infof("Image deleted successfully: %s", fileName);
            } else {
                Log.warnf("Image not found: %s", fileName);
            }
            return deleted;
        } catch (IOException e) {
            Log.errorf("Error deleting image: %s", e.getMessage());
            return false;
        }
    }
}
