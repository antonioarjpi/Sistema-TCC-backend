package com.estacio.tcc.service;

import com.estacio.tcc.dto.OrientadorDTO;
import com.estacio.tcc.dto.OrientadorPostDTO;
import com.estacio.tcc.model.Orientador;
import com.estacio.tcc.model.Titulacao;
import com.estacio.tcc.repository.OrientadorRepository;
import com.estacio.tcc.service.exceptions.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrientadorService {

    private OrientadorRepository repository;

    public Orientador findByMatricula(String matricula){
        try {
            return repository.findByMatricula(matricula);
        }catch (ObjectNotFoundException e){
            throw new ObjectNotFoundException("Orientador não encontrado");
        }
    }

    public List<OrientadorDTO> list(){
        return repository.findAll()
                .stream()
                .map(x -> dtoToModel(x))
                .collect(Collectors.toList());
    }

    public Orientador findById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Orientador não encontrado."));
    }

    @Transactional
    public Orientador save(OrientadorPostDTO dto){
        Orientador orientador = modelToDto(dto);
        orientador = repository.save(orientador);
        return orientador;
    }

    public OrientadorDTO dtoToModel(Orientador orientador){
        OrientadorDTO dto = new OrientadorDTO();
        dto.setNome(orientador.getNome());
        dto.setMatricula(orientador.getMatricula());
        dto.setDescricaoTitulacao(orientador.getTitulacao().getDescricao());
        dto.setGrau(orientador.getTitulacao().getGrau());
        dto.setIES(orientador.getTitulacao().getIes());
        return dto;
    }

    public Orientador modelToDto(OrientadorPostDTO dto){
        Orientador orientador = new Orientador();
        orientador.setMatricula(dto.getMatricula());
        orientador.setNome(dto.getNome());
        orientador.setSenha(dto.getSenha());

        orientador.setTitulacao(Titulacao
                .builder()
                .descricao(dto.getDescricaoTitulacao())
                .grau(dto.getGrau())
                .ies(dto.getIES())
                .build());

        return orientador;
    }
}