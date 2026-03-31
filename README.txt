# Companies,Employees and Devices Full Stack Management System And Android App

## Architecture

Frontend : Angular
Android: Android Studio
Backend: Java Spring Boot
Database: MongoDB

## Characteristics

CRUD : GET , GET/{ID}, POST, PUT/{ID}, DELETE/{ID}
REST API
Testing 

## Requirements

You have to install before the execution the followings:

Java JDK 17+: Download from here (https://www.oracle.com/java/technologies/downloads/#java17) or here (https://adoptium.net/temurin/releases/?version=17)

Node.js(LTS): Download from (nodejs.org) (Choose the LTS version)

Maven : Download from here (https://maven.apache.org/download.cgi)

Angular CLI: Install via terminal after Node.js is ready, with command ('npm install -g @angular/cli')

MongoDB Community Server: Download from here (https://www.mongodb.com/try/download/community)

MongoDB Compass: Download from here (https://www.mongodb.com/try/download/compass)

IDE: IntelliJ IDEA (Recommended for Backend): Download from here (https://www.jetbrains.com/idea/download/?section=window) (Community edition is enough to run the project)
     VS Code (Recommended for Frontend): Download from here (https://code.visualstudio.com/)

Android Studio: Download from here(https://developer.android.com/studio)

## EXECUTION

1) Database setup
-Start MongoDB Service:
  Windows: Press Win + R, type services.msc, find MongoDB Server, and click Start/Running.
  Mac: Run 'brew services start mongodb-community'
-Open MongoDB Compass
-Create the new connection mongodb://localhost:27017
-Create a database name 'inventory-db'

2) Backend setup
-Open IntelliJ and select File > Open.
-Select the backend folder and import it.
-Wait for Maven to download all necessary dependencies.
-Click the Run button on the Application.java file.
-The server will start on http://localhost:8080

3) Frontend Setup
-Open VS Code and select File > Open Folder
-Select the frontend folder and import it
-Open a new terminal in VS Code
-Run 'npm install' to download dependencies
-Run 'ng serve' to start the app
-Open your browser at http://localhost:4200

4) Android Setup
- Open Android Studio and select File > Open
- Select the android folder of the project and import it
- Wait for Gradle toy sync and download all necessary dependencies
- Ensure that Gradle JDK is set to Java 17
- If you want to run the app in the emulator the URL is ready "http://10.0.2.2:8080/". If you want to run the app in your physical device go to app/src/main/java/com/example/inventoryapp/network/RetrofitClient.java
and change the URL to "http://<YOUR_PC_IP>:8080/" and ensure that both devices are on the same Wi-Fi.
