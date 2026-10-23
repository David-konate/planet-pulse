
package com.planetpulse.api.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// this clas represent a table 
@Entity
@Table(name = "t_notes")
@Getter
@Setter
@NoArgsConstructor

public class Note
 implements Serializable {

    // this class cannot be import as an id in an other entity
    @EmbeddedId
    private Note id;

    @MapsId("idCountry")
    // specify that a multiple notes can be relate to the same country ( do not charge if i don't need it) 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_country", nullable = false)
    private Country country;

    @NotBlank
    @Size(max = 255)
    @Column(name = "note_title", nullable = false, length = 255)
    private String noteTitle;

    @NotBlank
    @Size(max = 10000)
    @Column(name = "note_content", nullable = false, length = 10000)
    private String noteContent;
}
