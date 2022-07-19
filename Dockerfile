FROM amazoncorretto:11
RUN gradle clean build
ARG JAR_FILE=/build/libs/onboarding-challenge-events-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} onboarding-challenge-events.jar
ENTRYPOINT ["java", "-jar", "/onboarding-challenge-events.jar"]