package com.jpsantana.Api;

import com.jpsantana.Api.controller.ProcessoController;
import com.jpsantana.Api.model.Processo;
import com.jpsantana.Api.model.Reu;
import com.jpsantana.Api.service.ProcessoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ProcessoControllerTest {

        private static final Long ID = 1L;
        private static final String NUMERO_PROCESSO = "12345";

        @InjectMocks
        private ProcessoController processoController;

        @Mock
        private ProcessoService processoService;

        private Processo processo;

        @BeforeEach
        void setUp() {
                MockitoAnnotations.openMocks(this);
                processo = new Processo();
                processo.setId(ID);
                processo.setNumero(NUMERO_PROCESSO);
        }

        @Test
        void criarProcesso() {
                when(processoService.save(any(Processo.class))).thenReturn(processo);

                ResponseEntity<Processo> response = processoController.create(processo);

                assertAll(
                                () -> assertEquals(HttpStatus.CREATED, response.getStatusCode()),
                                () -> assertNotNull(response.getBody()),
                                () -> assertEquals(NUMERO_PROCESSO, response.getBody().getNumero()));

                verify(processoService, times(1)).save(processo);
        }

        @Test
        void erroAoCriarProcessoDuplicado() {
                when(processoService.save(any(Processo.class)))
                                .thenThrow(new IllegalArgumentException("Processo com o mesmo número já existe."));

                ResponseEntity<Processo> response = processoController.create(processo);

                assertAll(
                                () -> assertEquals(HttpStatus.CONFLICT, response.getStatusCode()),
                                () -> assertEquals("Processo com o mesmo número já existe.", response.getBody()));

                verify(processoService, times(1)).save(processo);
        }

        @Test
        void listarProcessos() {
                when(processoService.findAll()).thenReturn(List.of(processo));

                ResponseEntity<List<Processo>> response = processoController.getAll();

                assertAll(
                                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                                () -> assertNotNull(response.getBody()),
                                () -> assertEquals(1, response.getBody().size()));

                verify(processoService, times(1)).findAll();
        }

        @Test
        void buscarProcessoPorId() {
                when(processoService.findById(ID)).thenReturn(Optional.of(processo));

                ResponseEntity<Processo> response = processoController.getById(ID);

                assertAll(
                                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                                () -> assertNotNull(response.getBody()),
                                () -> assertEquals(NUMERO_PROCESSO, response.getBody().getNumero()));

                verify(processoService, times(1)).findById(ID);
        }

        @Test
        void erroAoBuscarProcessoInexistente() {
                when(processoService.findById(ID)).thenReturn(Optional.empty());

                ResponseEntity<Processo> response = processoController.getById(ID);

                assertAll(
                                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()),
                                () -> assertNull(response.getBody()));

                verify(processoService, times(1)).findById(ID);
        }

        @Test
        void atualizarProcesso() {
                when(processoService.findById(ID)).thenReturn(Optional.of(processo));
                when(processoService.update(any(Processo.class))).thenReturn(processo);

                processo.setNumero("67890");

                ResponseEntity<Processo> response = processoController.update(ID, processo);

                assertAll(
                                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                                () -> assertNotNull(response.getBody()),
                                () -> assertEquals("67890", response.getBody().getNumero()));

                verify(processoService, times(1)).findById(ID);
                verify(processoService, times(1)).update(processo);
        }

        @Test
        void erroAoAtualizarProcessoInexistente() {
                when(processoService.findById(ID)).thenReturn(Optional.empty());

                ResponseEntity<Processo> response = processoController.update(ID, processo);

                assertAll(
                                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()),
                                () -> assertNull(response.getBody()));

                verify(processoService, times(1)).findById(ID);
                verify(processoService, never()).update(any(Processo.class));
        }

        @Test
        void deletarProcesso() {
                when(processoService.findById(ID)).thenReturn(Optional.of(processo));
                doNothing().when(processoService).deleteById(ID);

                ResponseEntity<Void> response = processoController.delete(ID);

                assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

                verify(processoService, times(1)).findById(ID);
                verify(processoService, times(1)).deleteById(ID);
        }

        @Test
        void erroAoDeletarProcessoInexistente() {
                when(processoService.findById(ID)).thenReturn(Optional.empty());

                ResponseEntity<Void> response = processoController.delete(ID);

                assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

                verify(processoService, times(1)).findById(ID);
                verify(processoService, never()).deleteById(ID);
        }

        @Test
        void adicionarReuAProcesso() {
                Reu reu = new Reu();
                reu.setNome("José da Silva");

                when(processoService.adicionarReu(eq(ID), eq(reu.getNome()))).thenReturn(processo);

                ResponseEntity<Processo> response = processoController.adicionarReu(ID, reu);

                assertAll(
                                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                                () -> assertNotNull(response.getBody()),
                                () -> assertEquals(NUMERO_PROCESSO, response.getBody().getNumero()));

                verify(processoService, times(1)).adicionarReu(ID, reu.getNome());
        }

        @Test
        void erroAoAdicionarReuAProcesso() {
                Reu reu = new Reu();
                reu.setNome("José da Silva");

                when(processoService.adicionarReu(eq(ID), eq(reu.getNome()))).thenReturn(null);

                ResponseEntity<Processo> response = processoController.adicionarReu(ID, reu);

                assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
                verify(processoService, times(1)).adicionarReu(ID, reu.getNome());
        }
}
