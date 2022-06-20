package com.estacio.tcc.service;

import com.estacio.tcc.builder.EquipeBuilder;
import com.estacio.tcc.builderDTO.EquipeBuilderDTO;
import com.estacio.tcc.dto.AcompanhamentoDTO;
import com.estacio.tcc.dto.EquipeDTO;
import com.estacio.tcc.dto.EquipePostDTO;
import com.estacio.tcc.model.Equipe;
import com.estacio.tcc.repository.EquipeRepository;
import com.estacio.tcc.service.exceptions.ObjectNotFoundException;
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
        Long quantidade = Long.valueOf(equipes.getQuantidade());

        List<EquipeDTO> list = service.lista(equipes);

        assertThat(list).isNotNull();

        assertThat(list).isNotEmpty();

        assertThat(list.get(0).getNome()).isEqualTo(equipes.getNome());

        assertThat(list.get(0).getQuantidade()).isEqualTo(quantidade);

        assertThat(list.get(0).getDataCadastro()).isEqualTo(equipes.getDataCadastro());

        assertThat(list.get(0).getTema()).isEqualTo(equipes.getTema().getDelimitacao());

        assertThat(list.get(0).getConhecimento()).isEqualTo(equipes.getTema().getLinhaPesquisa().getAreaConhecimento().getDescricao());

        assertThat(list.get(0).getLinhaPesquisa()).isEqualTo(equipes.getTema().getLinhaPesquisa().getDescricao());

        assertThat(list.get(0).getOrientacaoId()).isEqualTo(equipes.getOrientacao().getId());

        assertThat(list.get(0).getAlunos()).isEqualTo(equipes.getAlunos());

    }

    @Test
    @DisplayName("mostrarTodaEquipePeloId quando sucesso")
    void mostrarTodaEquipePeloId_RetornaEquipeComDevolutivasAcompanhamentosEstruturaDeTCC_QuandoSucesso() {
        Long id = EquipeBuilder.equipeValida().getId();

        AcompanhamentoDTO resultado = service.mostrarTodaEquipePeloId(id);

        assertThat(resultado).isNotNull();

        assertThat(resultado.getId()).isNotNull().isEqualTo(id);
    }

    @Test
    @DisplayName("mostrarTodaEquipePeloId quando falhar")
    void mostrarTodaEquipePeloId_RetornaErroDeNaoEncontrado_QuandoFalhar() {
        Long id = EquipeBuilder.equipeValida().getId();

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        catchThrowableOfType(() -> service.mostrarTodaEquipePeloId(id), ObjectNotFoundException.class);
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
        Equipe equipe = EquipeBuilder.equipeValida();

        EquipeDTO resultado = service.encontraIdDTO(equipe.getId());

        assertThat(resultado).isNotNull();

        assertThat(resultado.getId()).isNotNull().isEqualTo(equipe.getId());

        assertThat(resultado.getTema()).isEqualTo(equipe.getTema().getDelimitacao());

        assertThat(resultado.getConhecimento()).isEqualTo(equipe.getTema().getLinhaPesquisa().getAreaConhecimento().getDescricao());

        assertThat(resultado.getLinhaPesquisa()).isEqualTo(equipe.getTema().getLinhaPesquisa().getDescricao());

        assertThat(resultado.getDataCadastro()).isEqualTo(equipe.getDataCadastro());

        assertThat(resultado.getOrientacaoId()).isEqualTo(equipe.getOrientacao().getId());

        assertThat(resultado.getAlunos()).isEqualTo(equipe.getAlunos());
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
    void atualiza_SalvaEquipeAtualizada_QuandoSucesso(){
        EquipePostDTO dto = EquipeBuilderDTO.atualizaEquipeDTO();

        Equipe equipe = service.dtoParaEntidade(dto);

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(equipe));

        service.atualizar(dto);

        Mockito.verify(repository, Mockito.times(1)).save(equipe);
    }

    @Test
    @DisplayName("atualiza retorna erro quando nao achar ID")
    void atualiza_RetornaErroQuandoNaoAcharId_QuandoFalhar() {
        EquipePostDTO dto = EquipeBuilderDTO.atualizaEquipeDTO();

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
