package com.fiap.pedro.biometria_validador;

import com.fiap.pedro.biometria_validador.model.BiometriaRegistro;
import com.fiap.pedro.biometria_validador.repository.BiometriaRepository;
import com.fiap.pedro.biometria_validador.service.BiometriaService;
import com.fiap.pedro.biometria_validador.service.NotificacaoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BiometriaServiceTest {

    @Mock
    private BiometriaRepository repository;

    @Mock
    private NotificacaoService notificacaoService;

    @InjectMocks
    private BiometriaService service;

    private MultipartFile carregarImagemTeste() throws IOException {
        byte[] conteudo = Files.readAllBytes(Paths.get("src/test/resources/igor.jpg"));
        return new MockMultipartFile("file", "igor.jpg", "image/jpg", conteudo);
    }


    @Test
    public void testSalvarComFraudeDetectada() throws Exception {
        MultipartFile arquivoImagem = carregarImagemTeste();

        BiometriaRegistro registro = new BiometriaRegistro();
        registro.setTipoBiometria("facial");
        registro.setTipoFraude("deepfake");

        when(repository.save(any(BiometriaRegistro.class))).thenAnswer(i -> i.getArgument(0));

        BiometriaRegistro resultado = service.salvar(registro, arquivoImagem);

        assertTrue(resultado.getFraudeDetectada(), "Fraude deve ser detectada.");
        verify(notificacaoService, times(1)).notificarFraude(resultado);
    }

    @Test
    public void testSalvarSemFraudeDetectada() throws Exception {
        MultipartFile arquivoImagem = carregarImagemTeste();

        BiometriaRegistro registro = new BiometriaRegistro();
        registro.setTipoBiometria("facial");
        registro.setTipoFraude("nenhuma");

        when(repository.save(any(BiometriaRegistro.class))).thenAnswer(i -> i.getArgument(0));

        BiometriaRegistro resultado = service.salvar(registro, arquivoImagem);

        assertFalse(resultado.getFraudeDetectada(), "Fraude não deve ser detectada.");
        verify(notificacaoService, never()).notificarFraude(any());
    }
}
