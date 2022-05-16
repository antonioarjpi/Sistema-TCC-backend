package com.estacio.tcc.service;

import com.estacio.tcc.dto.EquipeDTO;
import com.estacio.tcc.model.*;
import com.estacio.tcc.repository.AlunoRepository;
import com.estacio.tcc.repository.EquipeRepository;
import com.estacio.tcc.service.exceptions.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EquipeService {

    private EquipeRepository repository;
    private AlunoService alunoService;

    private AlunoRepository alunoRepository;

    public Equipe findById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Equipe não encontrada."));
    }

    @Transactional
    public Equipe save(EquipeDTO dto){
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

        equipe.setAlunos(alunos);
        equipe = repository.save(equipe);

        for (Aluno aluno : alunos){
            aluno.setEquipe(equipe);
        }

        alunoRepository.saveAll(alunos);
        return equipe;
    }

    public Equipe modelToDto(EquipeDTO dto){
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
