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
            // Validar que el archivo es de tipo PNG
            if (file == null || !file.getContentType().equals("image/png")) {
                return new ResponseEntity<>("El archivo debe ser una imagen PNG.", HttpStatus.BAD_REQUEST);
            }

            // Convertir la imagen a Base64
            byte[] imageBytes = file.getBytes();
            String base64String = Base64.getEncoder().encodeToString(imageBytes);

            // Devolver el resultado
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
    
    /*
     curl -X POST -F "file=@/home/rafa/Imágenes/Capturas de pantalla/imagen.png" http://localhost:8080/api/imagenes/codificarPNG >> base64

     curl -X POST http://localhost:8080/api/imagenes/decodificarPNG \
     -H "Content-Type: text/plain" \
     -d @base64 --output output_image.png
     
     * */
    
//    @PostMapping("byteTo64")
//    @Operation(summary="Convertir un arreglo de bytes a Base64")
//    public ResponseEntity<String> convertBytesToBase64(@RequestParam byte[] bytes) {
//        try {
//            if (bytes == null || bytes.length == 0) {
//                return new ResponseEntity<>("Se recibió un arreglo vació", HttpStatus.BAD_REQUEST);
//            }
//
//            String base64String = Base64.getEncoder().encodeToString(bytes);
//
//            return new ResponseEntity<>(base64String, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Error al procesar los bytes: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    /*curl -X POST -F "file=@/home/rafa/Imágenes/Capturas de pantalla/imagen.png" http://localhost:8080/api/imagenes/codificarPNG*/
//    
//    @PostMapping("base64ToByte")
//    @Operation(summary="Convertir de base64 a un arreglo de bytes")
//    public ResponseEntity<byte[]> convertBase64ToBytes(@RequestBody String base64String) {
//        try {
//            String limpio = base64String.replaceAll("\n", "").replaceAll("\r", "");
//
//            byte[] bytes = Base64.getDecoder().decode(limpio);
//
//            return ResponseEntity.ok().body(bytes);
//            
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(("Error: Base64 inválido - " + e.getMessage()).getBytes());
//        }
//    }
    
}
