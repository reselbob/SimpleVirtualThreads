# The Purpose of this Repository

The purpose of this repository to is to demonstrate running virtual threads under Java 21. The repository contains two Maven projects.

One project creates and runs threads under the Java 11 SDL. The other creates and runs virtual threads under Java 21.

Or course both the Java 11 and Java 21 projects can be run on the same machine by installing both the Java 11 and Java 21 SDKs on the same machine and configuring the projects according to the specific SDK version.

However, the easier way is to create to separate virtual machines installing Java 11 and Maven 3.9.4 on one machine and Java 21 and Maven 3.9.4 on the other virtual machine.

Another way is to run the specific SDK under a Docker container. Thus, you'll install two container images, one for Java 11 and the other image for Java 21. Then you'll run Docker containers according to those images respectively.

Both the dedicated virtual machine technique and the technique of running each SDK as a Docker container are described in the following sections.

# Running the demonstration projects on dedicated virtual machines
The following sections describe how to run the demonstration projects on dedicated virtual machines running Ubuntu. These instructions assume that you've created two virtual machines.


## Install Java 11 on a Ubuntu Virtual Machine

Once the virtual machines are created, install Java 11 on one of the machines using the instructions shown below.

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

On the other virtual machine install Java 21 using the instructions shown below.

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

Install Maven 3.9.4 on both virtual machines using the instructions shown below.

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

# Get the source code

Install the source code on both virtual machines using the following instructions.

```bash
cd ~/
```

```bash
git clone https://github.com/reselbob/SimpleVirtualThreads.git
```

```bash
cd SimpleVirtualThreads
```

# Run the Java 11 code

On the virtual machine running Java 11 go to the directory that has the source code for the Java 11 [here](./Java11) and execute the following instructions.

```bash
cd Java11
```

```bash
mvn compile   
```

```bash
 mvn exec:java -Dexec.mainClass="org.example.App"
```

You'll see error ouput similar to the following:

```bash
There is insufficient memory for the Java Runtime Environment to continue.
```

# Run the Java 21 code

On the virtual machine running Java 21 go to the directory that has the source code for the Java 21 [here](./Java21) and execute the following instructions.

```bash
cd Java21
```

```bash
mvn compile   
```

```bash
 mvn exec:java -Dexec.mainClass="org.example.App" 
```

Upon success you'll see the following output:

```bash
All threads under  have run under Java 21.
```

# Running code as containers

As mentioned above, you can run both the Java 11 and Java 21 code as Docker containers. The instructions for running containers for each Java SDK version follows.

## Running Java 11 and Maven in a Docker container

Run the following instructions to run the Java 11 source code from within a Docker container. Be advised that the Java 11 source code is installed within the Docker image. 

```bash
cd Java11
```

Create the Docker image:

```bash
docker build -t my-java-11-app -f Dockerfile .
```

Run the Docker container for Java 11 based on the image created above.

```bash
docker run -it --rm -a stdout -a stderr my-java-11-app mvn -f /app/SimpleVirtualThreads/Java11/pom.xml  exec:java -Dexec.mainClass="org.example.App"
```

## Running Java 21 and Maven in a Docker container

Run the following instructions to run the Java 21 source code from within a Docker container. Be advised that the Java 21 source code is installed within the Docker image. 

```bash
cd Java21
```

Create the Docker image:

```bash
docker build -t my-java-21-app -f Dockerfile .
```

Run the Docker container for Java 21 based on the image created above.

```bash
docker run -it --rm -a stdout -a stderr my-java-21-app mvn -f /app/SimpleVirtualThreads/Java21/pom.xml  exec:java -Dexec.mainClass="org.example.App"
```

