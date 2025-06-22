# Desafio Itaú Backend
Este projeto consiste em uma API RESTful desenvolvida em Java, utilizando o framework Spring Boot. Ele foi criado para resolver um desafio de programação proposto para uma vaga de backend no Itaú, focado no processamento e análise de transações financeiras.
<br>Link do repositório referência: https://github.com/feltex/desafio-itau-backend

# Funcionalidades
A API oferece os seguintes endpoints para gerenciar transações e obter estatísticas em tempo real:

* `POST /transacao`: Recebe e registra uma nova transação.
* `DELETE /transacao`: Limpa todas as transações armazenadas em memória.
* `GET /estatistica`: Retorna estatísticas agregadas sobre as transações recebidas, como soma total, média, valor máximo, valor mínimo e contagem.

Documentação Interativa (Swagger UI)
* `http://localhost:8080/swagger-ui.html`: Com a aplicação rodando, acesse este endereço para acessar a documentação interativa da API no navegador

# Tecnologias Utilizadas
Este projeto demonstra proficiência e experiência com as seguintes tecnologias e conceitos:

* Java/Kotlin: Linguagem principal para o desenvolvimento da API, aproveitando a JVM para robustez e performance.
* Spring Boot: Utilizado para simplificar o desenvolvimento de aplicações microsserviços, proporcionando um ambiente standalone e de fácil deploy com servidor embarcado (Tomcat/Jetty).
* RESTful API Design: Implementação de uma arquitetura stateless para os endpoints, com troca de dados via JSON, garantindo interoperabilidade e escalabilidade.
* Gerenciamento de Dados em Memória: Solução otimizada para processar e armazenar dados transacionais inteiramente na RAM, demonstrando expertise em estruturas de dados e algoritmos de alta performance, sem dependência de bancos de dados externos.
* Controle de Versão (Git/GitHub): Gerenciamento colaborativo do código-fonte, utilizando boas práticas de Git para versionamento e controle de branches.
* Implementação de testes unitários e testes de integração: Testes que estressam as funcionalidades do sistemas e também como essas funcionalidades se comunicam entre si.
* Containerização do sistema: Fornecimento de imagem Docker para facilitar a implantação e execução do sistema em containers.
* Logs: Sistema de hierarquia de logs para melhor entendimento do fluxo da aplicação.
* Saúde da aplicação: Verificação da saúde da memória da aplicação.
* Perfomance: Medição de tempo do método que puxa as estatísticas das transações.
* Documentação: Documentação da API com Swagger no endereço `swagger-ui/index.html`

# Pré-requisitos
Antes de construir e executar a aplicação, certifique-se de ter as seguintes ferramentas instaladas em sua máquina:
* Java Development Kit (JDK) 17 ou superior: A aplicação foi desenvolvida e testada com JDK 17.
  * Verifique sua versão: java -version
* Apache Maven 3.6.x ou superior: Utilizado para gerenciar dependências e construir o projeto.
  * Verifique sua versão: mvn -v
* Docker Desktop (Opcional, mas Recomendado): Para construir e executar a aplicação em um ambiente containerizado.
  * Verifique se o Docker está rodando: docker --version
* Git: Para clonar o repositório.
  * Verifique sua versão: git --version

# Como construir e executar
1. Clone o repositório no GitLab
```
git clone https://github.com/feltex/desafio-itau-backend.git
cd desafio-itau-backend
```
2. Opção A: Executar localmente
  Na pasta raiz do projeto rode:
  ```
  mvn clean package -DskipTests
  ```
  Após isso, a aplicação pode ser executada rodando SpringbootApplication via editor de código, ou executando no terminal:
  ```
  java -jar target/springboot-0.0.1-SNAPSHOT.jar
  ```
2. Opção B: Executar com Docker
  Esta opção empacota sua aplicação em uma imagem Docker e a executa como um contêiner, garantindo um ambiente isolado e portátil.<br/>
  1 - Construir o projeto(Gerar o .jar)
  Na pasta raiz do projeto rode:
  ```
  mvn clean package -DskipTests
  ```
  2 - Construir a Imagem Docker:
  Ainda na raiz do projeto (onde o Dockerfile está localizado), construa a imagem Docker. Um Dockerfile de exemplo é fornecido na raiz do projeto.
  OBS: Lembre-se de iniciar o docker.
  ```
  docker build -t springboot .
  ```
  Depois que a imagem for construída, você pode rodar sua aplicação dentro de um contêiner:
  ```
  docker run -p 8080:8080 springboot
  ```

# Rodar os Testes
Para executar os testes unitários e de integração do projeto, navegue até o diretório raiz do projeto e execute:
```
mvn clean test
```
