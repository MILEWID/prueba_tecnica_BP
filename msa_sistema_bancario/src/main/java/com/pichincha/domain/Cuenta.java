package com.pichincha.domain;

import jakarta.persistence.*;
import lombok.Data;
import com.pichincha.domain.enums.TipoCuenta;
import java.util.UUID;

@Entity
@Table(name = "cuenta")
@Data
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(unique = true)
    private String numeroCuenta;
    
    @Enumerated(EnumType.STRING)
    private TipoCuenta tipoCuenta;
    
    private Double saldoInicial;
    private Boolean estado;
}
