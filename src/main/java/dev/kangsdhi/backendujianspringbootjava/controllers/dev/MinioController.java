package dev.kangsdhi.backendujianspringbootjava.controllers.dev;

import dev.kangsdhi.backendujianspringbootjava.services.MinioDevService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/dev/minio")
public class MinioController {

    @Autowired
    private MinioDevService minioDevService;

    @GetMapping("/test")
    public ResponseEntity<String> testConnection(){
        String result = minioDevService.testConnection();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file){
        try {
            String filename = minioDevService.uploadFile(file);
            return ResponseEntity.ok("File upload successfully: " + filename);
        } catch (Exception e){
            return ResponseEntity.status(500).body("Failed to upload file: " + e.getMessage());
        }
    }

    @GetMapping("/view/{filename}")
    public ResponseEntity<String> viewFile(@PathVariable String filename){
        try {
            String url = minioDevService.getFileUrl(filename);
            return ResponseEntity.ok("File URL: " + url);
        }catch (Exception e){
            return ResponseEntity.status(500).body("Failed to get file URL: " + e.getMessage());
        }
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename){
        try {
            Resource resource = minioDevService.downloadFile(filename);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(resource);
        } catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }
}
