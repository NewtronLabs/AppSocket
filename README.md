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
        classpath 'com.android.tools.build:gradle:2.3.3'
        classpath "com.newtronlabs.android:plugin:1.1.0"
    }
}

allprojects {
    repositories {
        jcenter()
        maven { url "http://code.newtronlabs.com:8081/artifactory/libs-release-local" }
    }
}
```

In the `build.gradle` for your app.

```gradle
apply plugin: 'com.newtronlabs.android'

dependencies {
    provided 'com.newtronlabs.appsocket:appsocket:2.0.0'
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

App Socket binaries and source code can only be used in accordance with Freeware license. That is, freeware may be used without payment, but may not be modified. The developer of App Socket retains all rights to change, alter, adapt, and/or distribute the software. App Socket is not liable for any damages and/or losses incurred during the use of App Socket.

You may not decompile, reverse engineer, pull apart, or otherwise attempt to dissect the source code, algorithm, technique or other information from the binary code of App Socket unless it is authorized by existing applicable law and only to the extent authorized by such law. In the event that such a law applies, user may only attempt the foregoing if: (1) user has contacted Newtron Labs to request such information and Newtron Labs has failed to respond in a reasonable time, or (2) reverse engineering is strictly necessary to obtain such information and Newtron Labs has failed to reply. Any information obtained by user from Newtron Labs may be used only in accordance to the terms agreed upon by Newtron Labs and in adherence to Newtron Labs confidentiality policy. Such information supplied by Newtron Labs and received by user shall not be disclosed to a third party or used to create a software substantially similar to the technique or expression of the Newtron Labs App Socket software.

You are solely responsible for determining the appropriateness of using App Socket and assume any risks associated with Your use of App Socket. In no event and under no legal theory, whether in tort (including negligence), contract, or otherwise, unless required by applicable law (such as deliberate and grossly negligent acts) or agreed to in writing, shall Newtron Labs be liable to You for damages, including any direct, indirect, special, incidental, or consequential damages of any character arising as a result of this License or out of the use or inability to use the App Socket (including but not limited to damages for loss of goodwill, work stoppage, computer failure or malfunction, or any and all other commercial damages or losses), even if Newtron Labs has been advised of the possibility of such damages. 

*Patent Pending*

## Contact

contact@newtronlabs.com
