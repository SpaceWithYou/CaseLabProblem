#Первая стадия
#Упаковка в jar
FROM eclipse-temurin:22-alpine AS package
LABEL authors="Артемий"
COPY pom.xml /pom.xml
COPY src /src
CMD ["mvn", "package"]

#Вторая стадия
FROM eclipse-temurin:22-alpine as run
#Копируем jar файл в контейнер
#!!! Перед сборкой запаковать проект в jar файл !!!
COPY /target/*.jar /app.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar", "app.jar"]