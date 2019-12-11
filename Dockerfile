FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY . shopping-list
RUN cd shopping-list && ./mvnw package && cp target/*.jar ../app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]