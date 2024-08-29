# Используем официальный образ JDK в качестве базового образа
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию в контейнере
WORKDIR /app

# Копируем файл сборки jar в контейнер
COPY target/cloudFileStorage-3.3.2.jar app.jar

# Указываем команду для запуска приложения
ENTRYPOINT ["java","-jar","/app/app.jar"]
