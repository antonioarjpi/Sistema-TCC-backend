package com.estacio.tcc.service;

import com.estacio.tcc.dto.BancaDTO;
import com.estacio.tcc.dto.BancaPostDTO;
import com.estacio.tcc.model.Banca;
import com.estacio.tcc.model.Equipe;
import com.estacio.tcc.model.MembroBanca;
import com.estacio.tcc.model.Orientador;
import com.estacio.tcc.repository.BancaRepository;
import com.estacio.tcc.repository.MembroBancaRepository;
import com.estacio.tcc.service.exceptions.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BancaService {

    private BancaRepository repository;
    private OrientadorService orientadorService;
    private EquipeService equipeService;
    private MembroBancaRepository membroBancaRepository;

    public Banca findById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Banca não localizada"));
    }

    @Transactional
    public Banca save(BancaPostDTO dto){
        Banca banca = modelToDto(dto);

        Orientador orientador = orientadorService.findByMatricula(dto.getMatriculaOrientador());
        banca.setOrientador(orientador);

        Equipe equipe = equipeService.search(dto.getEquipe());
        banca.setEquipe(equipe);

        banca = repository.save(banca);
        return banca;
    }

    @Transactional
    public void delete(Banca banca){
        Objects.requireNonNull(banca.getId());
        repository.delete(banca);
        membroBancaRepository.delete(banca.getMembroBanca());
    }

    public List<BancaDTO> list(){
        return repository.findAll()
                .stream()
                .map(x -> dtoToModel(x))
                .collect(Collectors.toList());
    }

    public Banca modelToDto(BancaPostDTO dto){
        Banca banca = new Banca();
        banca.setDescricao(dto.getDescricao());
        banca.setDataBanca(dto.getDataBanca());
        banca.setOrdemApresentacao(dto.getOrdemApresentacao());
        banca.setMembroBanca(MembroBanca
                .builder()
                .matricula(dto.getMembroMatricula())
                .build());
        return banca;
    }

    public BancaDTO dtoToModel(Banca banca){
        BancaDTO dto = new BancaDTO();
        dto.setId(banca.getId());
        dto.setDescricao(banca.getDescricao());
        dto.setDataBanca(banca.getDataBanca());
        dto.setOrdemDeApresentacao(banca.getOrdemApresentacao());
        dto.setNomeOrientador(banca.getOrientador().getNome());
        dto.setNomeEquipe(banca.getEquipe().getNome());
        dto.setTamanhoEquipe(banca.getEquipe().getQuantidade());
        dto.setDataEquipe(banca.getEquipe().getDataCadastro());
        dto.setIntegrantes(banca.getEquipe().getAlunos()
                        .stream()
                        .map(aluno -> aluno.getNome())
                        .collect(Collectors.toList()));
        dto.setMembroBanca(banca.getMembroBanca().getMatricula());
        return dto;

    }

}
