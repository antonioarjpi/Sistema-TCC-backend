package com.estacio.tcc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String senha;

    private String imagem;

    @Column(unique = true)
    private String matricula;

    @JsonIgnore
    @ManyToMany(mappedBy = "alunos")
    private List<Equipe> equipe = new ArrayList<>();


}
