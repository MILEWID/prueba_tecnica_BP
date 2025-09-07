package com.pichincha.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Entity
@Table(name = "persona")
@Data
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nombre;
    private String genero;
    private Integer edad;
    @Column(unique = true)
    private String identificacion;
    private String direccion;
    private String telefono;
}
