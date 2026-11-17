#!/bin/bash
# Script to run the Tourism Analytics Spring Boot Application
if [ -d "/opt/homebrew/opt/openjdk/libexec/openjdk.jdk/Contents/Home" ]; then
    export JAVA_HOME="/opt/homebrew/opt/openjdk/libexec/openjdk.jdk/Contents/Home"
fi
export PATH="$JAVA_HOME/bin:/opt/homebrew/opt/maven/bin:$PATH"

echo "Using Java: $(java -version 2>&1 | head -n 1)"
echo "Starting Spring Boot application..."
mvn spring-boot:run

