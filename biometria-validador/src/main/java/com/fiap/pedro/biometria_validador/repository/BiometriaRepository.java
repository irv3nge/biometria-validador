package com.fiap.pedro.biometria_validador.repository;


import com.fiap.pedro.biometria_validador.model.BiometriaRegistro;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BiometriaRepository extends MongoRepository<BiometriaRegistro , String> {

    List<BiometriaRegistro> findByTransacaoId(UUID transacaoId);

    List<BiometriaRegistro> findByFraudeDetectadaTrue();
}
