# Use a base image with Ubuntu
FROM ubuntu:latest

# Install required packages
RUN apt-get update -y && \
    apt-get install -y wget git && \
    rm -rf /var/lib/apt/lists/*

# Download and install Java 21
RUN wget https://download.java.net/java/GA/jdk21/fd2272bbf8e04c3dbaee13770090416c/35/GPL/openjdk-21_linux-x64_bin.tar.gz && \
    tar -xvf openjdk-21_linux-x64_bin.tar.gz && \
    mv jdk-21 /usr/local && \
    rm openjdk-21_linux-x64_bin.tar.gz

# Set Java environment variables
ENV JAVA_HOME=/usr/local/jdk-21
ENV PATH=$JAVA_HOME/bin:$PATH

# Install Maven 3.9.4
RUN wget https://archive.apache.org/dist/maven/maven-3/3.9.4/binaries/apache-maven-3.9.4-bin.tar.gz && \
    tar -xvf apache-maven-3.9.4-bin.tar.gz && \
    mv apache-maven-3.9.4 /usr/local && \
    rm apache-maven-3.9.4-bin.tar.gz

# Set Maven environment variables
ENV MAVEN_HOME=/usr/local/apache-maven-3.9.4
ENV PATH=$MAVEN_HOME/bin:$PATH

# Set a working directory
WORKDIR /app

# Clone the GitHub repository and switch to the Development branch
RUN git clone https://github.com/reselbob/SimpleVirtualThreads.git && \
    cd SimpleVirtualThreads && \
    git checkout development

# Compile the project using Maven
RUN mvn compile -f /app/SimpleVirtualThreads/Java21/pom.xml

# Additional configurations can be added as per your requirements

# Finally, specify the entry point or command to run your application (if needed)
# ENTRYPOINT ["java", "-jar", "your-app.jar"]
