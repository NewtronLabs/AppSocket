# App Socket

The App Socket library allows for the creation of sockets between Android applications. This allows for the creation of Client-Server style applications among Android applications. Once the socket are connected, applications can share data with one another over these sockets.

<p align="center">
  <img src="https://github.com/NewtronLabs/AppSocket/blob/master/Diagram.png" width="80%" height="80%" >
</p>

----


## How to Use 

### Setup

Include the below dependencies in your `build.gradle` project.

```gradle
buildscript {
    repositories {
        jcenter()
        maven { url "http://code.newtronlabs.com:8081/artifactory/libs-release-local" }
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.5.2'
        classpath 'com.newtronlabs.android:plugin:4.0.0'
    }
}

allprojects {
    repositories {
        jcenter()
        maven { url "http://code.newtronlabs.com:8081/artifactory/libs-release-local" }
    }
}

subprojects {
    apply plugin: 'com.newtronlabs.android'
}
```

In the `build.gradle` for your app.

```gradle
dependencies {
    compileOnly 'com.newtronlabs.appsocket:appsocket:4.0.0'
}
```

### App Socket - Server
From the server application create a server socket and listen for clients.

```java
// Create a server socket on port 2222
IAppServerSocket serverSocket = new AppServerSocket(2222)

// Wait for client to connect
IAppSocket clientSocket = serverSocket.accept();

// Get stream for writing to client.
OutputStream os = clientSocket.getOutputStream();

// Get stream for reading from the client
InputStram is = clientSocket.getInputStream();
```

### App Socket - Client
In order for an Android application to communicate with server app do the following:

```java
// This is the application id of the application that has the server socket.
String serverAppId = "com.newtronlabs.appsockserver";

// This is the port on which that app is listening on.
int port = 2222;

// Create the socket.
IAppSocket socket = new AppSocket();

// Connect to remote application.
socket.connect(context, serverAppId, port);

// Get stream for writing to the server.
OutputStream os = clientSocket.getOutputStream();

// Get stream for reading from the server
InputStram is = clientSocket.getInputStream();

```

### Additional Samples
A set of more complex exmaples can be found in this repo's samples folders: **AsServer** and **AsClient**. 

## License
https://gist.github.com/NewtronLabs/216f45db2339e0bc638e7c14a6af9cc8

*Patent Pending*

## Contact

solutions@newtronlabs.com
