package com.example.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/api/imagenes/")
@Tag(name="Conversor de imágenes PNG", description="Conversión de imágenes PNG")
public class ImagenesController {

    @PostMapping("codificarPNG")
    public ResponseEntity<String> convertImageToBase64(@RequestParam("file") MultipartFile file) {
        try {
            //validar que el archivo es de tipo PNG
            if (file == null || !file.getContentType().equals("image/png")) {
                return new ResponseEntity<>("El archivo debe ser una imagen PNG.", HttpStatus.BAD_REQUEST);
            }

            //convertir la imagen a base64
            byte[] imageBytes = file.getBytes();
            String base64String = Base64.getEncoder().encodeToString(imageBytes);

            return new ResponseEntity<>(base64String, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error al procesar la imagen: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*curl -X POST -F "file=@/home/rafa/Imágenes/Capturas de pantalla/imagen.png" http://localhost:8080/api/imagenes/codificarPNG*/
    
    @PostMapping("decodificarPNG")
    @Operation(summary="Convertir Base64 a imagen PNG")
    public ResponseEntity<byte[]> convertBase64ToImage(@RequestBody String base64String) {
        try {
            String limpio = base64String.replaceAll("\n", "").replaceAll("\r", "");

            byte[] bytes = Base64.getDecoder().decode(limpio);

            return ResponseEntity.ok()
                    .header("Content-Type", "image/png")
                    .body(bytes);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(("Error: Base64 inválido - " + e.getMessage()).getBytes());
        }
    }
    
}
