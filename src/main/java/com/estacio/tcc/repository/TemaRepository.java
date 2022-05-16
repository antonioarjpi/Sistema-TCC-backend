package com.estacio.tcc.repository;

import com.estacio.tcc.model.Tema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemaRepository extends JpaRepository<Tema, Long> {
}