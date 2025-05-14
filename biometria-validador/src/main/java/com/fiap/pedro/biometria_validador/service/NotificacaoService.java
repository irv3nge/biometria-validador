package com.fiap.pedro.biometria_validador.service;

import com.fiap.pedro.biometria_validador.model.BiometriaRegistro;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificacaoService {

    private final RestTemplate restTemplate;

    @Value("${quod.sistema.monitoramento.url}")
    private String urlNotificacaoFraude;

    public NotificacaoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void notificarFraude(BiometriaRegistro registro) {
        if (registro.getFraudeDetectada()) {

            String payload = createFraudNotificationPayload(registro);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> request = new HttpEntity<>(payload, headers);

            restTemplate.postForObject(urlNotificacaoFraude, request, String.class);
        }
    }

    private String createFraudNotificationPayload(BiometriaRegistro registro) {
        return "{\n" +
                "  \"transacaoId\": \"" + registro.getTransacaoId() + "\",\n" +
                "  \"tipoBiometria\": \"" + registro.getTipoBiometria() + "\",\n" +
                "  \"tipoFraude\": \"" + registro.getTipoFraude() + "\",\n" +
                "  \"dataCaptura\": \"" + registro.getDataCaptura() + "\",\n" +
                "  \"dispositivo\": {\n" +
                "    \"fabricante\": \"" + registro.getDispositivo().getFabricante() + "\",\n" +
                "    \"modelo\": \"" + registro.getDispositivo().getModelo() + "\",\n" +
                "    \"sistemaOperacional\": \"" + registro.getDispositivo().getSistemaOperacional() + "\"\n" +
                "  },\n" +
                "  \"canalNotificacao\": [\"sms\", \"email\"],\n" +
                "  \"notificadoPor\": \"sistema-de-monitoramento\",\n" +
                "  \"metadados\": {\n" +
                "    \"latitude\": " + registro.getMetadados().getLatitude() + ",\n" +
                "    \"longitude\": " + registro.getMetadados().getLongitude() + ",\n" +
                "    \"ipOrigem\": \"" + registro.getMetadados().getIpOrigem() + "\"\n" +
                "  }\n" +
                "}";
    }
}
