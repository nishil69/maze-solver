# Maze Solver


## Summary

**Maze Solver** is a Java 8 based project.
 It will process a **source** maze input file and attempt to solve the maze and output the maze on the **std** console.


## Technologies

Java 8, JUnit, Lombok plugin, and **Maven exec** plugin to package up project and its dependencies in a single executable jar file. 
It was developed using IntelliJ and it builds and runs both from intelliJ and the command line.

## Design/Code

There are several algorithms which are used to solve this problem. I referred to [Wikipedia](https://en.wikipedia.org/wiki/Maze_solving_algorithm) for inspiration.

I used Depth First Search (DFS) algorithm as it gives the benefit of not having to declare and maintain an explicit stack as it backtracks.

The **Solver** interface is provided to demonstrate that several other "solver" implementations can be provided e.g. DFSSolver being just one of them.

The **Main** class is the entry point when you run the application. This is provided as a demonstrator and uses the
sample file provided with the exercise as **default** (it is in src/main/resources folder). 
Alternative, you can supply a 'maze input file' with the full path as an **argument on the command line**. 

## Build

Project is built using Maven 3.x (see pom.xml in the project folder). 

To build the project, use standard **mvn install** or IntelliJ Maven Plugin. 

## Run

To run the app, you can either use the command line or IntelliJ with the main method in the Main class file.
You can run from the command line using **mvn exec:java** in the project folder (where the pom file is).
**NOTE:** If you wish to supply an alternative maze source file, you can provide the file path and name as the first command line argument.
E.g. **mvn exec:java -Dexec.args="path_and_name_of_your_file"** (with quotes included). If you had
a 'test.txt' file in the project folder, you can simply provide -Dexec.args="test.txt" as indicated earlier.

## Tests 

Unit tests are provided and it uses JUnit and some HamCrest matchers.
I did not use Mockito for mocking dependency object but injected actual instance to keep code smaller.

## Assumptions/Exclusions/Other Considerations

- Basic exception handler class for demonstration - by no means fully comprehensive.
- Minor assumptions made about the Maze format and is added as a comment in the **Maze** class file.
- Maze characters e.g. #, S etc can be made configurable but we have hardcoded them in this instance.
- We use Lombok just to keep the model class free of boiler plate code.
- Where deemed helpful comments are provided at class/method level to explain decisions e.g. "prettyprint" method in the **Maze** class.
- This project does not deliberately use logging framework like slf4j to keep things simple.