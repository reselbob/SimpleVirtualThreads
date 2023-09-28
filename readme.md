The evil code requires Java 11 and Maven 3.9.4.

The code requires Java 21 and Maven 3.9.4.

# Install Java 11 on Ubuntu

```bash
sudo apt update -y
```

```bash
sudo apt install openjdk-11-jdk -y
```

```bash
java --version
```


# Installing Java 21 on Ubuntu

```bash
sudo apt update -y
```

```bash
sudo apt-get install wget -y
```

```bash
wget https://download.java.net/java/GA/jdk21/fd2272bbf8e04c3dbaee13770090416c/35/GPL/openjdk-21_linux-x64_bin.tar.gz
```

```bash
tar -xvf openjdk-21_linux-x64_bin.tar.gz
```

```bash
cd jdk-21
```

```bash
sudo mkdir -p /usr/local/jdk-21
```

```bash
sudo mv * /usr/local/jdk-21
```

```bash
echo 'export JAVA_HOME=/usr/local/jdk-21' >> ~/.bashrc
```

```bash
echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.bashrc
```

```bash
source ~/.bashrc
```

```bash
java --version
```

# Install Maven 3.9.4 on Ubuntu

```bash
cd ~/
```

```bash
wget https://dlcdn.apache.org/maven/maven-3/3.9.4/binaries/apache-maven-3.9.4-bin.tar.gz
```

```bash
sudo tar xzvf apache-maven-3.9.4-bin.tar.gz -C/opt/
```

```bash
echo 'export MAVEN_HOME=/opt/apache-maven-3.9.4' >> ~/.bashrc
```

```bash
echo 'export PATH=$MAVEN_HOME/bin:$PATH' >> ~/.bashrc
```

```bash
source ~/.bashrc
```

```bash
mvn --version
```

# Get the code

```bash
cd ~/
```

```bash
git clone https://github.com/reselbob/SimpleVirtualThreads.git
```

```bash
cd SimpleVirtualThreads
```

# Run the evil code under Java 11

```bash
cd Java11
```

```bash
mvn compile   
```

```bash
 mvn exec:java -Dexec.mainClass="org.example.App" | grep memory
```

You'll see lines of errors like so:

`Thread is running and the result is: Error making HTTP request: unable to create native thread: possibly out of memory or process/resource limits reached`

# Run the good code under Java 21

```bash
cd Java21
```

```bash
mvn compile   
```

```bash
 mvn exec:java -Dexec.mainClass="org.example.App" | grep memory
```

# Running code as containers

## Java 11 and Maven

```bash
cd Java11
```

```bash
docker build -t my-java-11-app -f Dockerfile .
```

```bash
docker run -it --rm -a stdout -a stderr my-java-11-app mvn -f /app/SimpleVirtualThreads/Java11/pom.xml  exec:java -Dexec.mainClass="org.example.App" | grep memory
```

## Java 21 and Maven

```bash
cd Java21
```

```bash
docker build -t my-java-21-app -f Dockerfile .
```

```bash
docker run -it --rm -a stdout -a stderr my-java-21-app mvn -f /app/SimpleVirtualThreads/Java21/pom.xml  exec:java -Dexec.mainClass="org.example.App" | grep memory
```

