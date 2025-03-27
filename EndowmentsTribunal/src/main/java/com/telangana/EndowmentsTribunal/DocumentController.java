package com.telangana.EndowmentsTribunal;

import com.telangana.EndowmentsTribunal.Document;
import com.telangana.EndowmentsTribunal.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Document> getDocumentById(@PathVariable Long id) {
        return service.getDocumentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Document> createDocument(@RequestBody Document document) {
        return ResponseEntity.ok(service.saveDocument(document));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Document> updateDocument(@PathVariable Long id, @RequestBody Document updatedDocument) {
        return service.updateDocument(id, updatedDocument)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        return service.deleteDocument(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}