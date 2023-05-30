package com.example.podcast.repos;

import com.example.podcast.entidades.UserRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserRecord, Long> {
    Optional<UserRecord> findByDocument(String document);
    Optional<UserRecord> findByDocumentOrEmail(String document, String email);

}
