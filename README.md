# Movie Viewer Application

## Description
This Java application displays a list of popular movies fetched from The Movie Database (TMDb) API. It provides a graphical user interface (GUI) to browse through movie posters, view details about selected movies, and navigate between them.

## Features
- Fetches a list of popular movies from TMDb.
- Displays movie posters in a GUI.
- Allows navigation through the list of movies with "Previous" and "Next" buttons.
- Shows detailed information about a selected movie in a separate window.

## Requirements
- Java Development Kit (JDK) 8 or higher.
- Dependencies managed via Maven or manually.

## Setup

### Prerequisites
- Obtain an API key from [TMDb](https://www.themoviedb.org/).
- Replace `YOUR_API_KEY` in the code with your actual API key.

### Build and Run
1. Ensure JDK is installed on your machine.
2. Compile the Java code:
    ```bash
    javac devoir_4.java
    ```
3. Run the application:
    ```bash
    java devoir_4
    ```

## Dependencies
This project uses the following libraries:
- `JSON.simple` for parsing JSON data.
- `ImageIO` for handling images.

Ensure these libraries are available in your classpath.

## Author
- **Lee J. Vandersar St Cyr**
