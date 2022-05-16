package com.estacio.tcc.repository;

import com.estacio.tcc.model.Banca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BancaRepository extends JpaRepository<Banca, Long> {
}