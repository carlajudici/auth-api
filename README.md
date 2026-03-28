# auth-api

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/auth-api-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Related Guides

- REST ([guide](https://quarkus.io/guides/rest)): A Jakarta REST implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.
- SmallRye JWT ([guide](https://quarkus.io/guides/security-jwt)): Secure your applications with JSON Web Token
- JDBC Driver - PostgreSQL ([guide](https://quarkus.io/guides/datasource)): Connect to the PostgreSQL database via JDBC

## Provided Code

### Hibernate ORM

Create your first JPA entity

[Related guide section...](https://quarkus.io/guides/hibernate-orm)




### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)

Passo a Passo para criação do Projeto
 Etapa 1: Configuração Inicial (COMPLETO)
1.1 Criar conta no GitHub
✅ Criar usuário: carlajudici

✅ Repositório: auth-api

1.2 Configurar Java 21
✅ Instalar OpenJDK 21

✅ Configurar JAVA_HOME

✅ Verificar instalação

1.3 Criar projeto Quarkus
✅ Acessar https://code.quarkus.io

✅ Configurações:

Group: com.meuapp

Artifact: auth-api

Build Tool: Maven

Java: 21

✅ Extensões adicionadas:

Quarkus REST

Container Image Docker

SmallRye JWT

1.4 Testar projeto
✅ Extrair: unzip auth-api.zip

✅ Rodar: ./mvnw quarkus:dev

✅ Acessar: http://localhost:8080

✅ Etapa 2: Implementação Básica (COMPLETO)
2.1 Criar estrutura de pastas
bash
src/main/java/com/meuapp/
├── model/
├── resource/
└── service/
2.2 Criar arquivos
✅ User.java (model com roles)

✅ AuthService.java (criptografia)

✅ AuthResource.java (endpoints)

✅ ProtectedResource.java (endpoints protegidos)

✅ TestResource.java (teste)

2.3 Configurar JWT
✅ Criar pasta: src/main/resources/keys/

✅ Gerar chaves RSA:

bash
openssl genrsa -out privateKey.pem 2048
openssl rsa -in privateKey.pem -pubout -out publicKey.pem
✅ Configurar application.properties com caminhos das chaves

2.4 Adicionar ao .gitignore
✅ src/main/resources/keys/privateKey.pem

✅ Etapa 3: Git e GitHub (COMPLETO)
3.1 Inicializar Git
bash
git init
git add .
git commit -m "Initial commit: Auth API with Quarkus"
3.2 Configurar SSH
✅ Verificar chaves SSH existentes

✅ Testar conexão: ssh -T git@github.com

✅ Conectar ao GitHub: git remote add origin git@github.com:carlajudici/auth-api.git

3.3 Push
bash
git push -u origin main
✅ Push bem sucedido!

Etapa 4: Testar JWT Localmente
Rodar o projeto: ./mvnw quarkus:dev

Testar cadastro de usuário
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"João Silva","email":"joao@email.com","password":"123456"}'


  curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Admin","email":"admin@meuapp.com","password":"admin123"}'

  curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Outro","email":"joao@email.com","password":"123"}'
Testar login e obter token
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"joao@email.com","password":"123456"}'


  curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@meuapp.com","password":"admin123"}'
Testar endpoints protegidos
curl http://localhost:8080/test


Etapa 5: Preparar para AWS
Criar Dockerfile

Testar build da imagem Docker

Configurar variáveis de ambiente

Etapa 6: GitHub Actions (CI/CD)
Criar workflow .github/workflows/deploy.yml

Configurar secrets no GitHub

Testar pipeline

Etapa 7: Deploy na AWS
Criar ECR Repository

Criar ECS Cluster

Criar Task Definition

Criar Service

Configurar Load Balancer