package com.example.model;

import com.example.exception.MazeFileException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class MazeTest {

    // class under test
    private Maze underTest;

    private final static String VALID_MAZE_FILE = "sample_maze.txt";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    @Test
    public void shouldCreateValidMaze() {
        underTest = Maze.create(getFile(VALID_MAZE_FILE));
        assertNotNull(underTest);
        assertEquals(7, underTest.getHeight());
        assertEquals(7, underTest.getWidth());
        assertNotNull(underTest.getStartCoordinates());
        assertNotNull(underTest.getEndCoordinates());
    }

    @Test
    public void shouldIgnoreBlankLinesInMaze() {
        underTest = Maze.create(getFile(VALID_MAZE_FILE));
        assertNotNull(underTest);
        assertEquals(7, underTest.getHeight());
        assertEquals(7, underTest.getWidth());
    }

    @Test()
    public void shouldThrowExceptionOnInvalidFileName() {
        expectedException.expect(MazeFileException.class);
        expectedException.expectMessage("Invalid file name/location.");

        underTest = Maze.create(getFile("foo.txt"));
    }

    @Test
    public void shouldThrowExceptionOnEmptyMazeFile() {
        expectedException.expect(MazeFileException.class);
        expectedException.expectMessage("Maze input file is empty.");

        underTest = Maze.create(getFile("empty_file.txt"));
    }

    @Test
    public void shouldFailWithoutStartingPoint() {
        expectedException.expect(MazeFileException.class);
        expectedException.expectMessage("Maze does not contain starting point (S)");

        underTest = Maze.create(getFile("maze_with_missing_start_char.txt"));
    }

    @Test
    public void shouldFailWithoutEndPoint() {
        expectedException.expect(MazeFileException.class);
        expectedException.expectMessage("Maze does not contain end point (E)");

        underTest = Maze.create(getFile("maze_with_missing_end_char.txt"));
    }

    @Test
    public void shouldGiveValidStartLocation() {
        underTest = Maze.create(getFile(VALID_MAZE_FILE));
        assertThat(underTest.getStartCoordinates(), is(new Coordinate(5,1)));
    }

    @Test
    public void shouldGiveValidEndLocation() {
        underTest = Maze.create(getFile(VALID_MAZE_FILE));
        assertThat(underTest.getEndCoordinates(), is(new Coordinate(3,3)));
    }

    @Test
    public void shouldFailWhenMultipleStartPoints() {
        expectedException.expect(MazeFileException.class);
        expectedException.expectMessage("Maze contains more than one starting point (S)");

        underTest = Maze.create(getFile("maze_with_duplicate_start_char.txt"));
    }

    @Test
    public void shouldFailWhenMultipleEndPoints() {
        expectedException.expect(MazeFileException.class);
        expectedException.expectMessage("Maze contains more than one end point (E)");

        underTest = Maze.create(getFile("maze_with_duplicate_end_char.txt"));
    }

    @Test
    public void shouldSucceedIfLocationISAWall() {
        underTest = Maze.create(getFile(VALID_MAZE_FILE));
        assertTrue(underTest.isWall(3, 2));
    }

    @Test
    public void shouldSucceedIfLocationIsNOTAWall() {
        underTest = Maze.create(getFile(VALID_MAZE_FILE));
        assertFalse(underTest.isWall(5, 1));
    }

    @Test
    public void shouldFailWhenInvalidWallCoordinates() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Invalid coordinates supplied. They must be within Maze boundaries.");

        underTest = Maze.create(getFile(VALID_MAZE_FILE));
        underTest.isWall(7, 8);
    }

    @Test
    public void shouldPassWhenWithinBounds() {
        underTest = Maze.create(getFile(VALID_MAZE_FILE));
        assertTrue(underTest.areWithinMazeBounds(6, 6));
    }

    @Test
    public void shouldFailWhenNOTInBounds() {
        underTest = Maze.create(getFile(VALID_MAZE_FILE));
        assertFalse(underTest.areWithinMazeBounds(6, 7));
    }

    @Test
    public void shouldReturnTrueWhenUsingVisitedFlag() {
        underTest = Maze.create(getFile(VALID_MAZE_FILE));
        underTest.setVisited(3, 1, true);

        assertTrue(underTest.hasBeenVisited(3, 1));
    }

    @Test
    public void shouldFailWhenInvalidLocationForSetVisitedFlag() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Invalid coordinates supplied. They must be within Maze boundaries.");

        underTest = Maze.create(getFile(VALID_MAZE_FILE));
        underTest.setVisited(10, 1, true);
    }

    @Test
    public void shouldFailWhenInvalidLocationForHasVisitedFlag() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Invalid coordinates supplied. They must be within Maze boundaries.");

        underTest = Maze.create(getFile(VALID_MAZE_FILE));
        underTest.hasBeenVisited(10, 1);
    }

    @Test
    public void shouldFailWhenBadContentInMazeFile() {
        expectedException.expect(StringIndexOutOfBoundsException.class);
        underTest = Maze.create(getFile("bad_content_maze.txt"));
    }

    private File getFile(final String fileName) {
        return new File("src/test/resources/maze-test-input-files/" + fileName);
    }
}
