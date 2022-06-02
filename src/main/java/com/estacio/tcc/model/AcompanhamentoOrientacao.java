package com.estacio.tcc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AcompanhamentoOrientacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 5, max = 255, message = "Tamanho tem que ser entre 5 a 255 caracteres")
    private String statusOrientacao;

    @NotBlank
    private Date dataMudanca;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "orientacao_id")
    private Orientacao orientacao;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "devolutiva_id")
    private Devolutiva devolutiva;

}
