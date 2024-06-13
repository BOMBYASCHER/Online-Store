FROM gradle:8.5.0-jdk21

COPY . .

RUN gradle --no-daemon bootJar

EXPOSE 4343

CMD java -jar build/libs/Online-Store-1.0.0-SNAPSHOT.jar