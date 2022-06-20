package com.estacio.tcc.builderDTO;

import com.estacio.tcc.builder.EquipeBuilder;
import com.estacio.tcc.builder.OrientadorBuilder;
import com.estacio.tcc.dto.BancaDTO;
import com.estacio.tcc.dto.BancaPostDTO;

import java.time.LocalDate;

public class BancaBuilderDTO {

    public static BancaPostDTO criaBancaDTO(){
        return BancaPostDTO.builder()
                .id(null)
                .descricao("Descricao")
                .ordemApresentacao(1)
                .membroMatricula("Antonio")
                .dataBanca(LocalDate.of(2022, 6, 19))
                .matriculaOrientador("matricula")
                .equipe(EquipeBuilder.equipeValida().getId())
                .build();
    }

    public static BancaPostDTO atualizaBancaPostDTO(){
        return BancaPostDTO.builder()
                .id(1l)
                .descricao("Atualizou descricao")
                .ordemApresentacao(2)
                .membroMatricula("Antonio")
                .dataBanca(LocalDate.of(2021, 5, 20))
                .matriculaOrientador(OrientadorBuilder.orientadorValido().getMatricula())
                .equipe(EquipeBuilder.equipeValida().getId())
                .build();
    }

    public static BancaDTO bancaDTO(){
        return BancaDTO.builder()
                .id(null)
                .descricao("Descricao")
                .ordemApresentacao(1)
                .membroMatricula("Antonio")
                .dataBanca(LocalDate.of(2022, 6, 19))
                .defesaDataDefesa(LocalDate.of(2021, 5, 20))
                .equipeDataCadastro(EquipeBuilder.equipeValida().getDataCadastro())
                .equipeQuantidade(EquipeBuilder.equipeValida().getQuantidade())
                .orientadorMatricula(OrientadorBuilder.orientadorValido().getMatricula())
                .orientadorNome(OrientadorBuilder.orientadorValido().getNome())
                .build();
    }
}
