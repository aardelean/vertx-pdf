FROM openjdk:8u102-jre
ENV SERVICE_NAME=pdf-generator
ENV PORT=8888
ENV JAVA_OPTS='-Xms128m -Xmx128m -XX:MaxMetaspaceSize=32m -XX:+PrintCommandLineFlags'
ADD build/libs/vertx-pdf-1.0.jar app.jar
ADD entrypoint.sh entrypoint.sh
RUN chmod +x entrypoint.sh
ENTRYPOINT ["./entrypoint.sh"]
