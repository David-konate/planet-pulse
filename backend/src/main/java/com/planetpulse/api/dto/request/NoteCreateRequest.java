// STEP 2
// Receiving entry data and defining validations constraints
// Represent an object with the information needed to creat a note
package com.planetpulse.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// DTO : class that take data received from frontend
public class NoteCreateRequest {

    // Country Id associate to the note
    @NotNull(message = "Please select a Country.")
    private Integer countryId;


    @NotBlank(message = "Please enter a title.")
    @Size(max = 255, message = "The title cannot exceed 255 characters.")
    private String noteTitle;


    @NotBlank(message = "Please enter a content.")
    @Size(max = 10000, message = "The content cannot exceed 10000 characters.")
    private String noteContent;

    // GETTER : allow other class to read country id
    public Integer getCountryId() {
        return countryId;
    }

    // SETTER : allow to give value to country id
    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    // GETTER :
    public String getNoteTitle() {
        return noteTitle;
    }

    // SETTER :
    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    // GETTER :
    public String getNoteContent() {
        return noteContent;
    }

    // SETTER :
    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }
}
