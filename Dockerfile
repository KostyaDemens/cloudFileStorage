# Используем базовый образ с Java
FROM openjdk:17-jdk

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем JAR файл в рабочую директорию
COPY target/cloudFileStorage-3.3.2.jar app.jar

# Открываем порт 8080
EXPOSE 8080

# Проверяем содержимое директории /app
RUN ls -la /app

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]
