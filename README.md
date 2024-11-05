# Polynesian Navigation Route Planner

This assignment for ICS311 is designed to simulate resource distribution, knowledge sharing, and tourism across a network of islands inside of the Polynesian Triangle. By using graph algorithms, the program correctly models interconnected islands and has effecient planning for resource distribution, knowledge sharing, and tourism routes.

## Table of Contents

- [Assignment Structure](#assignment-structure)
- [Setup](#setup)
- [How to Run](#how-to-run)

## Assignment Structure
This assignment is divided into separate modules to manage different aspects of the island network and the individual problems:
- **src/core**: Core classes that define the main graph structure and island-related data.
    - `Graph.java`: Defines the graph structure, representing the islands as vertices and time to travel as edges (non-negative).
    - `Island.java`: Represents an island, includes data such as: name, population and resources.
    - `Route.java`: Represents a route between two islands with travel time.
- **src/problems**: Contains classes for individually solved problems
    - `Problem1.java`: Knowledge sharing problem - will calculate optimal routes for leader visits.
    - `Problem2.java`: Resource distribution problem - will determine efficient routes for resource distribution.
    - `Problem3.java`: Crop distribution problem - will efficiently distribute a certain type of crop on one island to the rest of the islands.
- **src/Main.java**: This is the main entry point for the program, contains coordinating problem execution and loading graph data.
- **data/island-data.txt**: A sample dataset for testing purposes, contains island and route information.

## Setup

To run this project locally, follow these steps:

1. Clone the repository:

    ```bash
    git clone https://github.com/jstnsmth/ics311-assignment5.git
    ```

2. Navigate to the project directory:

    ```bash
    cd ics311-assignment5
    ```

3. Compile the code:

    ```bash
    javac src/*.java src/core/*.java src/problems/*.java
    ```

Make sure Java is installed, preferably version 8 or higher.

## How to Run

Run the `Main` class to execute the program. The `Main` file initializes the graph data and runs each problem module:

```bash
java src.Main
```
