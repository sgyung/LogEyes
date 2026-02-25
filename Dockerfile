FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# 빌드 결과물(jar) 이름이 바뀌어도 자동 복사
COPY build/libs/*.jar app.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]