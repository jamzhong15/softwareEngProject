package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class TailTest {
    //tail 1 filename argument test  (something wrong here)
    // @Test
    // public void TailOneFileNameArgumentTest() throws Exception {
    //     PipedInputStream in = new PipedInputStream();
    //     PipedOutputStream out;
    //     out = new PipedOutputStream(in);
    //     Jsh.start("tail p.txt", out);
    //     Scanner scn = new Scanner(in);
    //     assertEquals("hello there", scn);
    //     scn.close();
    // }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // tail no argument test
    @Test
    public void TailMissingArgumentThrowsException() throws RuntimeException, IOException {
    PrintStream console = null;
    console = System.out;
    thrown.expect(RuntimeException.class);
    thrown.expectMessage("tail: missing arguments");
    Jsh.start("tail", console);
    }

    // tail wrong no. of arguments
    @Test
    public void TailWrongArgumentNumberThrowsException() throws RuntimeException, IOException {
        PrintStream console = null;
    
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("tail: wrong arguments");
        Jsh.start("tail -n Dockerfile", console);
        }
    
    // tail 3 argument but first one is not -n
    @Test
    public void TailThreeArgumentsWithWrongFirstArgumentThrowsException() throws RuntimeException, IOException {
        PrintStream console = null;
    
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("tail: wrong argument -s");
        Jsh.start("tail -s 3 Dockerfile", console);
        }

    // tail 3 argument but second argument is not number
    @Test
    public void TailThreeArgumentsWithWrongSecondArgumentThrowsException() throws RuntimeException, IOException {
        PrintStream console = null;
    
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("tail: wrong argument s");
        Jsh.start("tail -n s Dockerfile", console);
        }

    // tail file does not exist
    @Test
    public void TailFileDoesNotExistThrowsException() throws RuntimeException, IOException {
        PrintStream console = null;
    
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("tail: xxx does not exist");
        Jsh.start("tail -n 3 xxx", console);
        }
    
    // tail cannot open file
    @Test
    public void TailCannotOpenFileThrowsException() throws RuntimeException, IOException {
        PrintStream console = null;
    
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("tail: cannot open target");
        Jsh.start("tail target", console);
        }
}