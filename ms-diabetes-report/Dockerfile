FROM amazoncorretto:21.0.4-alpine3.18

ADD target/ms-diabetes-report-0.0.1-SNAPSHOT.jar diabetes.jar

EXPOSE 8083

ENTRYPOINT [ "java", "-jar", "diabetes.jar" ]