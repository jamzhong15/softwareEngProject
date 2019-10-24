FROM maven:3.6-jdk-11

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update \
    && apt-get -y install --no-install-recommends python3 2>&1 \
    && apt-get -y install --no-install-recommends apt-utils dialog 2>&1 \
    && apt-get -y install git procps lsb-release \
    && apt-get autoremove -y \
    && apt-get clean -y \
    && rm -rf /var/lib/apt/lists/*

COPY src /jsh/src
COPY pom.xml /jsh/pom.xml
COPY jsh /jsh/jsh
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> 957e72ff1d9d8ebebe1bf47385760953b63bdb12
RUN chmod +x /jsh/jsh
COPY analysis /jsh/analysis
RUN chmod +x /jsh/analysis
COPY test /jsh/test
RUN chmod +x /jsh/test
COPY coverage /jsh/coverage
RUN chmod +x /jsh/coverage
<<<<<<< HEAD
=======

>>>>>>> 957e72ff1d9d8ebebe1bf47385760953b63bdb12

RUN cd /jsh && mvn package

ENV DEBIAN_FRONTEND=

EXPOSE 8000

