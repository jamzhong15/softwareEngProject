package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FindTest {

    // find 2 args test
    @Test
    public void findTwoArgsTest() throws Exception {
        Jsh jsh = new Jsh();
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        jsh.start("find -name Dockerfile", out);
        Scanner scn = new Scanner(in);
        assertEquals("/Dockerfile", scn.next());
        assertEquals("/.devcontainer/Dockerfile", scn.next());
        scn.close();
    }

    // find 3 args test
    // @Test
    // public void findThreeArgsTest() throws Exception {
    //     Jsh jsh = new Jsh();
    //     PipedInputStream in = new PipedInputStream();
    //     PipedOutputStream out;
    //     out = new PipedOutputStream(in);
    //     jsh.start("find src -name *.g4", out);
    //     Scanner scn = new Scanner(in);
    //     assertEquals("/src/main/antlr4/uk/ac/ucl/jsh/CmdGrammar.g4", scn.nextLine());
    //     scn.close();
    // }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // find without argument (or 1 argument)
    @Test
    public void findAppArgsLessThanTwo() throws RuntimeException, IOException {
        Jsh jsh = new Jsh();
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("find: missing arguments"));
        jsh.start("find", console);
    }
    
    // find more than 3 arguments
    @Test
    public void findAppArgsMoreThanThree() throws RuntimeException, IOException {
        Jsh jsh = new Jsh();
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("find: too many arguments"));
        jsh.start("find x x x x", console);
    }

    // find 2 arguments but first arg is not -name
    @Test
    public void findTwoAppArgsButFirstArgsNot_nameThrowsException() throws RuntimeException, IOException {
        Jsh jsh = new Jsh();
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("find: invalid arguments"));
        jsh.start("find -namee x", console);
    }

    // find 3 arguments but second arg is not -name
    @Test
    public void findThreeAppArgsButSecondArgsNot_nameThrowsException() throws RuntimeException, IOException {
        Jsh jsh = new Jsh();
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("find: invalid arguments"));
        jsh.start("find x -namee x", console);
    }
}