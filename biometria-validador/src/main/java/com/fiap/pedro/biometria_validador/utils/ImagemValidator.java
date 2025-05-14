package com.fiap.pedro.biometria_validador.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.apache.commons.imaging.ImageFormat;
import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;

public class ImagemValidator {

    private static final int MAX_TAMANHO_MB = 5;

    public static boolean validarFormatoEQualidade(byte[] imageBytes) {
        try (InputStream is = new ByteArrayInputStream(imageBytes)) {
            ImageFormat format = Imaging.guessFormat(is.readAllBytes());
            return ImageFormats.JPEG.equals(format) || ImageFormats.PNG.equals(format);
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean validarTamanho(byte[] imageBytes) {
        try {
            File tempFile = File.createTempFile("temp", ".img");
            tempFile.deleteOnExit();
            Files.copy(new ByteArrayInputStream(imageBytes), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            long tamanhoEmBytes = tempFile.length();
            return tamanhoEmBytes <= (MAX_TAMANHO_MB * 1024 * 1024);
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean validarMetadados(byte[] imageBytes) {
        try (InputStream is = new ByteArrayInputStream(imageBytes)) {
            ImageMetadata metadata = Imaging.getMetadata(is.readAllBytes());
            return metadata != null;
        } catch (Exception e) {
            return false;
        }
    }
}
