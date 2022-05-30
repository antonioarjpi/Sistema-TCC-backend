package com.estacio.tcc.config;

import com.estacio.tcc.dto.EquipeDTO;
import com.estacio.tcc.model.Equipe;
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
                .<String>addMapping(src -> src.getTema().getDelimitacao(), (x, value) -> x.setTema(value))
                .<String>addMapping(src -> src.getTema().getLinhaPesquisa().getDescricao(), (x, value) -> x.setLinhaPesquisa(value))
                .<String>addMapping(src -> src.getTema().getLinhaPesquisa().getAreaConhecimento().getDescricao(), (x, value) -> x.setConhecimento(value))
                .<List>addMapping(src -> src.getAlunos(), (x, value) -> x.getAlunos())

        ;

        return modelMapper;
    }
}
