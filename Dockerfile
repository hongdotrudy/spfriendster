FROM alpine:3.7

RUN apk update && apk add --no-cache mongodb
RUN apk add --no-cache openjdk8

RUN mkdir -p /data/db
EXPOSE 27017 28017 9090

ADD target/sp-friendster-api-1.0.0.jar .

CMD mongod --fork --logpath /var/log/mongod.log && java -jar sp-friendster-api-1.0.0.jar


