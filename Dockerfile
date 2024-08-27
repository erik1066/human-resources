FROM openjdk:8-jre-alpine

ARG AWS_KEY_ID
ARG AWS_KEY

COPY target/demo-*.jar /app.jar

ENV AWS_ACCESS_KEY_ID ${AWS_KEY_ID}
ENV AWS_SECRET_ACCESS_KEY ${AWS_KEY}
ENV AWS_DEFAULT_REGION us-east-1
ENV AWS_DEFAULT_OUTPUT text

RUN mkdir ~/.aws

COPY credentials ~/.aws/credentials
COPY config ~/.aws/config

ENTRYPOINT java -Dserver.tomcat.protocol-header=x-forwarded-proto -Dserver.tomcat.remote-ip-header=x-forwarded-for -jar /app.jar

