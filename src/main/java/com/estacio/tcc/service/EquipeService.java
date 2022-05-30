package com.estacio.tcc.service;

import com.estacio.tcc.dto.EquipeDTO;
import com.estacio.tcc.dto.EquipePostDTO;
import com.estacio.tcc.model.*;
import com.estacio.tcc.repository.*;
import com.estacio.tcc.service.exceptions.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
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

    private AlunoRepository alunoRepository;

    public Equipe search(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Equipe não encontrada."));
    }

    public EquipeDTO findById(Long id){
        Equipe equipe = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Equipe não encontrada."));
        return dtoToEntiy(equipe);
    }

    @Transactional
    public Equipe save(EquipePostDTO dto){
        Equipe equipe = modelToDto(dto);
        List<Aluno> alunos = new ArrayList<>();

        for (String matricula : dto.getMatricula()){
            Aluno aluno = alunoService.findByMatricula(matricula);
            if (aluno == null) throw new ObjectNotFoundException("Matrícula inexistente.");
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

    @Transactional
    public void delete(Equipe equipe){
        Objects.requireNonNull(equipe.getId());
        repository.delete(equipe);
        temaRepository.delete(equipe.getTema());
        linhaPesquisaRepository.delete(equipe.getTema().getLinhaPesquisa());
        areaConhecimentoRepository.delete(equipe.getTema().getLinhaPesquisa().getAreaConhecimento());
    }

    public List<EquipeDTO> list(){
        return repository.findAll()
                .stream()
                .map(x -> dtoToEntiy(x))
                .collect(Collectors.toList());
    }

    public EquipeDTO dtoToEntiy(Equipe equipe){
        return modelMapper.map(equipe, EquipeDTO.class);
    }

    public Equipe modelToDto(EquipePostDTO dto){
        AreaConhecimento area = new AreaConhecimento(null, dto.getDescricaoConhecimento());
        LinhaPesquisa linha = new LinhaPesquisa(null, dto.getDescricaoLinha(), area );
        Tema tema = new Tema(null, dto.getDelimitacao(), linha);

        Equipe equipe = new Equipe();
        equipe.setNome(dto.getNome());
        equipe.setDataCadastro(dto.getDataCadastro());
        equipe.setQuantidade(dto.getQuantidade());
        equipe.setTema(tema);
        return equipe;
    }
}