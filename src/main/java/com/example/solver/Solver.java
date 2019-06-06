package com.example.solver;

import com.example.model.Maze;
import com.example.model.Coordinate;

import java.util.List;

public interface Solver {

    List<Coordinate> solve(Maze maze);
}
