package com.example.solver;

import com.example.model.Maze;
import com.example.model.Coordinate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class DFSSolver implements Solver {

    private static final int[][] SHIFTS = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

    @Override
    public List<Coordinate> solve(final Maze maze) {

        if (maze == null) {
            throw new IllegalArgumentException("You must load the maze first.");
        }

        final List<Coordinate> solutionPath = new ArrayList<>();

        // recursively search for path from Start->End through the maze.
        if (!searchMaze(maze, maze.getStartCoordinates(), maze.getEndCoordinates(), solutionPath)){
            return Collections.emptyList();
        }

        return solutionPath;
    }

    private boolean searchMaze(final Maze maze,
                               final Coordinate current,
                               final Coordinate end,
                               final List<Coordinate> solutionPath) {

        if (!isFeasible(current, maze)) {
            return false;
        }

        // add current coords to possible solution path.
        solutionPath.add(current);
        // mark current cords as visited
        maze.setVisited(current.getX(), current.getY(), true);

        if (current.equals(end)) {
            return true;
        }

        // now we try other directions e.g up, right, down, left - by adding shift coords to current position.
        for (int[] s : SHIFTS) {
            if (searchMaze(maze,
                    new Coordinate(current.getX() + s[0], current.getY() + s[1]),
                    end,
                    solutionPath)) {
                return true;
            }
        }

        // if we are here, it means there isn't a valid path, so remove it from our solution list!
        solutionPath.remove(solutionPath.size() - 1);

        return false;
    }

    // check current is within the maze bounds and is a space (i.e. not blocked) square.
    private boolean isFeasible(final Coordinate current, final Maze maze) {

        final int currX = current.getX();
        final int currY = current.getY();

        return  maze.areWithinMazeBounds(currX, currY)
                && !maze.isWall(currX, currY)
                && !maze.hasBeenVisited(currX, currY);
    }
}
