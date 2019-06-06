package com.example;

import com.example.model.Coordinate;
import com.example.model.Maze;
import com.example.solver.DFSSolver;
import com.example.solver.Solver;

import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        String fileName = args.length >= 1 ? args[0] : "src/main/resources/exercise_sample_maze.txt";
        final char PATH_INDICATOR = '.';

        final Solver solver = new DFSSolver();
        // load maze and create instance
        final Maze maze = Maze.create(new File(fileName));

        // solve
        List<Coordinate> solution =  solver.solve(maze);

        maze.prettyPrintWithSolution(solution, PATH_INDICATOR);

    }
}
