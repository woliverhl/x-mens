FROM gradle:latest as builder
WORKDIR /home/gradle/project
COPY . .
USER root
RUN gradle bootJar --no-daemon -s

FROM openjdk:8-jre
EXPOSE 8080
COPY --from=builder /home/gradle/project/build/libs/x-mens-*.jar /app/app.jar
WORKDIR /app
LABEL Name=x-mens Version=1.0.0
CMD java -jar app.jar
