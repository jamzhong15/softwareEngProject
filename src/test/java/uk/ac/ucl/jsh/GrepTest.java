package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class GrepTest {
    // grep with 1 argument (match argument with standin)
    @Test
    public void GrepWithOneArgumentTest() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Jsh.start("cat test | grep JSH_ROOT", out);
        Scanner scn = new Scanner(in);
        assertEquals("JSH_ROOT=\"$( cd \"$( dirname \"${BASH_SOURCE[0]}\" )\" >/dev/null 2>&1 && pwd )\"", scn.nextLine());
        scn.close();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // wrong file argument
    @Test
    public void GrepWrongFileArgumentTest() throws Exception {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("grep: wrong file argument");
        Jsh.start("grep xx xx xx", System.out);
    }

   // wrong number of argument
    @Test
    public void GrepWrongNoOfArgumentTest() throws Exception {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("grep: wrong number of argument");
        Jsh.start("grep", System.out);
    }
}



