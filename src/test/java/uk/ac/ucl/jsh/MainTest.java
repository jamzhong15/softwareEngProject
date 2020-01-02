package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class MainTest {

    // wrong no. of args (./jsh x)
    @Test
    public void MainWrongNoOfArgsTest() throws Exception {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String[] testString = {"-c"};
        Jsh.main(testString);
        assertEquals("jsh: wrong number of arguments\n", outContent.toString());
    }

    // unexpected args and unexpected app
    @Test
    public void MainUnexpectedArgAndUnexpecteAppTest() throws Exception {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String[] testString = {"x", "hi"};
        Jsh.main(testString);    
        assertEquals("jsh: x: unexpected argument\njsh: hi: unknown application\n", outContent.toString());
    }

    // unknown app
    @Test
    public void MainUnknownAppTest() throws Exception {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String[] testString = {"-c", "hi"};
        Jsh.main(testString);
        assertEquals("jsh: hi: unknown application\n", outContent.toString());
    }

    // unexpected args only
    @Test
    public void MainUnexpectedArgsTest() throws Exception {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String[] testString = {"x", "echo"};
        Jsh.main(testString);
        assertEquals("jsh: x: unexpected argument\n\n", outContent.toString());
    }

    // unexpected args
    // @Test
    // public void MainNormalTest() throws Exception {
    //     ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    //     System.setOut(new PrintStream(outContent));
    //     String[] testString = {};
    //     Jsh.main(testString);
    //     assertEquals("Welcome to JSH!\n", outContent.toString());
    // }
}

