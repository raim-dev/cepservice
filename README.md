# CEP SERVICE

Uma aplicação Java com Spring Boot baseada em Clean Architecture para consultar CEPs e registrar o histórico de consultas. A aplicação simula uma API externa utilizando WireMock e persiste os logs em banco de dados, integrando boas práticas como TDD, SOLID, contêineres e uso de nuvem (AWS).


## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **Spring Web, Data JPA**
- **H2 / PostgreSQL**
- **WireMock (simulação de API externa)**
- **Docker / Docker Compose**
- **JUnit 5 + Mockito**
- **Gitpod (ambiente de desenvolvimento remoto)**
- **AWS (RDS, ECS)**


## Arquitetura

O projeto segue os princípios da **Clean Architecture**, com separação clara entre camadas:

- **Presentation Layer**: Controllers REST
- **Application Layer**: Casos de uso, orquestração
- **Domain Model**: Entidades e lógica de negócio
- **Infrastructure Layer**: Banco de dados, APIs externas, Docker
- **Test Layer**: TDD, unit/integration tests com cobertura


Veja o diagrama de arquitetura no repositório: `docs/CleanArchitectureDocumentation.pdf`.


