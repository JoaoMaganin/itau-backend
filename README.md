# Desafio Itaú Backend
Este projeto consiste em uma API RESTful desenvolvida em Java, utilizando o framework Spring Boot. Ele foi criado para resolver um desafio de programação proposto para uma vaga de backend no Itaú, focado no processamento e análise de transações financeiras.
<br>Link do repositório referência: https://github.com/feltex/desafio-itau-backend

# Funcionalidades
A API oferece os seguintes endpoints para gerenciar transações e obter estatísticas em tempo real:

* `POST /transacao`: Recebe e registra uma nova transação.
* `DELETE /transacao`: Limpa todas as transações armazenadas em memória.
* `GET /estatistica`: Retorna estatísticas agregadas sobre as transações recebidas, como soma total, média, valor máximo, valor mínimo e contagem.

# Tecnologias Utilizadas
Este projeto demonstra proficiência e experiência com as seguintes tecnologias e conceitos:

* Java/Kotlin: Linguagem principal para o desenvolvimento da API, aproveitando a JVM para robustez e performance.
* Spring Boot: Utilizado para simplificar o desenvolvimento de aplicações microsserviços, proporcionando um ambiente standalone e de fácil deploy com servidor embarcado (Tomcat/Jetty).
* RESTful API Design: Implementação de uma arquitetura stateless para os endpoints, com troca de dados via JSON, garantindo interoperabilidade e escalabilidade.
* Gerenciamento de Dados em Memória: Solução otimizada para processar e armazenar dados transacionais inteiramente na RAM, demonstrando expertise em estruturas de dados e algoritmos de alta performance, sem dependência de bancos de dados externos.
* Controle de Versão (Git/GitHub): Gerenciamento colaborativo do código-fonte, utilizando boas práticas de Git para versionamento e controle de branches.
* Implementação de testes unitários e testes de integração: Testes que estressam as funcionalidades do sistemas e também como essas funcionalidades se comunicam entre si.

# Desafios e Aprendizados
Um dos principais desafios foi projetar uma solução que pudesse calcular estatísticas precisas e em tempo real sobre um fluxo contínuo de transações. Como no desafio não era permitido usar nenhum BD ou cache, a solução foi usar um tipo de lista que nunca tinha usado antes chamado ConcurrentLinkedQueue
que oferece bom desempenho em cenários de inserção e remoção frequentes, especialmente quando a taxa de operações de inserção e remoção é alta.
