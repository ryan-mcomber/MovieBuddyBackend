FROM openjdk:8-jdk-alpine
COPY /target/MovieBuddyBackend-0.0.1-SNAPSHOT.jar MovieBuddyBackend-0.0.1-SNAPSHOT.jar
EXPOSE 5000
ENTRYPOINT ["java", "-jar", "/MovieBuddyBackend-0.0.1-SNAPSHOT.jar"]