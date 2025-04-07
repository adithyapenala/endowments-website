package com.telangana.EndowmentsTribunal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Setter
@Getter
@Entity
@Table(name = "documents")
public class Document {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pdfUrl;
    private String fileName;
    private String contentType;
    private String date;
    private String panel;

    // Constructors
    public Document() {}
    public Document(String pdfUrl, String date, String panel) {
        this.pdfUrl = pdfUrl;
        this.date = date;
        this.panel = panel;
    }

}
