# CEP SERVICE

Uma aplicação Java com Spring Boot baseada em Clean Architecture para consultar CEPs e registrar o histórico de consultas. A aplicação simula uma API externa utilizando WireMock e persiste os logs em banco de dados, integrando boas práticas como TDD, SOLID, contêineres e uso de nuvem (AWS).

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **Spring Web, Data JPA, Security**
- **H2 / PostgreSQL / DynamoDB**
- **WireMock (simulação de API externa)**
- **Docker / Docker Compose**
- **JUnit 5 + Mockito**
- **AWS Lambda, API Gateway, DynamoDB**
- **GitHub Actions (CI/CD)**

## Arquitetura

O projeto segue os princípios da **Clean Architecture**, com separação clara entre camadas:

- **Presentation Layer**: Controllers REST
- **Application Layer**: Casos de uso, orquestração
- **Domain Model**: Entidades e lógica de negócio
- **Infrastructure Layer**: Banco de dados, APIs externas, Docker
- **Test Layer**: TDD, unit/integration tests com cobertura

Veja o diagrama de arquitetura no repositório: `docs/CleanArchitectureDocumentation.pdf`.

## Ambientes

O projeto suporta três ambientes diferentes, cada um com sua própria configuração:

### Ambiente de Teste (H2)

- Utiliza banco de dados H2 em memória
- Configurado para testes automatizados
- Console H2 habilitado para depuração
- Configurações de pool de conexões otimizadas

### Ambiente de Desenvolvimento (PostgreSQL)

- Utiliza PostgreSQL em contêiner Docker
- Scripts de inicialização para configuração do banco
- Configurações de performance para PostgreSQL
- WireMock para simular a API externa

### Ambiente de Produção (DynamoDB)

- Utiliza AWS DynamoDB para persistência
- Configurações de throughput e auto-scaling
- Rate limiting para proteção da API
- Implantação em AWS Lambda
- Autenticação via API Key para acesso aos endpoints

## Testes

O projeto inclui testes abrangentes, incluindo:

- **Testes unitários**: Testam componentes isoladamente
- **Testes de integração**: Testam a integração entre componentes
- **Testes de edge cases**: Testam cenários de erro e limites
  - CEP não encontrado
  - Formato de CEP inválido
  - Erros de servidor externo
  - Timeouts de conexão

## Segurança

A aplicação implementa as seguintes medidas de segurança:

- **Autenticação via API Key**: Em produção, todos os endpoints requerem uma chave de API válida no cabeçalho `X-API-Key`
- **Rate Limiting**: Limita o número de requisições por período para prevenir abusos
- **Configuração segura**: Senhas e chaves de API são armazenadas como variáveis de ambiente ou secrets do GitHub

### Configuração da API Key

Para acessar os endpoints em produção, é necessário incluir o cabeçalho `X-API-Key` com uma chave válida em todas as requisições:

```bash
curl -H "X-API-Key: sua-api-key" https://seu-endpoint-aws.com/api/cep/01001000
```

## CI/CD

O pipeline de CI/CD é implementado usando GitHub Actions e inclui:

1. **Teste**: Executa todos os testes em um ambiente PostgreSQL
2. **Build**: Compila a aplicação com o perfil de produção
3. **Deploy**: Implanta a aplicação no AWS Lambda e configura o DynamoDB com as variáveis de ambiente necessárias

## Como Executar

### Localmente

```bash
# Iniciar o ambiente de desenvolvimento
docker-compose up -d

# Executar a aplicação
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

### Testes

```bash
# Executar todos os testes
./mvnw test

# Executar testes com cobertura
./mvnw verify
```
