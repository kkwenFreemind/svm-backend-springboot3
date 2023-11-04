FROM openjdk:17
VOLUME /tmp
ADD ./target/svm-backend-springboot3-0.0.1-SNAPSHOT.jar svm-backend.jar
ENTRYPOINT ["java","-jar","./svm-backend.jar"]
EXPOSE 8080