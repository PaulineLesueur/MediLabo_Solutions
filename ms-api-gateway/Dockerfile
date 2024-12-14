FROM amazoncorretto:21.0.4-alpine3.18

ADD target/ms-api-gateway-0.0.1-SNAPSHOT.jar gateway.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "gateway.jar" ]