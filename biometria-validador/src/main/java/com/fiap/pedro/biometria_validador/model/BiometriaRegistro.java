package com.fiap.pedro.biometria_validador.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Document(collection = "biometrias")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BiometriaRegistro {

    @Id
    private String id;

    private UUID transacaoId;
    private String tipoBiometria;
    private String tipoFraude;
    private Date dataCaptura;

    private Dispositivo dispositivo;
    private List<String> canalNotificacao;
    private String notificadoPor;

    private Metadados metadados;

    private Boolean fraudeDetectada;
    private Boolean validadoComSucesso;

    private String imagemBase64;


    private Date dataRegistro;


}
