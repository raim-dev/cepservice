package com.br.cepservice.infrastructure.repository.jpa;

import com.br.cepservice.infrastructure.entity.CepLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CepLogJpaRepository extends JpaRepository<CepLogEntity, Long> {
}
