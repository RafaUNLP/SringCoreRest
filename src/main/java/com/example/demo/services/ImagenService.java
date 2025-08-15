package com.example.demo.services;

import de.huxhorn.sulky.ulid.ULID;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImagenService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    private final ULID ulid = new ULID();

    public String saveImagen(MultipartFile file) throws IOException {
        if (file == null | file.isEmpty()) {
            return null;
        }
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String nombre = ulid.nextULID() + "_" + file.getOriginalFilename();

        Path path = Path.of(uploadDir , nombre);

        Files.copy(file.getInputStream(), path);

        return nombre;

    }

    public Resource getImagen(String fileName) {
        try {
            Path path = Paths.get(uploadDir).resolve(fileName).normalize();
            Resource resource = new UrlResource(path.toUri());
            if (!resource.exists()){
                return null;
            }
            if ( resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e){
            throw new RuntimeException("Error: " + e.getMessage());
        }

    }

    public String getContentType(String fileName) {
        try {
            Path path = Paths.get(uploadDir).resolve(fileName).normalize();
            return Files.probeContentType(path);
        } catch (IOException e) {
            return "application/octet-stream"; // Fallback type
        }
    }


    public boolean deleteImagen(String fileName) {
        try {
            Path path = Paths.get(uploadDir).resolve(fileName).normalize();
            File file = path.toFile();
            if (file.exists()) {
                return file.delete(); // Returns true if the file was deleted successfully
            } else {
                return false; // File does not exist
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not delete the file: " + e.getMessage());
        }
    }

/*
 @Controller
public class FileUploadController {

    private final FileStorageService fileStorageService;

    @Autowired
    public FileUploadController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = fileStorageService.storeFile(file);
            return ResponseEntity.ok("File uploaded successfully: " + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
        }
    }
}
 
 
 * */
    
/*ASI USABAN EL CONTROLLER DE IMAGEN SOLO PARA GET
 @RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName){
        try {
            Resource resource = fileStorageService.getFile(fileName);
            if (resource == null) {
                return ResponseEntity.notFound().build();
            }

            // Detect content type
            String contentType = fileStorageService.getContentType(fileName);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
 */
    
   /*ASI USABAN EL SERVICE DESDE OTRO CONTROLLER
     @Autowired
    private FileStorageService fileStorageService;

    public MenuComponent create(MenuComponentAddRequest request, MultipartFile image){
        MenuComponent existingComponent = menuComponentRepository.findByName(request.getName()).orElse(null);
        if(existingComponent != null){
            throw new ResourceAlreadyExistsException("Component with name: " + request.getName() + " already exists");
        }

        MenuComponent menuComponent = new MenuComponent();
        menuComponent.setName(request.getName());
        menuComponent.setType(MenuComponentType.valueOf(request.getType()));

        try {
            menuComponent.setImageUrl(fileStorageService.storeFile(image));

        } catch (Exception e) {
            menuComponent.setImageUrl(null);
        }
        return menuComponentRepository.save(menuComponent);
    }
    * 
    * */
}