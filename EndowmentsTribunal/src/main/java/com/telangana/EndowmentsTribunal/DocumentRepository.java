package com.telangana.EndowmentsTribunal;

import com.telangana.EndowmentsTribunal.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query("SELECT p FROM Document p WHERE " +
            "(:date IS NULL OR p.date = :date) AND " +
            "(:panel IS NULL OR p.panel = :panel)")
    List<Document> search(@Param("date") String date,@Param("panel") String panel);
}