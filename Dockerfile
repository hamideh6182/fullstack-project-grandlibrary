FROM openjdk:19

ENV ENVIROMENT=prod

MAINTAINER Hamideh Abdollahi Aghdam <hamideh_h61@yahoo.com>

EXPOSE 8080

ADD ./backend/target/app.jar app.jar

CMD ["sh", "-c", "java -jar /app.jar"]