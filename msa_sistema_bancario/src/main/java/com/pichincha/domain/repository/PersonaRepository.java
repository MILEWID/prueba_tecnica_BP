package com.pichincha.domain.repository;

import com.pichincha.domain.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
    boolean existsByIdentificacion(String identificacion);
}
