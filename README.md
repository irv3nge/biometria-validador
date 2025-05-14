
# Biometric Validation System

## Descrição

Este projeto implementa um sistema de **validação biométrica e detecção de fraudes** utilizando tecnologias como **Spring Boot**, **MongoDB** e **Spring Security**. O sistema é capaz de validar **biometria facial** e **impressões digitais**, além de realizar a **detecção de fraudes** em imagens (como deepfake, máscaras e foto de foto).

## Funcionalidades

- Validação de biometria facial e digital.
- Detecção de fraudes (Deepfake, Máscaras, Foto de Foto).
- Persistência de dados no **MongoDB**.
- Envio de notificações para o sistema de monitoramento de fraudes.
- API RESTful com **Spring Boot**.
  
## Tecnologias Utilizadas

- **Spring Boot**: Framework para desenvolvimento do backend.
- **Spring Data MongoDB**: Para persistência de dados em MongoDB.
- **Spring Web**: Para a criação de APIs RESTful.
- **Spring Security** (opcional): Para segurança da aplicação (autenticação/autorização).
- **JUnit** e **Mockito**: Para testes unitários e de integração.
- **Metadata-Extractor**: Para extração de metadados das imagens.
- **Commons Imaging**: Para validação de formatos e qualidade das imagens.

## Estrutura do Projeto

- **`/src/main/java`**: Contém o código fonte da aplicação.
  - **`controller/`**: Contém o controlador da API.
  - **`service/`**: Contém os serviços principais, como a validação de biometria e a detecção de fraudes.
  - **`model/`**: Contém os modelos de dados (Biometria, Dispositivo, Metadados).
  - **`repository/`**: Contém o repositório para interação com o MongoDB.
  - **`utils/`**: Contém utilitários como `FraudeValidator` e `ImagemValidator`.

- **`/src/test`**: Contém os testes unitários e de integração.

## Como Rodar o Projeto

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/irv3nge/biometria-validator.git
   cd biometria-validator
   ```

2. **Instale as dependências:**

   Se estiver usando Maven:
   ```bash
   mvn clean install
   ```

3. **Configure o MongoDB:**
   Certifique-se de que o MongoDB está instalado e configurado. Caso contrário, você pode rodar uma instância local.

4. **Rodando a aplicação:**
   ```bash
   mvn spring-boot:run
   ```

   Ou, se preferir rodar diretamente da IDE (IntelliJ ou Eclipse), execute a classe `BiometriaValidatorApplication.java`.

## Endpoints da API

- **POST** `/api/biometria/registrar`: Recebe uma biometria (imagem) e retorna se a validação foi bem-sucedida ou se houve fraude.

## Testes

Os testes unitários e de integração estão localizados na pasta **`/src/test`**. Para rodá-los:

```bash
mvn test
```

## Contribuição

Contribuições são bem-vindas! Se você tiver sugestões de melhorias ou encontrar bugs, sinta-se à vontade para abrir **issues** ou enviar **pull requests**.

## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).
