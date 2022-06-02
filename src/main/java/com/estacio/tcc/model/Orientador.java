package com.estacio.tcc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Orientador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 5, max = 255, message = "Tamanho tem que ser entre 5 a 255 caracteres")
    private String nome;

    @NotBlank
    private String matricula;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 16, message = "Tamanho tem que ser entre 6 a 16 caracteres")
    private String senha;

    @NotBlank
    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "titulacao_id")
    private Titulacao titulacao;

    @NotBlank
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "linha_pesquisa_id")
    private LinhaPesquisa linhaPesquisa;

}