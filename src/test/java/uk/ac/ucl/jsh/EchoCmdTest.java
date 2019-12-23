package uk.ac.ucl.jsh;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

public class EchoCmdTest {
    

    @Test
    public void echoCmdTest() throws Exception {
        Jsh jsh = new Jsh();
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        jsh.start("echo foo", out);
        Scanner scn = new Scanner(in);
        assertEquals(scn.next(), "foo");
        scn.close();
    }
}