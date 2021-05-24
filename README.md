# Desafio Guiabolso
[Desafio Guiabolso | Back-end Engineer](https://github.com/GuiaBolso/seja-um-guia-back)

### Instruções
Requisição de lista de transações via ```[GET] /<id>/transacoes/<ano>/<mes>```,
onde:

- `id` representa o ID do usuário e deve ser um número inteiro de 1.000 a 100.000;
- `ano` representa o ano de ocorrência das transações (por convenção, deve ser um número inteiro de 4 dígitos);
- `mes` representa o mês de ocorrência das transações (por convenção, deve ser um número inteiro de 1 a 12).

#### Execução

- Para compilação e execução do código,
  <br/> utilizar IDE compatível com Java 8 e Gradle
  <br/>(Ex.: IntelliJ, Eclipse etc.)
  <br/> e fornecer os devidos parâmetros à URL:
  
  ```
  http://localhost:8080/{id}/transacoes/{ano}/{mes}
  ```

### Tecnologias utilizadas
- JDK 1.8.0_281
- Gradle 7.0.2
- Spring Boot 2.5.0
- IntelliJ IDEA 2021.1.1

