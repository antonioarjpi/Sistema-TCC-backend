package com.estacio.tcc.config;

import com.estacio.tcc.dto.*;
import com.estacio.tcc.model.Equipe;
import com.estacio.tcc.model.Orientacao;
import com.estacio.tcc.model.Orientador;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        modelMapper.createTypeMap(Equipe.class, EquipeDTO.class)
                .<String>addMapping(src -> src.getTema().getDelimitacao(),
                        (x, value) -> x.setTema(value))
                .<String>addMapping(src -> src.getTema().getLinhaPesquisa().getDescricao(),
                        (x, value) -> x.setLinhaPesquisa(value))
                .<String>addMapping(src -> src.getTema().getLinhaPesquisa().getAreaConhecimento().getDescricao(),
                        (x, value) -> x.setConhecimento(value))
                .<List>addMapping(src -> src.getAlunos(), (x, value) -> x.getAlunos());

        modelMapper.createTypeMap(Orientacao.class, OrientacaoDTO.class)
                .<String>addMapping(src -> src.getEstruturaTcc().getTipoTcc().getDescricao(),
                        (x, value) -> x.getTipoTCC());

        modelMapper.createTypeMap(OrientacaoPostDTO.class, Orientacao.class)
                .<String>addMapping(src -> src.getDescricaoTCC(),
                        (x, value) -> x.getEstruturaTcc().getDescricao())
                .<String>addMapping(src -> src.getTipoTCC(),
                        (x, value) -> x.getEstruturaTcc().getTipoTcc().getDescricao());

        modelMapper.createTypeMap(EquipePostDTO.class, Equipe.class)
                .<String>addMapping(src -> src.getTemaLinhapesquisaDescricao(),
                        (x, value) -> x.getTema().getLinhaPesquisa().setDescricao(value));

        modelMapper.createTypeMap(Equipe.class, AcompanhamentoDTO.class)
                .<String>addMapping(src -> src.getOrientacao().getEstruturaTcc().getDescricao(),
                        (x, value) -> x.setEstruturaTCC(value))
                .<String>addMapping(src -> src.getOrientacao().getAcompanhamentoOrientacao().getStatusOrientacao(),
                        (x, value) -> x.setStatusOrientacao(value))
                .<String>addMapping(src -> src.getOrientacao().getAcompanhamentoOrientacao().getDataMudanca(),
                        (x, value) -> x.setDataMudanca(value))
                .<String>addMapping(src -> src.getOrientacao().getAcompanhamentoOrientacao().getDevolutiva().getDescricao(),
                        (x, value) -> x.setDevolutivaDescricao(value))
                .<String>addMapping(src -> src.getOrientacao().getAcompanhamentoOrientacao().getDevolutiva().getVersaoDoc(),
                        (x, value) -> x.setDevolutivaVersaoDoc(value))
                .<String>addMapping(src -> src.getOrientacao().getAcompanhamentoOrientacao().getDevolutiva().getLocalCorrecao().getLocal(),
                        (x, value) -> x.setDevolutivaLocalCorrecao(value))
                .<String>addMapping(src -> src.getOrientacao().getAcompanhamentoOrientacao().getDevolutiva().getLocalCorrecao().getCorrecaoSugerida(),
                        (x, value) -> x.setDevolutivaCorrecaoSugerida(value))
                .<String>addMapping(src -> src.getOrientacao().getEstruturaTcc().getTipoTcc().getDescricao(),
                        (x, value) -> x.setTipoTCC(value));


        modelMapper.createTypeMap(Orientador.class, OrientadorDTO.class)
                .<String>addMapping(src -> src.getLinhaPesquisa().getAreaConhecimento().getDescricao(),
                        (x, value) -> x.setAreaConhecimento(value));

        return modelMapper;
    }
}
