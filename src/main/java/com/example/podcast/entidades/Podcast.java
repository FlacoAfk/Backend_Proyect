package com.example.podcast.entidades;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Podcast {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long clientId;
    private String title;
    private String description;
    private String file_name;
    private byte[] archivo;
    private LocalDateTime fechaCreacion;

}
