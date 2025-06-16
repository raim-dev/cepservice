package com.br.cepservice.infrastructure.mapper;


import com.br.cepservice.domain.model.CepLog;
import com.br.cepservice.infrastructure.entity.CepLogEntity;
import org.springframework.stereotype.Component;

@Component
public class CepLogMapper {
    public CepLogEntity toEntity(CepLog domain) {
        return new CepLogEntity(
                domain.getId(),
                domain.getCep(),
                domain.getDataConsulta(),
                domain.getRespostaApi()
        );
    }

    public CepLog toDomain(CepLogEntity entity) {
        return new CepLog(
                entity.getId(),
                entity.getCep(),
                entity.getDataConsulta(),
                entity.getRespostaApi()
        );
    }
}
