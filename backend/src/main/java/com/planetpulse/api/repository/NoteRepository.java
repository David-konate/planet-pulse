// Access to DB
package com.planetpulse.api.repository;

import com.planetpulse.api.entity.Note;

// to communicate with DB
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

// Note is the entity and LocalDateTime is his PK
public interface NoteRepository extends JpaRepository<Note, LocalDateTime> {
}
