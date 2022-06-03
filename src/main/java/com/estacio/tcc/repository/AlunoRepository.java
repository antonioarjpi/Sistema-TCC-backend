package com.estacio.tcc.repository;

import com.estacio.tcc.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    Aluno findByMatricula(String matricula);

    boolean existsByMatricula(String matricula);

    boolean existsByEmail(String email);

    Aluno findByEmail(String matricula);

}