package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.Writer;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class TailTest {
    // tail 1 filename argument test something wrong here
    @Before
    public void createNewFile() throws IOException {
        String filePath = System.getProperty("user.dir") + File.separator + "testFile.txt";
        File file = new File(filePath);
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(file);
        String data1 = "first line\n";
        String data2 = "second line";
        fileWriter.write(data1);
        fileWriter.write(data2);
        fileWriter.close();
    }
 
    @After
    public void deleteFile() {
        String filePath = System.getProperty("user.dir") + File.separator + "testFile.txt";
        File file = new File(filePath);
        file.delete();
    }

    @Test
    public void TailOneFileNameArgumentTest() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Jsh.start("tail testFile.txt", out);
        Scanner scn = new Scanner(in);
        assertEquals(scn.nextLine(), "first line");
        assertEquals(scn.nextLine(), "second line");
        scn.close();
    }



    // tail with 3 arguments test something wrong also
    @Test
    public void TailThreeArgumentsTest() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Jsh.start("tail -n 1 testFile.txt", out);
        Scanner scn = new Scanner(in);
        assertEquals(scn.nextLine(), "second line");
        scn.close();
    }


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