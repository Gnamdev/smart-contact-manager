FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
# COPY --from=build /target/smart-contact-manager-0.0.1-SNAPSHOT.jar smart-contact-manager.jar
COPY --from=build /target/smart-contact-manager.jar smart-contact-manager.jar

EXPOSE 8090

# ENTRYPOINT ["java","-jar","smart-contact-manager.jar"]
ENTRYPOINT ["java","-jar","smart-contact-manager.jar"]