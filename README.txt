# Companies,Employees and Devices Full Stack Management System

## Architecture

Frontend : Angular
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
