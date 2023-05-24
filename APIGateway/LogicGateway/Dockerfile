FROM openjdk:17-oracle

ARG JAR_FILE=target/LogicGateway-0.0.1-SNAPSHOT.jar
ARG COMMONS_JAR_FILE=../Commons/target/Commons-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app

COPY ${JAR_FILE} app.jar
COPY ${COMMONS_JAR_FILE} commons.jar

ENTRYPOINT ["java", "-jar", "app.jar", "&", "java", "-jar", "commons.jar"]