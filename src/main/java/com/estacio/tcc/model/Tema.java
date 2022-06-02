package com.estacio.tcc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Tema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 5, max = 255, message = "Tamanho tem que ser entre 5 a 255 caracteres")
    private String delimitacao;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "linha_pesquisa_id")
    private LinhaPesquisa linhaPesquisa;
}
