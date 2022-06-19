package com.estacio.tcc.service;

import com.estacio.tcc.builder.AlunoBuilder;
import com.estacio.tcc.builder.EquipeBuilder;
import com.estacio.tcc.builderDTO.AlunoBuilderDTO;
import com.estacio.tcc.builderDTO.EquipeBuilderDTO;
import com.estacio.tcc.dto.AlunoDTO;
import com.estacio.tcc.dto.AlunoPostDTO;
import com.estacio.tcc.dto.EquipeDTO;
import com.estacio.tcc.dto.EquipePostDTO;
import com.estacio.tcc.model.Aluno;
import com.estacio.tcc.model.Equipe;
import com.estacio.tcc.repository.AlunoRepository;
import com.estacio.tcc.repository.EquipeRepository;
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
@DisplayName("Equipe Service")
public class EquipeServiceTest {

    @SpyBean
    private EquipeService service;

    @MockBean
    private EquipeRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp(){
        List<Equipe> equipes = new ArrayList<>(List.of(EquipeBuilder.equipeValida()));

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(EquipeBuilder.equipeValida()));
        BDDMockito.when(repository.findAll(ArgumentMatchers.any(Example.class)))
                        .thenReturn(equipes);
        BDDMockito.when(repository.save(ArgumentMatchers.any(Equipe.class)))
                .thenReturn(EquipeBuilder.equipeValida());
    }

    @Test
    @DisplayName("Lista filtrada quando sucesso")
    void listaFiltrada_RetornaListaDeAlunos_QuandoSucesso(){
        Equipe equipes = EquipeBuilder.equipeValida();
        String nome = equipes.getNome();

        List<EquipeDTO> list = service.lista(equipes);

        assertThat(list).isNotNull();

        assertThat(list).isNotEmpty();

        assertThat(list.get(0).getNome()).isEqualTo(nome);
    }

    @Test
    @DisplayName("EncontraID quando sucesso")
    void encontraId_RetornaEquipe_QuandoSucesso() {
        Long id = EquipeBuilder.equipeValida().getId();

        Equipe resultado = service.encontraId(id);

        assertThat(resultado).isNotNull();

        assertThat(resultado.getId()).isNotNull().isEqualTo(id);
    }

    @Test
    @DisplayName("EncontraID quando falhar")
    void encontraId_RetornaErroDeNaoEncontrado_QuandoFalhar() {
        Long id = EquipeBuilder.equipeValida().getId();

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        catchThrowableOfType(() -> service.encontraId(id), ObjectNotFoundException.class);
    }

    @Test
    @DisplayName("EncontraID quando sucesso")
    void encontraIdDTO_RetornaAluno_QuandoSucesso() {
        Long id = EquipeBuilder.equipeValida().getId();

        EquipeDTO resultado = service.encontraIdDTO(id);

        assertThat(resultado).isNotNull();

        assertThat(resultado.getId()).isNotNull().isEqualTo(id);
    }

    @Test
    @DisplayName("EncontraID quando falhar")
    void encontraIdDTO_RetornaErroDeNaoEncontrado_QuandoFalhar() {
        Long id = EquipeBuilder.equipeValida().getId();

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        catchThrowableOfType(() -> service.encontraIdDTO(id), ObjectNotFoundException.class);
    }

    @Test
    @DisplayName("Salva equipe quando sucesso")
    void salva_SalvaEquipe_QuandoSucesso(){
        Equipe salvar = service.salvar(EquipeBuilderDTO.criaEquipeDTO());

        assertThat(salvar).isNotNull().isEqualTo(EquipeBuilder.equipeValida());
    }

    @Test
    @DisplayName("Atualiza equipe quando sucesso")
    void atualiza_SalvaEquipe_QuandoSucesso(){
        EquipePostDTO dto = EquipeBuilderDTO.atualizaEquipDTO();
        dto.setId(1l);
        dto.setNome("Nome Atualizado");
        dto.setTemaDelimitacao("atualizou");
        dto.setTemaLinhaPesquisaAreaConhecimentoDescricao("atualizou");
        dto.setTemaLinhapesquisaDescricao("atualizou");

        Equipe equipe = service.dtoParaEntidade(dto);

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(equipe));

        service.atualizar(dto);

        Mockito.verify(repository, Mockito.times(1)).save(equipe);
    }

    @Test
    @DisplayName("atualiza retorna erro quando nao achar ID")
    void atualiza_RetornaErroQuandoNaoAcharId_QuandoFalhar() {
        EquipePostDTO dto = EquipeBuilderDTO.atualizaEquipDTO();

        catchThrowableOfType(() -> service.atualizar(dto), ObjectNotFoundException.class);
    }

    @Test
    @DisplayName("Deleta Equipe quando sucesso")
    void deleta_DeletaEquipe_QuandoSucesso(){
        Equipe equipe = EquipeBuilder.equipeValida();

        service.deletar(equipe);

        Mockito.verify(repository).delete(equipe);
    }

    @Test
    @DisplayName("deleta de equipe quando der erro")
    void deleta_ErroAoDeletar_QuandoFalhar(){
        Equipe equipe = EquipeBuilder.criaEquipe();

        catchThrowableOfType(() -> service.deletar(equipe), NullPointerException.class);

        Mockito.verify(repository, Mockito.never()).delete(equipe);
    }


}
