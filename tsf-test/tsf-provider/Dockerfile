FROM centos:7
RUN yum update -y && yum install -y java-1.8.0-openjdk
ARG JAR_FILE
COPY ${JAR_FILE} /data/tsf/app.jar
COPY run.sh /data/tsf/run.sh
# GMT+8 for CentOS
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo "Asia/Shanghai" > /etc/timezone
# run.sh
CMD ["sh", "-c", "cd /data/tsf; sh run.sh app.jar /data/tsf"]
