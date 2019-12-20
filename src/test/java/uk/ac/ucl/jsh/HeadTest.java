package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class HeadTest {

    @Before
    public void buildTestFile() throws IOException
    {
        String absoluteFilePath = System.getProperty("user.dir") + File.separator + "head_test.txt";
        File testFile = new File(absoluteFilePath);
        ArrayList<String> testedStrings = new ArrayList<>();
        testedStrings.add("first line\n");
        testedStrings.add("second line");
        FileOutputStream file_writer = new FileOutputStream(testFile);
        for (String string : testedStrings)
        {
            file_writer.write(string.getBytes());
        }
        file_writer.close();
    }

    // head 1 filename argument test  (something wrong here)
    @Test
    public void HeadOneFileNameArgumentTest() throws Exception {
        Jsh jsh = new Jsh();

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        jsh.start("head head_test.txt", out);
        Scanner scn = new Scanner(in);
        assertEquals("first line", scn.nextLine());
        scn.close();
    }

    // head 3 arguments test sth wrong here also
    @Test
    public void HeadThreeArgumentsTest() throws Exception {
        Jsh jsh = new Jsh();

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        jsh.start("head -n 1 head_test.txt", out);
        Scanner scn = new Scanner(in);
        assertEquals("first line", scn.nextLine());
        scn.close();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // head no argument test
    @Test
    public void HeadMissingArgumentThrowsException() throws RuntimeException, IOException {
        Jsh jsh = new Jsh();
        PrintStream console = null;

        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("head: missing argument");
        jsh.start("head", console);
    }

    // Head wrong no. of arguments
    @Test
    public void HeadWrongArgumentNumberThrowsException() throws RuntimeException, IOException {
        Jsh jsh = new Jsh();
        PrintStream console = null;
    
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("head: wrong arguments");
        jsh.start("head -n Dockerfile", console);
    }
    
    // head 3 argument but first one is not -n
    @Test
    public void HeadThreeArgumentsWithWrongFirstArgumentThrowsException() throws RuntimeException, IOException {
        Jsh jsh = new Jsh();
        PrintStream console = null;
    
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("head: wrong argument -s");
        jsh.start("head -s 3 Dockerfile", console);
    }

    // head 3 argument but second argument is not number
    @Test
    public void HeadThreeArgumentsWithWrongSecondArgumentThrowsException() throws RuntimeException, IOException {
        Jsh jsh = new Jsh();
        PrintStream console = null;
    
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("head: wrong argument s");
        jsh.start("head -n s Dockerfile", console);
    }

    // head file does not exist
    @Test
    public void HeadFileDoesNotExistThrowsException() throws RuntimeException, IOException {
        Jsh jsh = new Jsh();
        PrintStream console = null;
    
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("head: xxx does not exist");
        jsh.start("head -n 3 xxx", console);
    }
    
    // head cannot open file
    @Test
    public void HeadCannotOpenFileThrowsException() throws RuntimeException, IOException {
        Jsh jsh = new Jsh();
        PrintStream console = null;
    
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("head: cannot open target");
        jsh.start("head target", console);
    }

    @After
    public void deleteTestFile()
    {
        File file = new File("head_test.txt");
        file.delete();
    }
}