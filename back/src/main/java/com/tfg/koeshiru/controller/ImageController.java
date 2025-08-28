package com.tfg.koeshiru.controller;

import com.tfg.koeshiru.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload/{userId}")
    public ResponseEntity<String> uploadImageForUser(@RequestParam("file") MultipartFile file, @PathVariable Long userId) {
        try {
            String imageUrl = imageService.saveImageForUser(file, userId);
            return ResponseEntity.ok(imageUrl);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error uploading image: " + e.getMessage());
        }
    }
}