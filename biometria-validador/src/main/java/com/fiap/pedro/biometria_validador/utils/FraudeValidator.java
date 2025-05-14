package com.fiap.pedro.biometria_validador.utils;

import org.apache.commons.lang3.StringUtils;

public class FraudeValidator {


    public static boolean verificarDeepFake(String tipoFraude, String nomeImagem) {
        if (StringUtils.containsIgnoreCase(nomeImagem, "deepfake")) {
            return true;
        }
        return tipoFraude != null && tipoFraude.equalsIgnoreCase("deepfake");
    }


    public static boolean verificarMascara(String tipoFraude, String nomeImagem) {
        if (StringUtils.containsIgnoreCase(nomeImagem, "mascara")) {
            return true;
        }
        return tipoFraude != null && tipoFraude.equalsIgnoreCase("mascara");
    }


    public static boolean verificarFotoDeFoto(String tipoFraude, String nomeImagem) {
        if (StringUtils.containsIgnoreCase(nomeImagem, "foto")) {
            return true;
        }
        return tipoFraude != null && tipoFraude.equalsIgnoreCase("foto");
    }


    public static boolean verificarFraude(String tipoFraude, String nomeImagem) {
        return verificarDeepFake(tipoFraude, nomeImagem)
                || verificarMascara(tipoFraude, nomeImagem)
                || verificarFotoDeFoto(tipoFraude, nomeImagem);
    }
}
