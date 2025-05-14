package com.fiap.pedro.biometria_validador.service;

import com.fiap.pedro.biometria_validador.model.BiometriaRegistro;
import com.fiap.pedro.biometria_validador.repository.BiometriaRepository;
import com.fiap.pedro.biometria_validador.utils.FraudeValidator;
import com.fiap.pedro.biometria_validador.utils.ImagemValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class BiometriaService {

    private final BiometriaRepository repository;
    private final NotificacaoService notificacaoService;

    public BiometriaRegistro salvar(BiometriaRegistro registro, MultipartFile arquivoImagem) throws IOException {

        byte[] imageBytes;
        try (InputStream inputStream = arquivoImagem.getInputStream()) {
            imageBytes = inputStream.readAllBytes();
        }

        if (!ImagemValidator.validarFormatoEQualidade(imageBytes)) {
            throw new IllegalArgumentException("Imagem com formato inválido ou qualidade insuficiente.");
        }

        if (!ImagemValidator.validarTamanho(imageBytes)) {
            throw new IllegalArgumentException("Imagem excede o tamanho permitido.");
        }

       // if (!ImagemValidator.validarMetadados(imageBytes)) {
       //     throw new IllegalArgumentException("Imagem não contém metadados válidos.");
      //  }

        if (FraudeValidator.verificarFraude(registro.getTipoFraude(), arquivoImagem.getOriginalFilename())) {
            registro.setFraudeDetectada(true);
        } else {
            registro.setFraudeDetectada(false);
        }

        registro.setDataRegistro(new Date());
        registro.setDataCaptura(new Date());

        BiometriaRegistro salvo = repository.save(registro);

        if (salvo.getFraudeDetectada()) {
            notificacaoService.notificarFraude(salvo);
        }

        return salvo;
    }
}
