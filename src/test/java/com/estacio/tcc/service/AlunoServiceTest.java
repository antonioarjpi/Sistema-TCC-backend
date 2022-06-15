package com.estacio.tcc.service;

import com.estacio.tcc.builder.AlunoBuilder;
import com.estacio.tcc.builderDTO.AlunoBuilderDTO;
import com.estacio.tcc.dto.AlunoDTO;
import com.estacio.tcc.dto.AlunoPostDTO;
import com.estacio.tcc.model.Aluno;
import com.estacio.tcc.repository.AlunoRepository;
import com.estacio.tcc.service.exceptions.ObjectNotFoundException;
import com.estacio.tcc.service.exceptions.RuleOfBusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("Aluno Service")
public class AlunoServiceTest {

    @SpyBean
    private AlunoService service;

    @MockBean
    private AlunoRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp(){
        List<Aluno> alunos = new ArrayList<>(List.of(AlunoBuilder.alunoValido()));

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(AlunoBuilder.alunoValido()));
        BDDMockito.when(repository.findAll(ArgumentMatchers.any(Example.class)))
                        .thenReturn(alunos);
        BDDMockito.when(repository.save(ArgumentMatchers.any(Aluno.class)))
                .thenReturn(AlunoBuilder.alunoValido());
        BDDMockito.when(repository.findByMatricula(ArgumentMatchers.anyString()))
                .thenReturn(AlunoBuilder.alunoValido());
        BDDMockito.when(repository.findByEmail(ArgumentMatchers.anyString()))
                .thenReturn(AlunoBuilder.alunoValido());
    }

    @Test
    @DisplayName("Lista filtrada")
    void listaFiltrada_RetornaListaDeAlunos_QuandoSucesso(){
        Aluno alunos = AlunoBuilder.alunoValido();
        String nome = alunos.getNome();

        List<AlunoDTO> list = service.listaFiltrada(alunos);

        assertThat(list).isNotNull();

        assertThat(list).isNotEmpty();

        assertThat(list.get(0).getNome()).isEqualTo(nome);
    }

    @Test
    @DisplayName("EncontraID quando sucesso")
    void encontraId_RetornaAluno_QuandoSucesso() {
        Long id = AlunoBuilder.alunoValido().getId();;

        Aluno resultado = service.encontraId(id);

        assertThat(resultado).isNotNull();

        assertThat(resultado.getId()).isNotNull().isEqualTo(id);
    }

    @Test
    @DisplayName("EncontraID quando falhar")
    void encontraId_RetornaErroDeNaoEncontrado_QuandoFalhar() {
        Long id = AlunoBuilder.alunoValido().getId();;

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        catchThrowableOfType(() -> service.encontraId(id), ObjectNotFoundException.class);
    }

    @Test
    @DisplayName("EncontraID quando sucesso")
    void encontraIdDTO_RetornaAluno_QuandoSucesso() {
        Long id = AlunoBuilder.alunoValido().getId();;

        AlunoDTO resultado = service.encontrarIdDTO(id);

        assertThat(resultado).isNotNull();

        assertThat(resultado.getId()).isNotNull().isEqualTo(id);
    }

    @Test
    @DisplayName("EncontraID quando falhar")
    void encontraIdDTO_RetornaErroDeNaoEncontrado_QuandoFalhar() {
        Long id = AlunoBuilder.alunoValido().getId();;

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        catchThrowableOfType(() -> service.encontrarIdDTO(id), ObjectNotFoundException.class);
    }

    @Test
    @DisplayName("Salva aluno quando sucesso")
    void salva_SalvaAluno_QuandoSucesso(){
        Aluno salvar = service.salvar(AlunoBuilderDTO.criaAlunoDTO());

        assertThat(salvar).isNotNull().isEqualTo(AlunoBuilder.alunoValido());
    }

    @Test
    @DisplayName("Atualiza aluno quando sucesso")
    void atualiza_SalvaAluno_QuandoSucesso(){
        AlunoPostDTO dto = AlunoBuilderDTO.alunoValidoDTO();
        dto.setEmail("emailatualizad@email.com");
        dto.setNome("Nome Atualizado");

        Aluno aluno = service.dtoParaEntidade(dto);

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(aluno));

        service.atualiza(dto);

        Mockito.verify(repository, Mockito.times(1)).save(aluno);
    }

    @Test
    @DisplayName("atualiza retorna erro quando nao achar ID")
    void atualiza_RetornaErroQuandoNaoAcharId_QuandoFalhar() {
        AlunoPostDTO dto = AlunoBuilderDTO.alunoValidoDTO();

        catchThrowableOfType(() -> service.atualiza(dto), ObjectNotFoundException.class);
    }

    @Test
    @DisplayName("atualiza retorna erro quando email for diferente e já estiver sido cadastrado por outro")
    void atualiza_RetornaErroQuandoEmailForDiferenteEjaEstiverSidoCadastrado_QuandoFalhar() {
        AlunoPostDTO dto = AlunoBuilderDTO.alunoValidoDTO();
        dto.setEmail("email@email.com");

        BDDMockito.when(repository.saveAll(ArgumentMatchers.any()))
                .thenReturn(Arrays.asList(AlunoBuilder.alunoValido(), AlunoBuilder.alunoValido2()));

        catchThrowableOfType(() -> service.atualiza(dto), RuleOfBusinessException.class);
    }

    @Test
    @DisplayName("Deleta Aluno quando sucesso")
    void deleta_DeletaAluno_QuandoSucesso(){
        Aluno aluno = AlunoBuilder.alunoValido();

        service.deletar(aluno);

        Mockito.verify(repository).delete(aluno);
    }

    @Test
    @DisplayName("deleta de aluno da erro")
    void deleta_ErroAoDeletar_QuandoFalhar(){
        Aluno aluno = AlunoBuilder.criaAluno();

        catchThrowableOfType(() -> service.deletar(aluno), NullPointerException.class);

        Mockito.verify(repository, Mockito.never()).delete(aluno);
    }

    @Test
    @DisplayName("Encontra Matrícula quando sucesso")
    void encontraMatricula_RetornaAluno_QuandoSucesso(){
        String matricula = AlunoBuilder.alunoValido().getMatricula();

        Aluno aluno = service.encontraMatricula(matricula);

        assertThat(aluno).isNotNull().isEqualTo(AlunoBuilder.alunoValido());
    }

    @Test
    @DisplayName("Encontra Matrícula quando falhar")
    void encontraMatricula_RetornaMatriculaNaoEncontrada_QuandoFalhar(){
        catchThrowableOfType(() -> service.encontraMatricula("Inexistente"), ObjectNotFoundException.class);
    }

    @Test
    @DisplayName("Encontra Matrícula quando sucesso")
    void encontraEmail_RetornaAluno_QuandoSucesso(){
        String email = AlunoBuilder.alunoValido().getEmail();

        Aluno aluno = service.encontraEmail(email);

        assertThat(aluno).isNotNull().isEqualTo(AlunoBuilder.alunoValido());
    }

    @Test
    @DisplayName("Encontra Matrícula quando falhar")
    void encontraEmail_RetornaMatriculaNaoEncontrada_QuandoFalhar(){
        catchThrowableOfType(() -> service.encontraMatricula("Inexistente"), ObjectNotFoundException.class);
    }

    @Test
    void validaEmail_DeveValidarEmail_QuandoSucesso() {
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);

        service.validaEmail("email@email.com");
    }

    @Test
    void validaEmail_DeveRetornarErro_QuandoJaExistirEmailCadastrado() {
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);

        org.junit.jupiter.api.Assertions
                .assertThrows(RuleOfBusinessException.class, () -> service.validaEmail("email@email.com"));
    }

    @Test
    void dtoParaEntidade(){
        AlunoPostDTO dto = AlunoBuilderDTO.criaAlunoDTO();
        Aluno map = modelMapper.map(dto, Aluno.class);

        assertThat(map.getEmail()).isEqualTo(dto.getEmail());
        assertThat(map.getSenha()).isEqualTo(dto.getSenha());
        assertThat(map.getNome()).isEqualTo(dto.getNome());
    }

    @Test
    void entidadeParaDTO(){
        Aluno aluno = AlunoBuilder.alunoValido();
        AlunoDTO map = modelMapper.map(aluno, AlunoDTO.class);

        assertThat(map.getId()).isEqualTo(aluno.getId());
        assertThat(map.getNome()).isEqualTo(aluno.getNome());
        assertThat(map.getEmail()).isEqualTo(aluno.getEmail());
        assertThat(map.getImagem()).isEqualTo(aluno.getImagem());
        assertThat(map.getMatricula()).isEqualTo(aluno.getMatricula());
    }
}
