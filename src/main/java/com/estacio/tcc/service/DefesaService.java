package com.estacio.tcc.service;

import com.estacio.tcc.dto.DefesaDTO;
import com.estacio.tcc.dto.DefesaPostDTO;
import com.estacio.tcc.model.Banca;
import com.estacio.tcc.model.Defesa;
import com.estacio.tcc.repository.DefesaRepository;
import com.estacio.tcc.service.exceptions.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DefesaService {

    private DefesaRepository repository;
    private BancaService bancaService;

    public Defesa findById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Defesa não encontrada."));
    }

    public Defesa save(DefesaPostDTO dto){
        Defesa defesa = modelToDTO(dto);
        Banca banca = bancaService.search(dto.getBanca());
        defesa.setBanca(banca);
        defesa = repository.save(defesa);
        return defesa;
    }

    @Transactional
    public void delete(Defesa defesa){
        Objects.requireNonNull(defesa.getId());
        repository.delete(defesa);
    }

    public List<DefesaDTO> list(){
        return repository.findAll()
                .stream()
                .map(x -> dtoToModel(x))
                .collect(Collectors.toList());
    }

    public Defesa modelToDTO(DefesaPostDTO dto){
        Defesa defesa = new Defesa();
        defesa.setDataDefesa(dto.getData());
        return defesa;
    }

    public DefesaDTO dtoToModel(Defesa defesa){
        DefesaDTO dto = new DefesaDTO();
        dto.setId(defesa.getId());
        dto.setDataDefesa(defesa.getDataDefesa());
        dto.setDataBanca(defesa.getBanca().getDataBanca());
        dto.setDescricao(defesa.getBanca().getDescricao());
        return dto;
    }
}