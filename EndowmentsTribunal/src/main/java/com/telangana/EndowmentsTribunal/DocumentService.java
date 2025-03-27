package com.telangana.EndowmentsTribunal;

import com.telangana.EndowmentsTribunal.Document;
import com.telangana.EndowmentsTribunal.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {
    @Autowired
    private DocumentRepository repository;

    public List<Document> getAllDocuments() {
        return repository.findAll();
    }

    public Optional<Document> getDocumentById(Long id) {
        return repository.findById(id);
    }

    public Document saveDocument(Document document) {
        return repository.save(document);
    }

    public Optional<Document> updateDocument(Long id, Document updatedDocument) {
        return repository.findById(id).map(existingDocument -> {
            existingDocument.setPdfUrl(updatedDocument.getPdfUrl());
            existingDocument.setDate(updatedDocument.getDate());
            existingDocument.setPanel(updatedDocument.getPanel());
            return repository.save(existingDocument);
        });
    }

    public boolean deleteDocument(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}