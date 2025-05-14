package com.fiap.pedro.biometria_validador.controller;

import com.fiap.pedro.biometria_validador.model.BiometriaRegistro;
import com.fiap.pedro.biometria_validador.service.BiometriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/biometria")
@RequiredArgsConstructor
public class BiometriaController {

    private final BiometriaService service;

    @PostMapping(value = "/registrar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registrarBiometria(
            @RequestPart("registro") @Valid BiometriaRegistro registro,
            @RequestPart("imagem") MultipartFile imagem) {
        try {
            BiometriaRegistro salvo = service.salvar(registro, imagem);
            if (salvo.getFraudeDetectada()) {
                return ResponseEntity.status(400).body("Fraude detectada: " + salvo.getTipoFraude());
            }
            return ResponseEntity.ok(salvo);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Erro ao validar a imagem: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
