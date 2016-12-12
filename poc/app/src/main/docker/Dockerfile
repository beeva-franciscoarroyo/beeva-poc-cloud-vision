FROM java:8
VOLUME /tmp
RUN mkdir -p /var/app/
ADD poc-cloud-vision-fc8488bd4d01.json /var/app/poc-cloud-vision-fc8488bd4d01.json
RUN mkdir -p /var/app/images
#ADD images /var/app/images
ADD tessdata tessdata
ADD app-0.0.3-SNAPSHOT.jar  app.jar
RUN sh -c 'touch /app.jar'
RUN apt-get update && apt-get install -y tesseract-ocr tesseract-ocr-eng
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]