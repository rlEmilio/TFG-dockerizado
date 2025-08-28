package com.tfg.koeshiru.services;

import com.tfg.koeshiru.entities.Usuario;
import com.tfg.koeshiru.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Service
public class ImageService {

    private final Path imagesFolder = Paths.get("images");

    @Autowired
    private UsuarioRepository usuarioRepository;

    public String saveImageForUser(MultipartFile file, Long userId) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Empty file");
        }

        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Borrar imagen anterior si existe
        if (usuario.getImagen() != null) {
            String previousFileName = Paths.get(usuario.getImagen()).getFileName().toString();
            Path previousImagePath = imagesFolder.resolve(previousFileName);
            try {
                Files.deleteIfExists(previousImagePath);
            } catch (IOException e) {
                // log warning pero no detener el proceso
                System.err.println("Could not delete old image: " + e.getMessage());
            }
        }

        if (!Files.exists(imagesFolder)) {
            Files.createDirectories(imagesFolder);
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        Path destinationFile = imagesFolder.resolve(fileName);

        Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);

        String imageUrl = "/images/" + fileName;
        usuario.setImagen(imageUrl); // aquí mantengo el campo 'imagen' porque la entidad lo tiene así
        usuarioRepository.save(usuario);

        return imageUrl;
    }
}