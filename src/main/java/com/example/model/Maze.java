package com.example.model;

import com.example.exception.MazeFileException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;

public final class Maze {

    private static final char START = 'S';
    private static final char END = 'E';
    private static final char WALL = '#';
    private static final char OPEN = ' ';

    private final char[][] grid;
    private final Coordinate start;
    private final Coordinate end;
    private final boolean[][] visited;


    /**
     * A method to create a {@link Maze} object instance by loading rows from a text file.
     *  NOTE: Any blank lines in the input file are ignored and we assume that that all lines are SAME length
     *  and that each row start and ends with a '#' character. So we do not do any additional file content validation.
     *  @Returns A reference to the {@link Maze} object containing the 2D grid.
     **/
    public static Maze create(File filename) {

        try(Scanner in = new Scanner(filename)) {

            Deque<String> q = new ArrayDeque<String>();
            while (in.hasNextLine()) {
                String line = in.nextLine();
                if (StringUtils.isNotBlank(line)) {
                    q.add(line);
                }
            }

            if (q.isEmpty()) {
                throw new MazeFileException("Maze input file is empty.");
            }

            int height = q.size();
            int width = q.element().length();
            char[][] grid = new char[height][width];
            Coordinate start = null;
            Coordinate end = null;

            for (int row = 0; row < height; row++) {
                String s = q.remove();
                for (int col = 0; col < width; col++) {
                    char ch = s.charAt(col);
                    if (START == ch) {
                        if (start != null) {
                            throw new MazeFileException("Maze contains more than one starting point (S)");
                        }
                        start = new Coordinate(row, col);
                    } else if (END == ch) {
                        if (end != null) {
                            throw new MazeFileException("Maze contains more than one end point (E)");
                        }
                        end = new Coordinate(row, col);
                    }
                    grid[row][col] = ch;
                }
            }

            checkContainStartAndEnd(start, end);

            return new Maze(grid, start, end);

        } catch (FileNotFoundException e) {
           throw new MazeFileException("Invalid file name/location.");
        }
    }

    private Maze(final char[][] grid, final Coordinate start, final Coordinate end) {
        this.grid = grid;
        this.start = start;
        this.end = end;
        this.visited = new boolean[getHeight()][getWidth()];
    }

    public int getHeight() {
        return grid.length;
    }

    public int getWidth() {
        return grid[0].length;
    }

    public Coordinate getStartCoordinates() {
        return new Coordinate(start.getX(), start.getY());
    }

    public Coordinate getEndCoordinates() {
        return new Coordinate(end.getX(), end.getY());
    }

    public boolean hasBeenVisited(int row, int col) {
        checkInRange(row, col);
        return visited[row][col];
    }

    public void setVisited(int row, int col, boolean value) {
        checkInRange(row, col);
        visited[row][col] = value;
    }

    public boolean isWall(int row, int col) {
        checkInRange(row, col);
        return grid[row][col] == WALL;
    }

    public boolean areWithinMazeBounds(int row, int col) {
       return  row >= 0 && row < getHeight()  && col >= 0 && col < getWidth();
    }

    /**
     * Method to 'pretty print' the maze with the supplied solution and path indicator.
     * Note: method exists for ease for exercise.
     * Usually any printing etc. would be done outside this model class and should be
     * a separate concern. We should be simply return a copy of the maze 'grid' and potentially
     * formatting/displaying would be responsibility of the user of the class.
     * @param solution
     * @param pathChar
     */
    public void prettyPrintWithSolution(final List<Coordinate> solution, final char pathChar) {

        final StringBuilder output = new StringBuilder();
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col++) {
                char current = grid[row][col];
                if (START == current) {
                    output.append(START);
                } else if (END == current) {
                    output.append(END);
                } else if (WALL == current) {
                    output.append(WALL);
                } else if (OPEN == current) {
                    final Coordinate currCoord = new Coordinate(row, col);
                    if (solution.contains(currCoord)) {
                        output.append(pathChar);
                    } else {
                        output.append(OPEN);
                    }
                }
            }
            output.append('\n');
        }

        System.out.println("=== Maze solution (if any) below ===\n" + output.toString());
    }

    private static void checkContainStartAndEnd(Coordinate start, Coordinate end) {

        if (start == null) {
            throw new MazeFileException("Maze does not contain starting point (S)");
        }

        if (end == null) {
            throw new MazeFileException("Maze does not contain end point (E)");
        }
    }

    private void checkInRange(int row, int col) {
        if (!areWithinMazeBounds(row, col)) {
            throw new IllegalArgumentException("Invalid coordinates supplied. They must be within Maze boundaries.");
        }
    }
}
