package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CatTest {
    // cat with directory test (with bugs i guess)
    @Test
    public void catDirectoryExistAndCanOpenTest() throws Exception {
        Jsh jsh = new Jsh();
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);        
        jsh.start("cat test", out);
        Scanner scn = new Scanner(in);
        assertEquals("#!/bin/bash", scn.next());
        scn.close();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // cat with directory but cannot open bc not a file
    @Test
    public void catDirectoryExistButCannotOpenTest() throws Exception {
        Jsh jsh = new Jsh();
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("cat: cannot open src");
        jsh.start("cat src", console);
    }

    // cat no argument test
    @Test
    public void CatNoArgumentThrowsException() throws RuntimeException, IOException {
        Jsh jsh = new Jsh();
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("cat: missing arguments");
        jsh.start("cat", console);
    }

    // cat file not exist test
    @Test
    public void CatFileNotExistThrowsException() throws RuntimeException, IOException {
        Jsh jsh = new Jsh();
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("cat: file does not exist");
        jsh.start("cat xxx", console);
    }

}