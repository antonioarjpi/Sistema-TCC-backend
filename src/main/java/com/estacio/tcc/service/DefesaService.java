package com.estacio.tcc.service;

import com.estacio.tcc.dto.DefesaDTO;
import com.estacio.tcc.model.Banca;
import com.estacio.tcc.model.Defesa;
import com.estacio.tcc.repository.DefesaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefesaService {

    private DefesaRepository repository;
    private BancaService bancaService;

    public Defesa save(DefesaDTO dto){
        Defesa defesa = modelToDTO(dto);
        Banca banca = bancaService.findById(dto.getBanca());
        defesa.setBanca(banca);
        defesa = repository.save(defesa);
        return defesa;
    }

    public Defesa modelToDTO(DefesaDTO dto){
        Defesa defesa = new Defesa();
        defesa.setDataDefesa(dto.getData());
        return defesa;
    }
}
