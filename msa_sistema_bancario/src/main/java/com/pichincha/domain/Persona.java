package com.pichincha.domain;

import jakarta.persistence.*;
import lombok.Data;
import com.pichincha.domain.enums.Genero;
import java.util.UUID;

@Entity
@Table(name = "persona")
@Data
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nombre;
    
    @Enumerated(EnumType.STRING)
    private Genero genero;
    
    private Integer edad;
    @Column(unique = true)
    private String identificacion;
    private String direccion;
    private String telefono;
}
