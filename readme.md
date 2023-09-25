Requires Java 21 and Maven 3.9.4

# Install Java 21 on Ubuntu

```bash
sudo apt update -y

sudo apt-get install wget -y

wget https://download.java.net/java/GA/jdk21/fd2272bbf8e04c3dbaee13770090416c/35/GPL/openjdk-21_linux-x64_bin.tar.gz

tar -xvf openjdk-21_linux-x64_bin.tar.gz

cd jdk-21

sudo mkdir -p /usr/local/jdk-21

sudo mv * /usr/local/jdk-21

echo 'export JAVA_HOME=/usr/local/jdk-21' >> ~/.bashrc

echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.bashrc

source ~/.bashrc

java --version

```

# Install Maven 3.9.4 on Ubuntu

```bash

cd ~/

wget https://dlcdn.apache.org/maven/maven-3/3.9.4/binaries/apache-maven-3.9.4-bin.tar.gz

sudo tar xzvf apache-maven-3.9.4-bin.tar.gz -C/opt/

echo 'export MAVEN_HOME=/opt/apache-maven-3.9.4' >> ~/.bashrc

echo 'export PATH=$MAVEN_HOME/bin:$PATH' >> ~/.bashrc

source ~/.bashrc

mvn --version

```

# Compile the code

```bash
mvn compile   
```

# Run the code

```bash
 mvn exec:java -Dexec.mainClass="org.example.App"
```