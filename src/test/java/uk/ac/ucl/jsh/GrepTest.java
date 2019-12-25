package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.hamcrest.CoreMatchers;


public class GrepTest {
    // grep with 2 arguments
    @Test
    public void GrepWIthTwoArgumentsTest() throws Exception {
        Jsh jsh = new Jsh();
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        jsh.start("grep pwd test", out);
        Scanner scn = new Scanner(in);
        assertEquals("JSH_ROOT=\"$( cd \"$( dirname \"${BASH_SOURCE[0]}\" )\" >/dev/null 2>&1 && pwd )\"", scn.nextLine());
        scn.close();
    }

    // grep with 1 argument (match argument with standin)
    @Test
    public void GrepWithOneArgumentTest() throws Exception {
        Jsh jsh = new Jsh();
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        jsh.start("cat test | grep JSH_ROOT", out);
        Scanner scn = new Scanner(in);
        assertEquals("JSH_ROOT=\"$( cd \"$( dirname \"${BASH_SOURCE[0]}\" )\" >/dev/null 2>&1 && pwd )\"", scn.nextLine());
        scn.close();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // wrong file argument
    @Test
    public void GrepWrongFileArgumentTest() throws Exception {
        Jsh jsh = new Jsh();
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("grep: wrong file argument"));
        jsh.start("grep xx xx", System.out);
    }

   // missing arguments
    @Test
    public void GrepMissingArgumentTest() throws Exception {
        Jsh jsh = new Jsh();
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("grep: missing arguments"));
        jsh.start("grep", System.out);
    }

    // cannot open file 2
    // @Test
    // public void GrepCannotOpenFileTest() throws Exception {
    //     Jsh jsh = new Jsh();
    //     PrintStream console = null;

    //     console = System.out;
    //     thrown.expect(RuntimeException.class);
    //     thrown.expectMessage(CoreMatchers.equalTo("grep: cannot open jsh-1.0-SNAPSHOT-jar-with-dependencies.jar"));
    //     jsh.start("cd target ; grep x jsh-1.0-SNAPSHOT-jar-with-dependencies.jar", console);
    // }

    // @After
    // public void resetDirectory()
    // {
    //     Jsh jsh = new Jsh();
    //     jsh.setcurrentDirectory(System.getProperty("user.dir"));
    // }
}



