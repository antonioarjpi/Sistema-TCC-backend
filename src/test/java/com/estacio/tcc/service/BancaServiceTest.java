package com.estacio.tcc.service;

import com.estacio.tcc.builder.BancaBuilder;
import com.estacio.tcc.builder.DefesaBuilder;
import com.estacio.tcc.builder.EquipeBuilder;
import com.estacio.tcc.builder.OrientadorBuilder;
import com.estacio.tcc.builderDTO.BancaBuilderDTO;
import com.estacio.tcc.builderDTO.DefesaBuilderDTO;
import com.estacio.tcc.dto.BancaDTO;
import com.estacio.tcc.dto.BancaPostDTO;
import com.estacio.tcc.model.Banca;
import com.estacio.tcc.repository.BancaRepository;
import com.estacio.tcc.repository.DefesaRepository;
import com.estacio.tcc.repository.EquipeRepository;
import com.estacio.tcc.repository.OrientadorRepository;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("Banca Service")
public class BancaServiceTest {

    @SpyBean
    private BancaService service;

    @MockBean
    private BancaRepository repository;

    @MockBean
    private DefesaRepository defesaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private OrientadorRepository orientadorRepository;

    @MockBean
    private EquipeRepository equipeRepository;

    @BeforeEach
    void setUp(){
        List<Banca> bancas = new ArrayList<>(List.of(BancaBuilder.bancaValida()));

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(BancaBuilder.bancaValida()));
        BDDMockito.when(repository.findAll(ArgumentMatchers.any(Example.class)))
                .thenReturn(bancas);
        BDDMockito.when(repository.save(ArgumentMatchers.any(Banca.class)))
                .thenReturn(BancaBuilder.bancaValida());
        BDDMockito.when(defesaRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(DefesaBuilder.defesaValida()));

        BDDMockito.when(orientadorRepository.findByMatricula(ArgumentMatchers.anyString()))
                .thenReturn(OrientadorBuilder.orientadorValido());
        BDDMockito.when(equipeRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(EquipeBuilder.equipeValida()));
    }

    @Test
    @DisplayName("Lista filtrada quando sucesso")
    void listaFiltrada_RetornaListaDeAlunos_QuandoSucesso(){
        Banca bancas = BancaBuilder.bancaValida();

        List<BancaDTO> list = service.lista(bancas);

        assertThat(list).isNotNull();

        assertThat(list).isNotEmpty();

        assertThat(list.get(0).getDescricao()).isEqualTo(bancas.getDescricao());

    }

    @Test
    @DisplayName("EncontraID quando sucesso")
    void encontraId_RetornaBanca_QuandoSucesso() {
        Long id = BancaBuilder.bancaValida().getId();

        Banca resultado = service.encontrarId(id);

        assertThat(resultado).isNotNull();

        assertThat(resultado.getId()).isNotNull().isEqualTo(id);
    }

    @Test
    @DisplayName("EncontraID quando falhar")
    void encontraId_RetornaErroDeNaoEncontrado_QuandoFalhar() {
        Long id = BancaBuilder.bancaValida().getId();

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        catchThrowableOfType(() -> service.encontrarId(id), ObjectNotFoundException.class);
    }

    @Test
    @DisplayName("EncontraID quando sucesso")
    void encontraIdDTO_RetornaAluno_QuandoSucesso() {
        Banca equipe = BancaBuilder.bancaValida();

        BancaDTO resultado = service.encontrarIdDTO(equipe.getId());

        assertThat(resultado).isNotNull();

        assertThat(resultado.getId()).isNotNull().isEqualTo(equipe.getId());
    }

    @Test
    @DisplayName("EncontraID quando falhar")
    void encontraIdDTO_RetornaErroDeNaoEncontrado_QuandoFalhar() {
        Long id = BancaBuilder.bancaValida().getId();

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        catchThrowableOfType(() -> service.encontrarIdDTO(id), ObjectNotFoundException.class);
    }

    @Test
    @DisplayName("Salva equipe quando sucesso")
    void salva_SalvaBanca_QuandoSucesso(){
        Banca salvar = service.salvar(BancaBuilderDTO.criaBancaDTO());

        assertThat(salvar).isNotNull().isEqualTo(BancaBuilder.bancaValida());
    }

    @Test
    @DisplayName("Atualiza equipe quando sucesso")
    void atualiza_SalvaBancaAtualizada_QuandoSucesso(){
        BancaPostDTO dto = BancaBuilderDTO.atualizaBancaPostDTO();

        Banca banca = service.dtoParaEntidade(dto);

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(banca));

        service.atualizar(dto);

        Mockito.verify(repository, Mockito.times(1)).save(banca);
    }

    @Test
    @DisplayName("atualiza retorna erro quando nao achar ID")
    void atualiza_RetornaErroQuandoNaoAcharId_QuandoFalhar() {
        BancaPostDTO dto = BancaBuilderDTO.atualizaBancaPostDTO();

        catchThrowableOfType(() -> service.atualizar(dto), ObjectNotFoundException.class);
    }

    @Test
    @DisplayName("Deleta Banca quando sucesso")
    void deleta_DeletaBanca_QuandoSucesso(){
        Banca banca = BancaBuilder.bancaValida();

        service.deletar(banca.getId());

        Mockito.verify(repository).delete(banca);
    }

    @Test
    @DisplayName("deleta de equipe quando der erro")
    void deleta_ErroAoDeletar_QuandoFalhar(){
        Banca banca = BancaBuilder.criaBanca();

        catchThrowableOfType(() -> service.deletar(banca.getId()), ObjectNotFoundException.class);

        Mockito.verify(repository, Mockito.never()).delete(banca);
    }

    @Test
    @DisplayName("AgendamentoDefesa Quando for sucesso")
    void agendamentoDefesa_SalvaDefesa_QuandoSucesso(){
        Banca banca = BancaBuilder.bancaValida();

        Banca agendamento = service.agendamentoDefesa(banca.getId(), DefesaBuilderDTO.criaDefesa());

        Mockito.verify(repository, Mockito.times(1)).save(agendamento);
    }


}
