package com.estacio.tcc.service;

import com.estacio.tcc.builder.OrientadorBuilder;
import com.estacio.tcc.builderDTO.OrientadorBuilderDTO;
import com.estacio.tcc.dto.OrientadorDTO;
import com.estacio.tcc.dto.OrientadorPostDTO;
import com.estacio.tcc.model.Orientador;
import com.estacio.tcc.repository.OrientadorRepository;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("Orientador Service")
class OrientadorServiceTest {

    @SpyBean
    private OrientadorService service;

    @MockBean
    private OrientadorRepository repository;

    @Autowired
    private ModelMapper modelMapper;


    @BeforeEach
    void setUp(){
        ArrayList<Orientador> orientadores = new ArrayList<>(List.of(OrientadorBuilder.orientadorValido()));

        BDDMockito.when(repository.findAll(ArgumentMatchers.any(Example.class)))
                .thenReturn(orientadores);

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(OrientadorBuilder.orientadorValido()));

        BDDMockito.when(repository.save(ArgumentMatchers.any(Orientador.class)))
                .thenReturn(OrientadorBuilder.orientadorValido());

        BDDMockito.when(repository.findByMatricula(ArgumentMatchers.anyString()))
                .thenReturn(OrientadorBuilder.orientadorValido());

    }

    @Test
    @DisplayName("Lista filtrada")
    void listaFiltrada_RetornaListaDeOrientadores_QuandoSucesso(){
        Orientador orientador = OrientadorBuilder.orientadorValido();
        String nome = orientador.getNome();

        List<OrientadorDTO> list = service.lista(orientador);

        assertThat(list).isNotNull();

        assertThat(list).isNotEmpty();

        assertThat(list.get(0).getNome()).isEqualTo(nome);
    }

    @Test
    @DisplayName("EncontraID quando sucesso")
    void encontraId_RetornaOrientador_QuandoSucesso() {
        Long id = OrientadorBuilder.orientadorValido().getId();;

        Orientador resultado = service.encontraId(id);

        assertThat(resultado).isNotNull();

        assertThat(resultado.getId()).isNotNull().isEqualTo(id);
    }

    @Test
    @DisplayName("EncontraID quando falhar")
    void encontraId_RetornaErroDeNaoEncontrado_QuandoFalhar() {
        Long id = OrientadorBuilder.orientadorValido().getId();;

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        catchThrowableOfType(() -> service.encontraId(id), ObjectNotFoundException.class);
    }

    @Test
    @DisplayName("Salva orientador quando sucesso")
    void salva_SalvaOrientador_QuandoSucesso(){
        Orientador salvar = service.salvar(OrientadorBuilderDTO.criaOrientadorPostDTO());

        assertThat(salvar).isNotNull().isEqualTo(OrientadorBuilder.orientadorValido());
    }

    @Test
    @DisplayName("Atualiza Orientador quando sucesso")
    void atualiza_AtualizaOrientador_QuandoSucesso(){
        OrientadorPostDTO dto = OrientadorBuilderDTO.orientadorValidoPostDTO();
        dto.setEmail("emailatualizad@email.com");
        dto.setNome("Nome Atualizado");
        dto.setLinhaPesquisaAreaconhecimentoDescricao("Linha Atualizado");

        Orientador orientador = service.dtoParaEntidade(dto);

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(orientador));

        service.atualiza(dto);

        Mockito.verify(repository, Mockito.times(1)).save(orientador);
    }

    @Test
    @DisplayName("atualiza retorna erro quando nao achar ID")
    void atualiza_RetornaErroQuandoNaoAcharId_QuandoFalhar() {
        OrientadorPostDTO dto = OrientadorBuilderDTO.orientadorValidoPostDTO();

        catchThrowableOfType(() -> service.atualiza(dto), ObjectNotFoundException.class);
    }

    @Test
    @DisplayName("atualiza retorna erro quando email for diferente e já estiver sido cadastrado por outro")
    void atualiza_RetornaErroQuandoEmailForDiferenteEjaEstiverSidoCadastrado_QuandoFalhar() {
        OrientadorPostDTO dto = OrientadorBuilderDTO.orientadorValidoPostDTO();

        dto.setEmail("email@email.com");

        BDDMockito.when(repository.saveAll(ArgumentMatchers.any()))
                .thenReturn(Arrays.asList(OrientadorBuilder.orientadorValido(), OrientadorBuilder.orientadorValido()));

        catchThrowableOfType(() -> service.atualiza(dto), RuleOfBusinessException.class);
    }

    @Test
    @DisplayName("Deleta Orientador quando sucesso")
    void deleta_DeletaOrientador_QuandoSucesso(){
        Orientador orientador = OrientadorBuilder.orientadorValido();

        service.delete(orientador);

        Mockito.verify(repository).delete(orientador);
    }

    @Test
    @DisplayName("deleta de Orientador da erro")
    void deleta_ErroAoDeletar_QuandoFalhar(){
        Orientador orientador = OrientadorBuilder.criaOrientador();

        catchThrowableOfType(() -> service.delete(orientador), NullPointerException.class);

        Mockito.verify(repository, Mockito.never()).delete(orientador);
    }

    @Test
    @DisplayName("EncontraMatrícula quando sucesso")
    void encontraMatricula_RetornaOrientador_QuandoSucesso(){
        String matricula = OrientadorBuilder.orientadorValido().getMatricula();

        Orientador orientador = service.encontraMatricula(matricula);

        assertThat(orientador).isNotNull().isEqualTo(OrientadorBuilder.orientadorValido());
    }

    @Test
    @DisplayName("EncontraMatrícula quando falhar")
    void encontraMatricula_RetornaMatriculaNaoEncontrada_QuandoFalhar(){
        BDDMockito.when(repository.findByMatricula(ArgumentMatchers.anyString()))
                .thenReturn(null);
        catchThrowableOfType(() -> service.encontraMatricula("Resultado para falhar"), ObjectNotFoundException.class);
    }

    @Test
    @DisplayName("Valida E-mail quando for sucesso")
    void validaEmail_DeveValidarEmail_QuandoSucesso() {
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);

        service.validaEmail("email@email.com");
    }

    @Test
    @DisplayName("Valida Email quando falhar")
    void validaEmail_DeveRetornarErro_QuandoJaExistirEmailCadastrado() {
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);

        org.junit.jupiter.api.Assertions
                .assertThrows(RuleOfBusinessException.class, () -> service.validaEmail("email@email.com"));
    }

}