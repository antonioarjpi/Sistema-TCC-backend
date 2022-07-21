package com.estacio.tcc.service;

import com.estacio.tcc.dto.AcompanhamentoDTO;
import com.estacio.tcc.dto.EquipeDTO;
import com.estacio.tcc.dto.EquipePostDTO;
import com.estacio.tcc.model.Equipe;
import com.estacio.tcc.repository.EquipeRepository;
import com.estacio.tcc.service.exceptions.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EquipeService {

    private EquipeRepository repository;
    private ModelMapper modelMapper;

    public Equipe encontraId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Equipe não encontrada."));
    }

    public EquipeDTO encontraIdDTO(Long id) {
        Equipe equipe = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Equipe não encontrada."));
        return entidadeParaDTO(equipe);
    }

    @Transactional
    public Equipe salvar(EquipePostDTO dto) {
        Equipe equipe = dtoParaEntidade(dto);
        equipe.setQuantidade(equipe.getAlunos().size());
        equipe = repository.save(equipe);
        return equipe;
    }

    public Equipe atualizar(EquipePostDTO dto) {
        Equipe novaEquipe = dtoParaEntidade(dto);
        Equipe equipe = encontraId(novaEquipe.getId());
        atualizaDados(equipe, novaEquipe);
        return repository.save(equipe);
    }

    private void atualizaDados(Equipe novaEquipe, Equipe equipe) {
        Long idTema = novaEquipe.getTema().getId();
        Long idLinha = novaEquipe.getTema().getLinhaPesquisa().getId();
        Long idConhecimento = novaEquipe.getTema().getLinhaPesquisa().getAreaConhecimento().getId();
        novaEquipe.setTema(equipe.getTema());
        novaEquipe.setNome(equipe.getNome());
        novaEquipe.setAlunos(equipe.getAlunos());
        novaEquipe.getTema().setId(idTema);
        novaEquipe.getTema().getLinhaPesquisa().setId(idLinha);
        novaEquipe.getTema().getLinhaPesquisa().getAreaConhecimento().setId(idConhecimento);
        novaEquipe.setQuantidade(equipe.getAlunos().size());
    }

    @Transactional
    public void deletar(Equipe equipe) {
        Objects.requireNonNull(equipe.getId());
        repository.delete(equipe);
    }

    @Transactional(readOnly = true)
    public List<EquipeDTO> lista(Equipe equipe) {
        Example example = Example.of(equipe, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return (List<EquipeDTO>) repository.findAll(example)
                .stream()
                .map(x -> entidadeParaDTO((Equipe) x))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<Equipe> listaPageada(Equipe equipe, Pageable pageable) {
        Example example = Example.of(equipe, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return repository.findAll(example, pageable);
    }

    @Transactional(readOnly = true)
    public AcompanhamentoDTO mostrarTodaEquipePeloId(Long id) {
        Equipe equipe = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Equipe não encontrada."));
        return entidadeParaDTOCompleto(equipe);
    }

    public EquipeDTO entidadeParaDTO(Equipe equipe) {
        return modelMapper.map(equipe, EquipeDTO.class);
    }

    public AcompanhamentoDTO entidadeParaDTOCompleto(Equipe equipe) {
        return modelMapper.map(equipe, AcompanhamentoDTO.class);
    }

    public Equipe dtoParaEntidade(EquipePostDTO dto) {
        return modelMapper.map(dto, Equipe.class);
    }

}