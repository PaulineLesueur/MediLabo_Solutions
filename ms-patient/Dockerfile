FROM amazoncorretto:21.0.4-alpine3.18

ADD target/ms-patient-0.0.1-SNAPSHOT.jar patient.jar

EXPOSE 8081

ENTRYPOINT [ "java", "-jar", "patient.jar" ]