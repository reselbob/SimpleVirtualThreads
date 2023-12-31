# Use a base image with Java 11
FROM ubuntu:20.04

# Set environment variables for non-interactive installation
ENV DEBIAN_FRONTEND=noninteractive \
    TZ=UTC

# Update the package list and install essential packages
RUN apt-get update -y && \
    apt-get install -y --no-install-recommends \
        tzdata \
        ca-certificates \
        wget \
        && rm -rf /var/lib/apt/lists/*

# Set the timezone
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# Download and install OpenJDK 11
RUN apt-get update -y && \
    apt-get install -y --no-install-recommends \
        openjdk-11-jdk \
        && rm -rf /var/lib/apt/lists/*

# Set JAVA_HOME environment variable
ENV JAVA_HOME /usr/lib/jvm/java-11-openjdk-amd64
ENV PATH $JAVA_HOME/bin:$PATH

# Check Java and javac versions
RUN java -version && \
    javac -version

RUN apt-get update && apt-get install -y wget && rm -rf /var/lib/apt/lists/*

# Set environment variables for Maven
ENV MAVEN_VERSION 3.9.4
ENV MAVEN_HOME /usr/local/apache-maven-3.9.4

# Download and install Maven
RUN wget -q https://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz -O /tmp/apache-maven.tar.gz \
    && tar xzf /tmp/apache-maven.tar.gz -C /usr/local/ \
    && rm -f /tmp/apache-maven.tar.gz

# Add Maven binaries to PATH
ENV PATH $MAVEN_HOME/bin:$PATH

# Verify Maven installation
RUN mvn --version

# Install Git
RUN apt-get update && apt-get install -y git

# Set a working directory
WORKDIR /app

# Clone the GitHub repository and switch to the Development branch
RUN git clone https://github.com/reselbob/SimpleVirtualThreads.git

# Compile the project using Maven
#RUN cd SimpleVirtualThreads
RUN mvn compile -f /app/SimpleVirtualThreads/Java11/pom.xml

# Additional configurations can be added as per your requirements

# Finally, specify the entry point or command to run your application (if needed)
# ENTRYPOINT ["java", "-jar", "your-app.jar"]
