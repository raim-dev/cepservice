CREATE TABLE IF NOT EXISTS cep_log (
    id BIGSERIAL PRIMARY KEY,
    cep VARCHAR(8) NOT NULL,
    data_consulta TIMESTAMP NOT NULL,
    resposta_api TEXT NOT NULL
    );