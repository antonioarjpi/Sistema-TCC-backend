package com.estacio.tcc.repository;

import com.estacio.tcc.model.Membro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembroRepository extends JpaRepository<Membro, Long> {
}