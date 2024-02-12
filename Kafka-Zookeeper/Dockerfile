FROM ubuntu:20.04
RUN apt-get update
RUN apt-get install -y wget
RUN apt-get install -y openjdk-17-jdk openjdk-17-jre
RUN apt-get install -y supervisor
RUN wget https://downloads.apache.org/kafka/3.6.1/kafka_2.12-3.6.1.tgz
RUN tar -xvf kafka_2.12-3.6.1.tgz
RUN mv kafka_2.12-3.6.1 /opt/kafka
RUN rm -rf kafka_2.12-3.6.1.tgz
RUN wget https://dlcdn.apache.org/zookeeper/zookeeper-3.9.1/apache-zookeeper-3.9.1-bin.tar.gz
RUN tar -xvf apache-zookeeper-3.9.1-bin.tar.gz
RUN mv apache-zookeeper-3.9.1-bin /opt/zookeeper
RUN cp /opt/zookeeper/conf/zoo_sample.cfg /opt/zookeeper/conf/zoo.cfg
RUN rm -rf apache-zookeeper-3.9.1-bin.tar.gz
COPY supervisord.conf /etc/supervisor/conf.d/supervisord.conf
EXPOSE 2181 9092
ENTRYPOINT ["/usr/bin/supervisord"]
CMD ["-c","/etc/supervisor/conf.d/supervisord.conf"]