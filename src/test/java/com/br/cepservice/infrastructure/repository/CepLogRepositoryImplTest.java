package com.br.cepservice.infrastructure.repository;

import com.br.cepservice.domain.model.CepLog;
import com.br.cepservice.infrastructure.mapper.CepLogMapper;
import com.br.cepservice.infrastructure.repository.jpa.CepLogJpaRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import({CepLogRepositoryImpl.class, CepLogMapper.class})
class CepLogRepositoryImplTest {

    @Autowired
    private CepLogJpaRepository jpaRepository;

    private CepLogRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        repository = new CepLogRepositoryImpl(jpaRepository, new CepLogMapper());
    }

    @Test
    @Transactional
    void deveSalvarLogCorretamente() {
        CepLog log = CepLog.builder()
                .cep("01001000")
                .dataConsulta(LocalDateTime.now())
                .respostaApi("{\"logradouro\":\"Praça da Sé\"}")
                .build();
        CepLog saved = repository.salvar(log);
        assertNotNull(saved);
        assertNotNull(jpaRepository.findById(saved.getId()).orElse(null));
    }
}
