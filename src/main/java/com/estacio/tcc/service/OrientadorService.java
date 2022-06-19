package com.estacio.tcc.service;

import com.estacio.tcc.dto.OrientadorDTO;
import com.estacio.tcc.dto.OrientadorPostDTO;
import com.estacio.tcc.model.Orientador;
import com.estacio.tcc.repository.OrientadorRepository;
import com.estacio.tcc.repository.TitulacaoRepository;
import com.estacio.tcc.service.exceptions.ObjectNotFoundException;
import com.estacio.tcc.service.exceptions.RuleOfBusinessException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrientadorService {

    private BCryptPasswordEncoder passwordEncoder;
    private OrientadorRepository repository;
    private TitulacaoRepository titulacaoRepository;
    private ModelMapper modelMapper;
    private S3Service s3Service;

    public Orientador encontraMatricula(String matricula){
        Orientador orientador = repository.findByMatricula(matricula);
        if (orientador == null) {
            throw new ObjectNotFoundException("Orientador não encontrado");
        }
        return orientador;
    }

    public void validaEmail(String email) {
        boolean exist = repository.existsByEmail(email);
        if (exist){
            throw new RuleOfBusinessException("E-mail já está cadastrado por outro orientador.");
        }
    }

    @Transactional(readOnly = true)
    public List<OrientadorDTO> lista(Orientador orientador) {
        Example example = Example.of(orientador, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return (List<OrientadorDTO>) repository.findAll(example)
                .stream()
                .map(x -> entidadeParaDTO((Orientador) x))
                .collect(Collectors.toList());
    }

    public Orientador encontraId(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Orientador não encontrado."));
    }

    @Transactional
    public void delete(Orientador orientador){
        Objects.requireNonNull(orientador.getId());
        repository.delete(orientador);
        titulacaoRepository.delete(orientador.getTitulacao());
    }

    @Transactional
    public Orientador salvar(OrientadorPostDTO dto){
        criptografarSenha(dto);
        Orientador orientador = dtoParaEntidade(dto);
        validaEmail(orientador.getEmail());
        String matricula = matriculaValidada();
        orientador.setMatricula(matricula);
        orientador = repository.save(orientador);
        return orientador;
    }

    private void criptografarSenha(OrientadorPostDTO dto) {
        String encode = passwordEncoder.encode(dto.getSenha());
        dto.setSenha(encode);
    }

    @Transactional
    public Orientador atualiza(OrientadorPostDTO dto){
        Orientador novoOrientador = dtoParaEntidade(dto);
        Orientador orientador = encontraId(novoOrientador.getId());
        if (!novoOrientador.getEmail().equals(orientador.getEmail())){
            validaEmail(novoOrientador.getEmail());
        }
        novoOrientador.getTitulacao().setId(orientador.getTitulacao().getId());
        novoOrientador.getLinhaPesquisa().setId(orientador.getLinhaPesquisa().getId());
        novoOrientador.getLinhaPesquisa().getAreaConhecimento().setId(orientador.getLinhaPesquisa().getAreaConhecimento().getId());
        putOrientador(orientador, novoOrientador);
        return repository.save(orientador);
    }

    private void putOrientador(Orientador novo, Orientador orientador){
        novo.setNome(orientador.getNome());
        novo.setEmail(orientador.getEmail());
        novo.setLinhaPesquisa(orientador.getLinhaPesquisa());
        novo.setTitulacao(orientador.getTitulacao());
        novo.getLinhaPesquisa().setAreaConhecimento(orientador.getLinhaPesquisa().getAreaConhecimento());
    }

    public String geraMatricula(){
        Random random = new Random();
        String matricula = new String();
        for (int i=0; i<4; i++){ //Gera 4 digitos aleatórios do final da matrícula
            String valueRandom = String.valueOf(random.nextInt(9));
            matricula +=  valueRandom;
        }
        return matricula;
    }

    public String matriculaValidada(){
        String matricula = geraMatricula();
        boolean exists = repository.existsByMatricula(matricula);
        while (exists){
            matriculaValidada();
        }
        return matricula;
    }

    public URI uploadFotoPerfil(MultipartFile file, Long id){
        URI uri = s3Service.uploadFile(file);
        Orientador orientador = encontraId(id);
        orientador.setId(orientador.getId());
        orientador.setImagem(uri.toString());
        repository.save(orientador);
        return uri;
    }

    public OrientadorDTO entidadeParaDTO(Orientador orientador){
        return modelMapper.map(orientador, OrientadorDTO.class);
    }

    public Orientador dtoParaEntidade(OrientadorPostDTO dto){
        return modelMapper.map(dto, Orientador.class);
    }

}