package uk.ac.ucl.jsh;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

public class PwdTest {
    @Test
    public void pwdTest() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Jsh.start("pwd", out);
        Scanner scn = new Scanner(in);
        Jsh jsh = new Jsh();
        String currentDirectory = jsh.getcurrentDirectory();
        assertEquals(scn.next() , currentDirectory);
        scn.close();
    }

}