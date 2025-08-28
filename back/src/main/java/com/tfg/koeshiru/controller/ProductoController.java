package com.tfg.koeshiru.controller;

import com.tfg.koeshiru.entities.Producto;
import com.tfg.koeshiru.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("api/productos")
@CrossOrigin
public class ProductoController {

    @Autowired
    private ProductoService service;

    @GetMapping
    public ResponseEntity<?> list() {
        try {
            return ResponseEntity.ok(service.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("Error", "Error al obtener los productos: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> showById(@PathVariable Long id) {
        try {
            Optional<Producto> productoOptional = service.findById(id);
            if (productoOptional.isPresent()) {
                return ResponseEntity.ok(productoOptional.get());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Error", "El producto no se encontró por el ID"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("Error", "Error al buscar el producto: " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Producto producto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(producto));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("Error", "Error al crear el producto: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Producto producto) {
        try {
            Optional<Producto> productoOptional = service.findById(id);
            if (productoOptional.isPresent()) {
                Producto productoBD = productoOptional.get();
                productoBD.setNombre(producto.getNombre());
                productoBD.setEnlace(producto.getEnlace());
                productoBD.setImagen(producto.getImagen());

                return ResponseEntity.ok(service.save(productoBD));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Error", "Producto no encontrado"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("Error", "Error al actualizar el producto: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            Optional<Producto> productoOptional = service.findById(id);
            if (productoOptional.isPresent()) {
                service.deleteById(id);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Error", "Producto no encontrado"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("Error", "Error al eliminar el producto: " + e.getMessage()));
        }
    }
}
