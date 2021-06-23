#!/bin/sh

cd "$(dirname "$0")"

mvn clean && \
mvn compile && \
mvn install && \
echo
