# vrc - Property API

Repositório contém a implementação do desafio backend de fazer uma [api que busca properties de Spotippos][challenge].

##### Tecnologias utilizadas:

  - java (guice + resteasy)
  - gradle
  - redis
  - mongodb (+shellzinho pra ajudar a iniciar o db/collection já testando a API)
  - Docker (opcional, para subir Redis e MongoDB)
  - apidoc (para doc da API, npm install apidoc -g)

## Long story short?

Para rodar o projeto utilizando o arquivo estático dentro do projeto, simples assim, rode:

```sh
$ gradle jettyRun
```

Para ver a documentação da API, olhe em [http://localhost:9000/doc/](http://localhost:9000/doc/).

Teste rápido? Acesse:

- [http://localhost:9000/vrc/v1/properties?ax=357&ay=536&bx=359&by=505](http://localhost:9000/vrc/v1/properties?ax=357&ay=536&bx=359&by=505)
- [http://localhost:9000/vrc/v1/properties/5398](http://localhost:9000/vrc/v1/properties/5398)

## Quer mais?

Vamos lá! As API's GET são "cacheáveis" (se o Redis for configurado). Então, a primeira requisição, pode "demorar" um pouco mais que a segunda vez (a mesma requisição por id ou por área). É tudo cacheado no Redis, se falhar qualquer operação nele ou não for configurado, a vida segue normalmente.

Você pode rodar o projeto utilizando o MongDB também.

Para rodar a app com Redis e MongoDB, faça o seguinte:

> Supondo que já tenha docker-machine e tudo mais [configurado](https://docs.docker.com/machine/get-started/).

No meu caso rodei `eval $(docker-machine env default)`.

### Configurando

> Vou mostrar as confs no final.

Adicionar/atualizar `jvmArg "-Dvrc.mode.impl.properties=MONGODB"` nas confs do gretty (dentro do arquivo build.gradle).

#### Iniciar Redis utilizando docker

- Iniciar container
```sh
$ docker pull smebberson/alpine-redis
$ docker run -d --name redis -p 6379:6379 smebberson/alpine-redis
$ export GRADLE_CACHE_HOST=$(docker-machine ip default)
```

- Configurar app para utilizar esse redis, adicionando/atualizando `jvmArg "-Dvrc.cache.master.server=$System.env.GRADLE_CACHE_HOST"` e `jvmArg "-Dvrc.cache.slave.servers=$System.env.GRADLE_CACHE_HOST"` nas confs do gretty (dentro do arquivo build.gradle).

Para acessar o redis, utilize: `redis-cli -h $(docker-machine ip default)`.

#### Iniciar MongoDB utilizando docker

- Iniciar container
```sh
$ docker pull mvertes/alpine-mongo:latest
$ docker run -d --name mongo -p 27017:27017 mvertes/alpine-mongo
$ export GRADLE_MONGODB_HOST=$(docker-machine ip default)
```

- Configurar app para utilizar esse mongodb, adicionando/atualizando `jvmArg "-Dvrc.mongodb.host=$System.env.GRADLE_MONGODB_HOST"` nas confs do gretty (dentro do arquivo build.gradle).

Para acessar o mongodb, utilize: `mongo --host $(docker-machine ip default)`.

#### Como as confs ficaram

```javascript
gretty {
	port = 9000
	contextPath = '/'
	debugPort = 5005
	debugSuspend = false
	jvmArg "-Dvrc.mode.impl.properties=MONGODB"
	jvmArg "-Dvrc.cache.master.server=$System.env.GRADLE_CACHE_HOST"
	jvmArg "-Dvrc.cache.slave.servers=$System.env.GRADLE_CACHE_HOST"
	jvmArg "-Dvrc.mongodb.host=$System.env.GRADLE_MONGODB_HOST"
}
```

#### Rode a APP

```sh
$ gradle jettyRun
```

#### Colocando info no MongoDB

Após subir a app, em outro terminal, rode:

```sh
$ ./util/init_mongo_properties.sh
```

> Pode demorar uns 2 minutos pra rodar tudo (são 8000 propriedades criadas sequencialmente).

#### Faça algumas consultas

- [http://localhost:9000/vrc/v1/properties?ax=357&ay=536&bx=359&by=505](http://localhost:9000/vrc/v1/properties?ax=357&ay=536&bx=359&by=505)
- [http://localhost:9000/vrc/v1/properties/5398](http://localhost:9000/vrc/v1/properties/5398)

## Criando a documentação da API

```sh
$ apidoc -i src/main/java/com/caio/vrc/resource/ -o src/main/webapp/doc/
```

----

Caio S.

**Thanks, Yeah!**

   [challenge]: <https://github.com/VivaReal/code-challenge/blob/master/backend.md>

