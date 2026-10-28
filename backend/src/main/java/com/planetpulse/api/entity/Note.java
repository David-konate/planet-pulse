
package com.planetpulse.api.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "t_notes")
// TO DEFINE !!!!!
public class Note implements Serializable {

    @Id
    @Column(name = "note_timestamp", nullable = false)
    private LocalDateTime noteTimestamp;

    // Allows multiple notes to be associated with the same country
    // Loads country data only when needed.
   // @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "id_country")
    private Integer countryId;

    //@NotBlank
    //@Size(max = 255)
    //@Column(name = "note_title", nullable = false, length = 255)
    @Column(name = "note_title")
    private String noteTitle;

    //@NotBlank
    //@Size(max = 10000)
    //@Column(name = "note_content", nullable = false, length = 10000)
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


    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }
}
