package com.example.solver;

import com.example.model.Coordinate;
import com.example.model.Maze;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

// Note: Normally one would consider using Mockito or similar for mocking collaborator objects.
// we create actual instance  for demonstration and to keep test methods shorter and an alternative approach etc.
public class DFSSolverTest {

    private final static char PATH_INDICATOR = '.';

    // class under test
    private static DFSSolver underTest;

    // collaborator
    private Maze maze;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void setUp() {
       underTest = new DFSSolver();
    }

    @Test
    public void ShouldThrowExceptionOnNullMaze() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("You must load the maze first.");
        underTest.solve(null);
    }

    @Test
    public void shouldFindSolutionWhenGoingUp() {
        maze = Maze.create(getFile("exercise_sample_maze.txt"));
        assertNotNull(maze);

        final List<Coordinate> solution =  underTest.solve(maze);
        assertEquals(9, solution.size());

        // we print in these tests for this excercise -
        // in case if anyone wants to see if tests passing visually on the command line
        // besides it's also a good test of the method!
        maze.prettyPrintWithSolution(solution, PATH_INDICATOR);
    }

    @Test
    public void shouldNotFindSolution() {
        maze = Maze.create(getFile("no_solution_maze.txt"));
        assertNotNull(maze);

        final List<Coordinate> solution =  underTest.solve(maze);
        assertEquals(0, solution.size());
        maze.prettyPrintWithSolution(solution, PATH_INDICATOR);
    }

    @Test
    public void shouldFindSolutionWhenGoingDown() {
        maze = Maze.create(getFile("topleft-to-bottomright-maze.txt"));
        assertNotNull(maze);

        final List<Coordinate> solution =  underTest.solve(maze);
        assertEquals(11, solution.size());

        maze.prettyPrintWithSolution(solution, PATH_INDICATOR);
    }

    @Test
    public void shouldFindSolutionWhenTurningLeft() {
        maze = Maze.create(getFile("turn_left_maze.txt"));
        assertNotNull(maze);

        final List<Coordinate> solution =  underTest.solve(maze);
        assertEquals(10,solution.size());

        maze.prettyPrintWithSolution(solution, PATH_INDICATOR);
    }

    @Test
    public void shouldFindSolutionForLargerMaze() {
        maze = Maze.create(getFile("different-size-maze.txt"));
        assertNotNull(maze);

        final List<Coordinate> solution =  underTest.solve(maze);
        assertEquals(13, solution.size());

        maze.prettyPrintWithSolution(solution, PATH_INDICATOR);
    }

    private File getFile(final String fileName) {

        return new File("src/test/resources/solver-test-input-files/" + fileName);
    }
}
