package com.telangana.EndowmentsTribunal;

import com.telangana.EndowmentsTribunal.Document;
import com.telangana.EndowmentsTribunal.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DocumentService {
    @Autowired
    private DocumentRepository repository;

    private static final String UPLOAD_DIR = "uploads";

    public List<Document> getAllDocuments() {
        return repository.findAll();
    }

    public List<Document> searchByDateAndPanel(String date, String panel) {
        if ((date == null || date.isBlank()) && (panel == null || panel.isBlank())) {
            return repository.findAll(); // fallback: return everything
        }

        return repository.search(
                date == null || date.isBlank() ? null : date,
                panel == null || panel.isBlank() ? null : panel
        );
    }


    public Document saveDocument(MultipartFile file, String date, String panel) throws IOException {
        Document doc = new Document();
        doc.setDate(date);
        doc.setPanel(panel);
        doc.setFileName(file.getOriginalFilename());
        String contentType = file.getContentType();
        if (contentType == null || contentType.isEmpty()) {
            contentType = "application/pdf";
        }
        doc.setContentType(contentType);

        Document savedDoc = repository.save(doc);

        // Use ID as filename
        String extension = Objects.requireNonNull(file.getOriginalFilename())
                .substring(file.getOriginalFilename().lastIndexOf('.'));
        String fileName = savedDoc.getId() + extension;
        Path filePath = Paths.get(UPLOAD_DIR, fileName);

        Files.createDirectories(filePath.getParent());
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        savedDoc.setPdfUrl(filePath.toString());
        return repository.save(savedDoc);
    }

    public Optional<Document> updateDocument(Long id, Document updatedDocument) {
        return repository.findById(id).map(existingDocument -> {
            existingDocument.setPdfUrl(updatedDocument.getPdfUrl());
            existingDocument.setDate(updatedDocument.getDate());
            existingDocument.setPanel(updatedDocument.getPanel());
            return repository.save(existingDocument);
        });
    }

    public Optional<Document> get(Long id) {
        return repository.findById(id);
    }

    public void delete(Long id) throws IOException {
        Optional<Document> optionalDoc = repository.findById(id);
        if (optionalDoc.isPresent()) {
            Document doc = optionalDoc.get();
            Path path = Paths.get(doc.getPdfUrl());
            Files.deleteIfExists(path);
            repository.deleteById(id);
        }
    }
}