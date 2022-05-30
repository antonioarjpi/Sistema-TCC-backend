package com.estacio.tcc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data @AllArgsConstructor @Builder
@NoArgsConstructor
public class Aluno{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String matricula;

    @JsonIgnore
    @ManyToMany(mappedBy = "alunos")
    private List<Equipe> equipe;


}
