package ru.itis.workproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.workproject.models.Document;

public interface DocumentsRepository extends JpaRepository<Document, Long> {
}
