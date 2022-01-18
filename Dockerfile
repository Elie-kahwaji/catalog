FROM adoptopenjdk/openjdk11:jdk-11.0.11_9-alpine  as build
#Create a new workspace folder in the image
WORKDIR /workspace/app
#Copy required file and folder to the created directory inside the buidl image
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
#Execute the following Command to build the application
#--mount=type=cache (This mount type allows the build container to cache directories for compilers and package managers.)
#in our mount we are caching the m2 directory
RUN --mount=type=cache,target=/root/.m2 ./mvnw install -DskipTests

#Create an arg vale that indicate the jar file created
ARG JAR_FILE=target/*.jar
#Create a copy of the jar created and name it application.jar inside the target folder
COPY ${JAR_FILE} target/application.jar
#extract the content of the application.jar inside the target/extracted
RUN java -Djarmode=layertools -jar target/application.jar extract --destination target/extracted

FROM adoptopenjdk/openjdk11:jdk-11.0.11_9-alpine
#instruct Docker that the container listens on the specified network ports at runtime
EXPOSE 8080/tcp
#Create a group called spring and a user called spring
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
#Indicate the extracted content of the application.jar in the build image
ARG EXTRACTED=/workspace/app/target/extracted
WORKDIR application
#Copy the extracted content from the build image to the run image
COPY --from=build ${EXTRACTED}/dependencies/ ./
COPY --from=build ${EXTRACTED}/spring-boot-loader/ ./
COPY --from=build ${EXTRACTED}/snapshot-dependencies/ ./
COPY --from=build ${EXTRACTED}/application/ ./
ENTRYPOINT ["java","-noverify","-XX:TieredStopAtLevel=1","-Dspring.main.lazy-initialization=true","org.springframework.boot.loader.JarLauncher"]