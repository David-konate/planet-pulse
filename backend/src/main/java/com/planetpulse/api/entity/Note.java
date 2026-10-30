// this is the representation Java of the note in the DB
package com.planetpulse.api.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_notes")

public class Note {

    @Id
    @Column(name = "note_timestamp", nullable = false)
    private LocalDateTime noteTimestamp;

    @Column(name = "id_country")
    private Integer countryId;

    @Column(name = "note_title")
    private String noteTitle;

    @Column(name = "note_date")
    private LocalDate noteDate;

    @Column(name = "note_content")
    private String noteContent;

    // Required by JPA
    public Note() {
    }

    public LocalDateTime getNoteTimestamp() {
        return noteTimestamp;
    }

    public void setNoteTimestamp(LocalDateTime noteTimestamp) {
        this.noteTimestamp = noteTimestamp;
    }


    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }


    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public LocalDate getNoteDate() { return noteDate; }

    public void setNoteDate(LocalDate noteDate) { this.noteDate = noteDate; }


    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }
}
