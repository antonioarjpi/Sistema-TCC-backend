package com.estacio.tcc.service;

import com.estacio.tcc.dto.BancaDTO;
import com.estacio.tcc.dto.BancaPostDTO;
import com.estacio.tcc.dto.DefesaPostDTO;
import com.estacio.tcc.model.Banca;
import com.estacio.tcc.model.Defesa;
import com.estacio.tcc.model.Equipe;
import com.estacio.tcc.model.Orientador;
import com.estacio.tcc.repository.*;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BancaService {

    private BancaRepository repository;
    private OrientadorRepository orientadorRepository;
    private EquipeRepository equipeRepository;
    private MembroRepository membroRepository;
    private DefesaRepository defesaRepository;
    private ModelMapper modelMapper;

    public BancaDTO encontrarIdDTO(Long id) {
        Banca banca = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Banca não encontrada"));
        return entidadeParaDTO(banca);
    }

    public Banca encontrarId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Banca não encontrada"));
    }

    @Transactional
    public Banca salvar(BancaPostDTO dto) {
        Banca banca = dtoParaEntidade(dto);
        buscaOrientadorEquipe(dto, banca);
        return repository.save(banca);
    }

    private void buscaOrientadorEquipe(BancaPostDTO dto, Banca banca) {
        Orientador orientador = orientadorRepository.findByMatricula(dto.getMatriculaOrientador());
        banca.setOrientador(orientador);
        Equipe equipe = equipeRepository.findById(dto.getEquipe())
                .orElseThrow(() -> new ObjectNotFoundException("Equipe não localizada"));
        banca.setEquipe(equipe);
    }

    @Transactional
    public void deletar(Long id) {
        Banca banca = encontrarId(id);
        repository.delete(banca);
    }

    public List<BancaDTO> lista(Banca banca) {
        Example example = Example.of(banca, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return (List<BancaDTO>) repository.findAll(example)
                .stream()
                .map(x -> entidadeParaDTO((Banca) x))
                .collect(Collectors.toList());
    }

    public Page<Banca> listaPageada(Banca banca, Pageable pageable) {
        Example example = Example.of(banca, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return repository.findAll(example, pageable);
    }

    @Transactional
    public Banca atualizar(BancaPostDTO dto) {
        Banca banca = dtoParaEntidade(dto);
        Banca novaBanca = encontrarId(banca.getId());

        buscaOrientadorEquipe(dto, banca);

        atualizaDados(novaBanca, banca);
        return repository.save(banca);
    }

    private void atualizaDados(Banca novaBanca, Banca banca) {
        //Guarda id de membro
        Long idMembro = novaBanca.getMembro().getId();
        //Bancas
        novaBanca.setId(novaBanca.getId());
        novaBanca.setDataBanca(banca.getDataBanca());
        novaBanca.setDescricao(banca.getDescricao());
        novaBanca.setOrdemApresentacao(banca.getOrdemApresentacao());
        //Membros da bancas
        novaBanca.setMembro(banca.getMembro());
        novaBanca.getMembro().setId(idMembro);
        //Defesa
        banca.setDefesa(novaBanca.getDefesa());
    }

    @Transactional
    public Banca agendamentoDefesa(Long id, DefesaPostDTO dto) {
        Banca banca = encontrarId(id);
        if (banca.getDefesa() == null) {
            Defesa defesa = new Defesa();
            defesa.setDataDefesa(dto.getData());
            banca.setDefesa(defesa);
        } else {
            Optional<Defesa> defesa = defesaRepository.findById(banca.getDefesa().getId());
            defesa.get().setId(defesa.get().getId());
            defesa.get().setDataDefesa(dto.getData());
            banca.setDefesa(defesa.get());
        }
        return repository.save(banca);
    }

    public Banca dtoParaEntidade(BancaPostDTO dto) {
        return modelMapper.map(dto, Banca.class);
    }

    public BancaDTO entidadeParaDTO(Banca banca) {
        return modelMapper.map(banca, BancaDTO.class);
    }

}
