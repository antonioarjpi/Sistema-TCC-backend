package com.estacio.tcc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AcompanhamentoOrientacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String statusOrientacao;
    private LocalDate dataMudanca;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "orientacao_id")
    private Orientacao orientacao;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "devolutiva_id")
    private Devolutiva devolutiva;

}
