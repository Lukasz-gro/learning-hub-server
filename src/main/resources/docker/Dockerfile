FROM ubuntu:latest

RUN apt-get update && \
    apt-get -y install gcc && \
    apt-get -y install g++ && \
    apt-get -y install python3 && \
    apt-get install -y openjdk-11-jdk && \
    apt-get -y install vim && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /judge
COPY $DOCKER_DIR_PATH/containerFiles /judge/scripts
