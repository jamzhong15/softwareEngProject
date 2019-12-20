package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class HeadTest {
    // head 1 filename argument test  (something wrong here)
    @Test
    public void HeadOneFileNameArgumentTest() throws Exception {
        String data1 = "first line\n";
        String data2 = "second line";
        FileOutputStream file_writer = new FileOutputStream("head_test.txt");
        file_writer.write(data1.getBytes());
        file_writer.write(data2.getBytes());
        file_writer.close();

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Jsh.start("head head_test.txt", out);
        Scanner scn = new Scanner(in);
        assertEquals("first line", scn.nextLine());
        scn.close();

        File file = new File("head_test.txt");
        file.delete();
    }

    // head 3 arguments test sth wrong here also
    @Test
    public void HeadThreeArgumentsTest() throws Exception {
        String data1 = "first line\n";
        String data2 = "second line";
        FileOutputStream file_writer = new FileOutputStream("head_test.txt");
        file_writer.write(data1.getBytes());
        file_writer.write(data2.getBytes());
        file_writer.close();

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Jsh.start("head -n 1 head_test.txt", out);
        Scanner scn = new Scanner(in);
        assertEquals("first line", scn.nextLine());
        scn.close();

        File file = new File("head_test.txt");
        file.delete();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // head no argument test
    @Test
    public void HeadMissingArgumentThrowsException() throws RuntimeException, IOException {
    PrintStream console = null;

    console = System.out;
    thrown.expect(RuntimeException.class);
    thrown.expectMessage("head: missing argument");
    Jsh.start("head", console);
    }

    // Head wrong no. of arguments
    @Test
    public void HeadWrongArgumentNumberThrowsException() throws RuntimeException, IOException {
        PrintStream console = null;
    
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("head: wrong arguments");
        Jsh.start("head -n Dockerfile", console);
        }
    
    // head 3 argument but first one is not -n
    @Test
    public void HeadThreeArgumentsWithWrongFirstArgumentThrowsException() throws RuntimeException, IOException {
        PrintStream console = null;
    
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("head: wrong argument -s");
        Jsh.start("head -s 3 Dockerfile", console);
        }

    // head 3 argument but second argument is not number
    @Test
    public void HeadThreeArgumentsWithWrongSecondArgumentThrowsException() throws RuntimeException, IOException {
        PrintStream console = null;
    
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("head: wrong argument s");
        Jsh.start("head -n s Dockerfile", console);
        }

    // head file does not exist
    @Test
    public void HeadFileDoesNotExistThrowsException() throws RuntimeException, IOException {
        PrintStream console = null;
    
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("head: xxx does not exist");
        Jsh.start("head -n 3 xxx", console);
        }
    
    // head cannot open file
    @Test
    public void HeadCannotOpenFileThrowsException() throws RuntimeException, IOException {
        PrintStream console = null;
    
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("head: cannot open target");
        Jsh.start("head target", console);
        }
}