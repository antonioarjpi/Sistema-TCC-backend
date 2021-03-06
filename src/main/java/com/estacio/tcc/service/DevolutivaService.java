package com.estacio.tcc.service;

import com.estacio.tcc.dto.DevolutivaDTO;
import com.estacio.tcc.dto.DevolutivaPostDTO;
import com.estacio.tcc.model.AcompanhamentoOrientacao;
import com.estacio.tcc.model.Orientacao;
import com.estacio.tcc.repository.AcompanhamentoOrientacaoRepository;
import com.estacio.tcc.repository.OrientacaoRepository;
import com.estacio.tcc.service.exceptions.ObjectNotFoundException;
import com.estacio.tcc.service.exceptions.RuleOfBusinessException;
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
public class DevolutivaService {

    private AcompanhamentoOrientacaoRepository repository;
    private OrientacaoService orientacaoService;
    private OrientacaoRepository orientacaoRepository;
    private ModelMapper modelMapper;

    public AcompanhamentoOrientacao encontrarId(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Devolutiva não encontrada"));
    }

    public DevolutivaDTO encontrarIdDTO(Long id) {
        AcompanhamentoOrientacao acompanhamento = encontrarId(id);
        return entidadeParaDTO(acompanhamento);
    }

    public List<DevolutivaDTO> lista(AcompanhamentoOrientacao acompanhamento) {
        Example<AcompanhamentoOrientacao> example = Example.of(acompanhamento, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return repository.findAll(example)
                .stream()
                .map(x -> entidadeParaDTO(x))
                .collect(Collectors.toList());
    }

    public Page<DevolutivaDTO> listaPageada(AcompanhamentoOrientacao acompanhamento, Pageable pageable) {
        Example<AcompanhamentoOrientacao> example = Example.of(acompanhamento, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        Page<AcompanhamentoOrientacao> acompanhamentosDTO = repository.findAll(example, pageable);
        return acompanhamentosDTO.map(x -> entidadeParaDTO(x));
    }

    @Transactional
    public AcompanhamentoOrientacao salvar(DevolutivaPostDTO dto) {
        Orientacao orientacao = orientacaoService.encontrarId(dto.getOrientacaoId());
        if (orientacao.getAcompanhamentoOrientacao() != null) {
            throw new RuleOfBusinessException("Orientação já possui acompanhamentos");
        }
        AcompanhamentoOrientacao acompanhamento = dtoParaEntidade(dto);
        acompanhamento.setOrientacao(orientacao);
        orientacao.setAcompanhamentoOrientacao(acompanhamento);
        acompanhamento = repository.save(acompanhamento);
        orientacaoRepository.save(orientacao);
        return acompanhamento;
    }

    @Transactional
    public void deletar(AcompanhamentoOrientacao acompanhamentoOrientacao) {
        Objects.requireNonNull(acompanhamentoOrientacao.getId());
        Orientacao orientacao = orientacaoService.encontrarId(acompanhamentoOrientacao.getOrientacao().getId());
        orientacao.setAcompanhamentoOrientacao(null);
        orientacaoRepository.save(orientacao);
        repository.delete(acompanhamentoOrientacao);
    }

    @Transactional
    public AcompanhamentoOrientacao atualizar(DevolutivaPostDTO dto) {
        AcompanhamentoOrientacao novoAcompanhamento = dtoParaEntidade(dto);
        AcompanhamentoOrientacao acompanhamento = encontrarId(novoAcompanhamento.getId());

        Orientacao orientacao = orientacaoService.encontrarId(dto.getOrientacaoId());
        if (!acompanhamento.getOrientacao().getId().equals(dto.getOrientacaoId())) {
            throw new RuleOfBusinessException("Orientação não pode ser alterada!");
        }

        novoAcompanhamento.setOrientacao(orientacao);
        attDevolutiva(acompanhamento, novoAcompanhamento);
        return repository.save(acompanhamento);
    }

    private void attDevolutiva(AcompanhamentoOrientacao nova, AcompanhamentoOrientacao acompanhamento) {
        Long idLocalCorrecao = nova.getDevolutiva().getLocalCorrecao().getId();
        nova.setStatusOrientacao(acompanhamento.getStatusOrientacao());
        nova.setDataMudanca(acompanhamento.getDataMudanca());
        nova.setDevolutiva(acompanhamento.getDevolutiva());
        nova.getDevolutiva().setLocalCorrecao(acompanhamento.getDevolutiva().getLocalCorrecao());
        nova.setOrientacao(acompanhamento.getOrientacao());
        nova.getDevolutiva().getLocalCorrecao().setId(idLocalCorrecao);
    }

    public DevolutivaDTO entidadeParaDTO(AcompanhamentoOrientacao acompanhamentoOrientacao) {
        return modelMapper.map(acompanhamentoOrientacao, DevolutivaDTO.class);
    }

    public AcompanhamentoOrientacao dtoParaEntidade(DevolutivaPostDTO dto) {
        return modelMapper.map(dto, AcompanhamentoOrientacao.class);
    }
}
