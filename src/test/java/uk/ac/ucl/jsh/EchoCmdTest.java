package uk.ac.ucl.jsh;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

public class EchoCmdTest {
    @Test
    public void echoCmdTest() throws Exception {
        Jsh jsh = new Jsh();
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jsh.start("echo foo", out);
        Scanner scn = new Scanner(in);
        assertEquals("foo", scn.next());
        scn.close();
    }

    @Test
    public void echoEmptyCmdTest() throws Exception {
        Jsh jsh = new Jsh();
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jsh.start("echo", out);
        Scanner scn = new Scanner(in);
        assertEquals("", scn.nextLine());
        scn.close();
    }

    @Test
    public void echoCmdMultipleArgumentIncludingQuoted() throws Exception
    {
        Jsh jsh = new Jsh();
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jsh.start("echo \"hello world\" HELLO WORLD \"Hi World\"", out);
        Scanner scn = new Scanner(in);
        assertEquals("hello world HELLO WORLD Hi World", scn.nextLine());
        scn.close();
    }
}