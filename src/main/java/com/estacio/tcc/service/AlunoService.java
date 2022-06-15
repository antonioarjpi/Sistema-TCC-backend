package com.estacio.tcc.service;

import com.estacio.tcc.dto.AlunoDTO;
import com.estacio.tcc.dto.AlunoPostDTO;
import com.estacio.tcc.model.Aluno;
import com.estacio.tcc.repository.AlunoRepository;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AlunoService {

    private BCryptPasswordEncoder passwordEncoder;
    private AlunoRepository repository;
    private S3Service s3Service;
    private ModelMapper modelMapper;

    public Aluno encontraId(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Aluno não encontrado"));
    }

    public AlunoDTO encontrarIdDTO(Long id){
        Aluno aluno = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Aluno não encontrado"));
        return entidadeParaDTO(aluno);
    }


    @Transactional(readOnly = true)
    public List<AlunoDTO> listaFiltrada(Aluno aluno) {
        Example example = Example.of(aluno, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return (List<AlunoDTO>) repository.findAll(example)
                .stream()
                .map(x -> entidadeParaDTO((Aluno) x))
                .collect(Collectors.toList());
    }

    @Transactional
    public Aluno salvar(AlunoPostDTO dto){
        criptografarSenha(dto);
        Aluno aluno = dtoParaEntidade(dto);
        validaEmail(aluno.getEmail());
        String matricula = matriculaValidada();
        aluno.setMatricula(matricula);
        return repository.save(aluno);
    }

    private void criptografarSenha(AlunoPostDTO dto) {
        String encode = passwordEncoder.encode(dto.getSenha());
        dto.setSenha(encode);
    }

    @Transactional
    public void deletar(Aluno aluno){
        Objects.requireNonNull(aluno.getId());
        repository.delete(aluno);
    }

    @Transactional
    public Aluno atualiza(AlunoPostDTO dto){
        Aluno aluno = dtoParaEntidade(dto);
        Aluno achou = encontraId(aluno.getId());
        if (!aluno.getEmail().equals(achou.getEmail())){
            validaEmail(aluno.getEmail());
        }
        atualizaDados(achou, aluno);
        aluno.setImagem(achou.getImagem());
        aluno.setMatricula(achou.getMatricula());
        aluno.setSenha(achou.getSenha());
        return repository.save(aluno);
    }

    @Transactional
    public Aluno encontraMatricula(String matricula){
        Aluno aluno = repository.findByMatricula(matricula);
        if (aluno == null){
            throw new ObjectNotFoundException("Matricula inexistente");
        }
        return aluno;
    }

    @Transactional
    public Aluno encontraEmail(String email){
        Aluno aluno = repository.findByEmail(email);
        if (aluno == null){
            throw new ObjectNotFoundException("E-mail inexistente");
        }
        return aluno;
    }

    @Transactional
    public URI uploadFotoPerfil(MultipartFile file, Long id){
        URI uri = s3Service.uploadFile(file);
        Aluno aluno = encontraId(id);
        aluno.setId(aluno.getId());
        aluno.setImagem(uri.toString());
        repository.save(aluno);
        return uri;
    }

    private String gerarMatricula(){
        Random random = new Random();
        LocalDate dateTime = LocalDate.now();

        String matricula = String.valueOf(dateTime.getYear()); //captura o ano

        for (int i=0; i<4; i++){ //Gera 4 digitos aleatórios do final da matrícula
            String valueRandom = String.valueOf(random.nextInt(9));
            matricula +=  valueRandom;
        }
        return matricula;
    }

    private String matriculaValidada(){
        String matricula = gerarMatricula();
        boolean exists = repository.existsByMatricula(matricula);
        while (exists){
            matriculaValidada();
        }
        return matricula;
    }

    public void validaEmail(String email) {
        boolean exist = repository.existsByEmail(email);
        if (exist){
            throw new RuleOfBusinessException("E-mail já está cadastrado por outro aluno.");
        }
    }

    private void atualizaDados(Aluno novoAluno, Aluno aluno){
        novoAluno.setNome(aluno.getNome());
        novoAluno.setEmail(aluno.getEmail());
    }

    public Aluno dtoParaEntidade(AlunoPostDTO dto){
        return modelMapper.map(dto, Aluno.class);
    }

    public AlunoDTO entidadeParaDTO(Aluno aluno){
        return modelMapper.map(aluno, AlunoDTO.class);
    }

}
