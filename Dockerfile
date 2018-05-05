FROM openjdk:8-jre

ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/myservice/myservice.jar

EXPOSE 80

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/myservice/myservice.jar", "--spring.profiles.active=production"]
