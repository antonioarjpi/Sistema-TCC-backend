package com.estacio.tcc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Devolutiva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 5, max = 255, message = "Tamanho tem que ser entre 5 a 255 caracteres")
    private String descricao;

    @NotBlank
    @Size(min = 1, max = 5, message = "Tamanho tem que ser entre 1 a 5 caracteres")
    private String versaoDoc;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "orientacao_id")
    private Orientacao orientacao;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "local_correcao_id")
    private LocalCorrecao localCorrecao;

}
