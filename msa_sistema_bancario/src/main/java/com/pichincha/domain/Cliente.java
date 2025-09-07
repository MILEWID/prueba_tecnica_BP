package com.pichincha.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "cliente")
@EqualsAndHashCode(callSuper = false)
@Data
public class Cliente extends Persona {
    @Column(unique = true)
    private String clienteId;
    private String contrasena;
    private Boolean estado;
}
