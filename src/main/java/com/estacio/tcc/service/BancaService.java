package com.estacio.tcc.service;

import com.estacio.tcc.dto.BancaPostDTO;
import com.estacio.tcc.model.Banca;
import com.estacio.tcc.model.Equipe;
import com.estacio.tcc.model.MembroBanca;
import com.estacio.tcc.model.Orientador;
import com.estacio.tcc.repository.BancaRepository;
import com.estacio.tcc.service.exceptions.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BancaService {

    private BancaRepository repository;
    private OrientadorService orientadorService;
    private EquipeService equipeService;

    public Banca findById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Banca não localizada"));
    }

    public Banca save(BancaPostDTO dto){
        Banca banca = modelToDto(dto);

        Orientador orientador = orientadorService.findByMatricula(dto.getMatriculaOrientador());
        banca.setOrientador(orientador);

        Equipe equipe = equipeService.findById(dto.getEquipe());
        banca.setEquipe(equipe);

        banca = repository.save(banca);
        return banca;
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

}
