package com.estacio.tcc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Orientador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String matricula;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String senha;
    private String imagem;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "titulacao_id")
    private Titulacao titulacao;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "linha_pesquisa_id")
    private LinhaPesquisa linhaPesquisa;

}