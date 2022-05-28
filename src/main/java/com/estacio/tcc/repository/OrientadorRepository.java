package com.estacio.tcc.repository;

import com.estacio.tcc.model.Orientador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrientadorRepository extends JpaRepository<Orientador, Long> {

    Orientador findByMatricula(String matricula);

    boolean existsByMatricula(String matricula);
}