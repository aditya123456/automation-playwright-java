# Test Automation Project Setup Guide

This guide outlines the steps to set up a test automation project using Java, Maven, IntelliJ IDE, and Git.

## Step 1: Install Java Development Kit (JDK)

1. Download the latest version of JDK from [Oracle's website](https://www.oracle.com/java/technologies/downloads/#jdk21-windows).
2. Follow the installation instructions provided on the website.
3. Set up the environment variable `JAVA_HOME` to point to the JDK installation directory.

## Step 2: Install Apache Maven

1. Download Maven from the [official Apache Maven website](https://maven.apache.org/download.cgi).
2. Extract the downloaded Maven archive to a preferred location, e.g., `C:\apache-maven-3.9.6-bin\apache-maven-3.9.6`.
3. Add the Maven `bin` directory (`C:\apache-maven-3.9.6-bin\apache-maven-3.9.6\bin`) to the system's PATH environment variable.

## Step 3: Install IntelliJ IDEA

1. Download IntelliJ IDEA from the [JetBrains website](https://www.jetbrains.com/idea/download/?section=windows).
2. Run the downloaded installer and follow the installation wizard's instructions.
3. Open IntelliJ IDEA and configure any necessary settings.

## Step 4: Clone the Git Project

1. Open a terminal or command prompt.
2. Clone the Git project using the command:

git clone https://github.com/aditya123456/automation-playwright-java.git
3. Navigate to the cloned project directory:

cd dta-sdet-aditya/test-automation


## Step 5: Build the Project with Maven

1. In the terminal or command prompt, navigate to the project directory.
2. Execute the Maven command to clean and install the project dependencies:

mvn clean install

## Step 6: Checking the reports
Go to target/surefire-report

Open the index.html file
It will give you all the results.


Congratulations! You have successfully set up the test automation project. You can now start developing and running automated tests using Java, Maven, and IntelliJ IDEA.


