// Received data
package com.planetpulse.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NoteCreateRequest {

    @NotNull(message = "A country must be selected.")
    private Integer countryId;

    @NotBlank(message = "The note title cannot be empty.")
    @Size(max = 255, message = "The title cannot exceed 255 characters.")
    private String noteTitle;

    @NotBlank(message = "The note content cannot be empty.")
    @Size(max = 10000, message = "The content cannot exceed 10000 characters.")
    private String noteContent;
}
