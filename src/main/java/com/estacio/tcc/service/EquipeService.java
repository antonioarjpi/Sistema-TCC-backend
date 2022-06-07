package com.estacio.tcc.service;

import com.estacio.tcc.dto.AcompanhamentoDTO;
import com.estacio.tcc.dto.EquipeDTO;
import com.estacio.tcc.dto.EquipePostDTO;
import com.estacio.tcc.model.Aluno;
import com.estacio.tcc.model.Equipe;
import com.estacio.tcc.model.Tema;
import com.estacio.tcc.repository.*;
import com.estacio.tcc.service.exceptions.ObjectNotFoundException;
import com.estacio.tcc.service.exceptions.RuleOfBusinessException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EquipeService {

    private EquipeRepository repository;
    private AlunoService alunoService;
    private LinhaPesquisaRepository linhaPesquisaRepository;
    private TemaRepository temaRepository;
    private AreaConhecimentoRepository areaConhecimentoRepository;
    private ModelMapper modelMapper;
    private TemaService temaService;
    private AlunoRepository alunoRepository;

    public Equipe encontraId(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Equipe não encontrada."));
    }

    public EquipeDTO encontraIdDTO(Long id){
        Equipe equipe = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Equipe não encontrada."));
        return entidadeParaDTO(equipe);
    }

    @Transactional
    public Equipe salvar(EquipePostDTO dto){
        Equipe equipe = dtoParaEntidade(dto);
        List<Aluno> alunos = new ArrayList<>();

        for (Aluno a : dto.getAlunos()){
            Aluno aluno = alunoService.encontraMatricula(a.getMatricula());
            if (aluno == null) throw new ObjectNotFoundException("Aluno inexistente.");
            alunos.add(aluno);
        }

        if (alunos.isEmpty()){
            throw new ObjectNotFoundException("Não pode ter equipe sem aluno.");
        }

        equipe.setQuantidade(alunos.size());

        equipe.setAlunos(alunos);
        equipe = repository.save(equipe);

        alunoRepository.saveAll(alunos);
        return equipe;
    }

    public Equipe atualizar(EquipePostDTO dto){
        Equipe novaEquipe = dtoParaEntidade(dto);
        Equipe equipe = encontraId(novaEquipe.getId());

        Tema tema = temaService.encontraId(equipe.getTema().getId());
        tema.setDelimitacao(novaEquipe.getTema().getDelimitacao());
        tema.getLinhaPesquisa().setDescricao(novaEquipe.getTema().getLinhaPesquisa().getDescricao());
        tema.getLinhaPesquisa().getAreaConhecimento().setDescricao(dto.getTemaLinhaPesquisaAreaConhecimentoDescricao());

        equipe.setQuantidade(equipe.getAlunos().size());
        equipe.setTema(tema);

        atualizaDados(equipe, novaEquipe);
        return repository.save(equipe);
    }

    private void atualizaDados (Equipe novaEquipe, Equipe equipe){
        novaEquipe.getTema().setId(novaEquipe.getTema().getId());
        novaEquipe.setNome(equipe.getNome());
        novaEquipe.setAlunos(equipe.getAlunos());
    }

    @Transactional
    public void deletar(Equipe equipe){
        Objects.requireNonNull(equipe.getId());
        if (equipe.getOrientacao() != null){
            throw new RuleOfBusinessException("Equipe com Orientação cadastrada, exclua a orientação primeiro.");
        }
        repository.delete(equipe);
        temaRepository.delete(equipe.getTema());
        linhaPesquisaRepository.delete(equipe.getTema().getLinhaPesquisa());
        areaConhecimentoRepository.delete(equipe.getTema().getLinhaPesquisa().getAreaConhecimento());
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
    public AcompanhamentoDTO mostrarTodaEquipePeloId(Long id){
        Equipe equipe = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Equipe não encontrada."));
        return entidadeParaDTOCompleto(equipe);
    }

    public EquipeDTO entidadeParaDTO(Equipe equipe){
        return modelMapper.map(equipe, EquipeDTO.class);
    }

    public AcompanhamentoDTO entidadeParaDTOCompleto(Equipe equipe){
        return modelMapper.map(equipe, AcompanhamentoDTO.class);
    }

    public Equipe dtoParaEntidade(EquipePostDTO dto){
        return modelMapper.map(dto, Equipe.class);
    }

}