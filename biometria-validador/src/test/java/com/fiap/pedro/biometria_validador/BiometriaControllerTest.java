package com.fiap.pedro.biometria_validador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.pedro.biometria_validador.controller.BiometriaController;
import com.fiap.pedro.biometria_validador.model.BiometriaRegistro;
import com.fiap.pedro.biometria_validador.service.BiometriaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BiometriaController.class)
public class BiometriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BiometriaService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRegistrarBiometriaFraudeDetectada() throws Exception {
        BiometriaRegistro registro = new BiometriaRegistro();
        registro.setTipoBiometria("facial");
        registro.setTipoFraude("deepfake");
        registro.setFraudeDetectada(true);

        MockMultipartFile imagem = new MockMultipartFile("imagem", "imagem.jpg", "image/jpeg", "fake-content".getBytes());
        MockMultipartFile json = new MockMultipartFile("registro", "", "application/json", objectMapper.writeValueAsBytes(registro));

        when(service.salvar(registro, imagem)).thenReturn(registro);

        mockMvc.perform(multipart("/api/biometria/registrar")
                        .file(json)
                        .file(imagem)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Fraude detectada: deepfake"));
    }

    @Test
    void testRegistrarBiometriaSemFraude() throws Exception {
        BiometriaRegistro registro = new BiometriaRegistro();
        registro.setTipoBiometria("facial");
        registro.setTipoFraude("nenhuma");
        registro.setFraudeDetectada(false);

        MockMultipartFile imagem = new MockMultipartFile("imagem", "imagem.jpg", "image/jpeg", "fake-content".getBytes());
        MockMultipartFile json = new MockMultipartFile("registro", "", "application/json", objectMapper.writeValueAsBytes(registro));

        when(service.salvar(registro, imagem)).thenReturn(registro);

        mockMvc.perform(multipart("/api/biometria/registrar")
                        .file(json)
                        .file(imagem)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(registro)));
    }
}
