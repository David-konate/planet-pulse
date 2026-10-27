// STEP 5 interacting with DB
// Repository for accessing notes in the database
package com.planetpulse.api.repository;

import com.planetpulse.api.entity.Note;

// Provides methods to interact with de DB
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

// interface define a contract without writing the methods
// Note is the entity and LocalDateTime is his PK
public interface NoteRepository extends JpaRepository<Note, LocalDateTime> {
}
