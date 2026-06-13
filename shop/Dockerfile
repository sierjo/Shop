# Образ Java 21
FROM eclipse-temurin:21-jre-alpine

# рабочая папка внутри контейнера
WORKDIR /app

#Копирование скомпилированного проекта
# Сначала компиляция проекта, затем запуск Docker
COPY target/shop-0.0.1-SNAPSHOT.jar app.jar

# порт для работы Spring Boot
EXPOSE 8080

# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]