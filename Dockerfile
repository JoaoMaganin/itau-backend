# Usa uma imagem base oficial do OpenJDK (Java Development Kit)
FROM openjdk:17-jdk-slim

# Define um argumento para o nome do arquivo JAR.
# O nome do JAR é geralmente gerado pelo Maven/Gradle.
# Para Maven, geralmente é target/*.jar
ARG JAR_FILE=target/springboot-0.0.1-SNAPSHOT.jar

# Copia o arquivo JAR do seu projeto (depois de compilado) para dentro da imagem.
# O caminho 'app.jar' dentro do contêiner é arbitrário, mas comum.
COPY ${JAR_FILE} app.jar

# Expõe a porta em que sua aplicação Spring Boot irá rodar.
# Por padrão, Spring Boot usa a porta 8080.
EXPOSE 8080

# Comando para rodar a aplicação quando o contêiner for iniciado.
# 'java -jar app.jar' executa o JAR.
ENTRYPOINT ["java", "-jar", "app.jar"]