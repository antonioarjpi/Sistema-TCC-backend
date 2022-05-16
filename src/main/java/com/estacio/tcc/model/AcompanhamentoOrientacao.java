package com.estacio.tcc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private String statusOrientacao;
    private Date dataMudanca;

    @ManyToOne
    @JoinColumn(name = "orientacao_id")
    private Orientacao orientacao;

    @ManyToOne
    @JoinColumn(name = "devolutiva_id")
    private Devolutiva devolutiva;

}
