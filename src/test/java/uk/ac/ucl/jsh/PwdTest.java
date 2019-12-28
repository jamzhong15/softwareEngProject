package uk.ac.ucl.jsh;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

public class PwdTest {
<<<<<<< HEAD
    @Test
    public void pwdTest() throws IOException {
        Jsh jsh = new Jsh();
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        jsh.start("pwd", out);
        Scanner scn = new Scanner(in);
        String currentDirectory = jsh.getcurrentDirectory();
        assertEquals(currentDirectory, scn.next());
        scn.close();
    }
=======
    // @Test
    // public void pwdTest() throws IOException {
    //     Jsh jsh = new Jsh();
    //     PipedInputStream in = new PipedInputStream();
    //     PipedOutputStream out;
    //     out = new PipedOutputStream(in);
    //     jsh.start("pwd", out);
    //     Scanner scn = new Scanner(in);
    //     String currentDirectory = jsh.getcurrentDirectory();
    //     assertEquals(currentDirectory, scn.next());
    //     scn.close();
    // }
>>>>>>> f4b3b170a0085c31fe86f446a247b23def254c2b

}