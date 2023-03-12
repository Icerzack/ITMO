FROM ubuntu
ENV DEBIAN_FRONTEND noninteractive

RUN apt-get update && apt-get install -qy \
    python3 python3-pip python3-pytest pylint pep8 python3-coverage mypy \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /data
VOLUME ["/data"]

# docker build --tag python-tools .
