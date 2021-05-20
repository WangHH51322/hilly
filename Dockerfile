FROM java:8
EXPOSE 8080

VOLUME /tmp

ENV TZ=Asia/Shanghai
RUN ln -sf /usr/share/zoneinfo/{TZ} /etc/localtime && echo "{TZ}" > /etc/timezone

ADD hillyProject.jar /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]