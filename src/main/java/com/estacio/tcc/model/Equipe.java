package com.estacio.tcc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Equipe{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Integer quantidade;
    private Date dataCadastro;

    @OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL)
    private List<Aluno> alunos;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "tema_id")
    private Tema tema;

}
