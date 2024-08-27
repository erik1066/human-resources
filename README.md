[![pipeline status](https://gitlab.com/biohazard501/human-resources/badges/master/pipeline.svg)](https://gitlab.com/biohazard501/human-resources/commits/master)

# Description

This project contains the web UI for a human resources software product named **Manager Ninja**. Manager Ninja is all about employee management and engagement. Conduct employee reviews, see employee reviews if you're a supervisor, post and reply to messages on an internal messaging system to stay connected, and see what your company is up to. It's kind of like a social network - but for your company.

This was a university project done in fall 2017.

All front-end code is written in AngularJS with a backend API server written in Java 8 using Spring Boot. The app is deployed to an AWS ECS task and uses DynamoDB for persistence.

## How to build it?
Navigate to the human-resources folder in a terminal. For macOS and Linux users, run:

```
mvn clean package
```

For Windows users, you must use `mvnw` instead of `mvn` like, such:

```
mvnw clean package
```

Once this is done executing, the code has been successfully compiled, the unit tests have executed and, if there are no failing tests, a JAR file will be produced in the /target folder.

## How to run it?

Once you have run the `clean package` command as described in the previous section, run the following command (for macOS and Linux):

```
mvn spring-boot:run
```

For Windows users, again you must use `mvnw` instead of `mvn`:

```
mvnw spring-boot:run
```

This tells the app to start. Open http://localhost:8080 in a web browser. The app should appear in the browser if everything has completed successfully. See the next section for debugging the application within an IDE.

## How to open it in an IDE?

Using [Visual Studio Code](https://code.visualstudio.com/), navigate to **File** -> **Open Folder** and select the human-resources folder that was created when you ran `git clone`. The Java files are located in folders underneath /src/main/java. The html and JavaScript files are located in folders in and underneath /src/main/resources/static.

You will need two Visual Studio Code extensions to debug the project: Microsoft's [Java Debug Extension](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-debug) and Red Hat's [Language Support for Java](https://marketplace.visualstudio.com/items?itemName=redhat.java). These can be installed to Visual Studio Code by clicking on the Extensions button on the Visual Studio Code sidebar.

Once the two extensions have been added, select the Debug button on the Visual Studio Code sidebar and click the green Debug button. The application should launch in debug mode in a few seconds. 

Other IDEs, such as [Spring Tool Suite](https://spring.io/tools), are optimized for Java Spring applications and may work better than Visual Studio Code.
