FROM adoptopenjdk/openjdk11:ubi

WORKDIR /program
ADD setenv.sh setenv.sh
ADD target/*0.0.1-SNAPSHOT.war app.war

EXPOSE 9000

VOLUME ["/logs", "/logs/gc"]
CMD ["/bin/bash", "-c", "source setenv.sh && exec java $JAVA_OPTS -jar app.war"]
