FROM amazoncorretto:21.0.4-alpine3.18

ADD target/ms-notes-0.0.1-SNAPSHOT.jar notes.jar

EXPOSE 8082

ENTRYPOINT [ "java", "-jar", "notes.jar" ]