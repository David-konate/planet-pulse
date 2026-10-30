// STEP 2
// Receiving entry data and defining validations constraints
// Represent an object with the information needed to creat a note
package com.planetpulse.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

// DTO : class that take data received from frontend
// public record = handles data

public record NoteCreateRequest(

        // Country Id associate to the note
        @NotNull(message = "Please select a Country.")
        Integer countryId,


        @NotBlank(message = "Please enter a title.")
        @Size(max = 255, message = "The title cannot exceed 255 characters.")
        String noteTitle,

        LocalDate noteDate,

        @NotBlank(message = "Please enter a content.")
        @Size(max = 10000, message = "The content cannot exceed 10000 characters.")
        String noteContent

) {
    //
}
