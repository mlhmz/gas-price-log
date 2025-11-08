FROM node:25.1.0 AS frontend-builder

WORKDIR /app

COPY ./ui/package*.json ./

RUN npm install

COPY ./ui .

RUN npm run build

FROM maven:3.9.11-eclipse-temurin-21-noble AS backend-builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

COPY --from=frontend-builder /app/dist ./src/main/resources/META-INF/resources

RUN mvn package

FROM registry.access.redhat.com/ubi9/openjdk-21:1.21 AS runner

ENV LANGUAGE='en_US:en'

COPY --from=backend-builder /app/target/quarkus-app/lib/ /deployments/lib/
COPY --from=backend-builder /app/target/quarkus-app/*.jar /deployments/
COPY --from=backend-builder /app/target/quarkus-app/app/ /deployments/app/
COPY --from=backend-builder /app/target/quarkus-app/quarkus/ /deployments/quarkus/
RUN mkdir -p /deployments/data

EXPOSE 8080
USER 185
ENV JAVA_OPTS_APPEND="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV JAVA_APP_JAR="/deployments/quarkus-run.jar"

ENTRYPOINT [ "/opt/jboss/container/java/run/run-java.sh" ]

