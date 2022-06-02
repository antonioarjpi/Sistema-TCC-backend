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
public class LinhaPesquisa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 10, max = 5000, message = "Tamanho tem que ser entre 5 a 5000 caracteres")
    private String descricao;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "area_conhecimento_id")
    private AreaConhecimento areaConhecimento;
}
