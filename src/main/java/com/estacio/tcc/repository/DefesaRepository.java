package com.estacio.tcc.repository;

import com.estacio.tcc.model.Defesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DefesaRepository extends JpaRepository<Defesa, Long>, JpaSpecificationExecutor<Defesa> {
}