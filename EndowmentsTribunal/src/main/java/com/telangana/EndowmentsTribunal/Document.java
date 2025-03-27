package com.telangana.EndowmentsTribunal;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pdfUrl;
    private LocalDate date;
    private String panel;

    // Constructors
    public Document() {}
    public Document(String pdfUrl, LocalDate date, String panel) {
        this.pdfUrl = pdfUrl;
        this.date = date;
        this.panel = panel;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPdfUrl() { return pdfUrl; }
    public void setPdfUrl(String pdfUrl) { this.pdfUrl = pdfUrl; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getPanel() { return panel; }
    public void setPanel(String panel) { this.panel = panel; }
}
