
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_notes")
@Getter
@Setter
@NoArgsConstructor
public class Note implements Serializable {

    @Id
    @Column(name = "note_timestamp", nullable = false)
    private LocalDateTime noteTimestamp;

    // allow to creat multiple notes to the same country
    // only charge teh data when i need them
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
