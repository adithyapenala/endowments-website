package com.telangana.EndowmentsTribunal;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {
    @Autowired
    private DocumentService service;

    @GetMapping
    public List<Document> getAllDocuments() {
        return service.getAllDocuments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> download(@PathVariable Long id) {
        return (ResponseEntity<Resource>) service.get(id)
                .map(doc -> {
                    Path path = Paths.get(doc.getPdfUrl()); // assuming getPdfUrl() = getFilePath()
                    try {
                        Resource resource = new UrlResource(path.toUri());
                        if (resource.exists()) {
                            return ResponseEntity.ok()
                                    .contentType(MediaType.parseMediaType(doc.getContentType()))
                                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + doc.getFileName() + "\"")
                                    .body(resource);
                        } else {
                            return ResponseEntity.notFound().build();
                        }
                    } catch (MalformedURLException e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).<Resource>build();
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Document>> search(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String panel) {
        List<Document> results = service.searchByDateAndPanel(date, panel);
        return ResponseEntity.ok(results);
    }
    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok("Deleted successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deletion failed.");
        }
    }

    @PostMapping
    public ResponseEntity<String> upload(
            @ModelAttribute Document metaData,
            @RequestParam("file") MultipartFile file) {

        try {
            Document doc = service.saveDocument(file, metaData.getDate(), metaData.getPanel());
            return ResponseEntity.ok("Uploaded with ID: " + doc.getId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed.");
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Document> updateDocument(@PathVariable Long id, @RequestBody Document updatedDocument) {
        return service.updateDocument(id, updatedDocument)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}