Любите Payara? Мы тоже!

Вот как запускать этот проект:
```shell
java -jar payara-micro-6.2023.11.jar --deploy target/navigator.war --sslport 8089 --nocluster --nohazelcast
```
Перед этим предварительно:
```shell
mvn clean install
```
Переименовать `navigator-1` в `navigator`
