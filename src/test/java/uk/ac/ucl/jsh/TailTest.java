package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;


public class TailTest {

    // @Rule
    // public TemporaryFolder folder= new TemporaryFolder();


    // tail 1 filename argument test something wrong here
    @Before
    public void createNewFile() throws IOException {
        // File file= folder.newFile("tail_test.txt");


        String filePath = System.getProperty("user.dir") + File.separator + "tail_test.txt";
        File file = new File(filePath);
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write("first line\n");
        fileWriter.write("second line\n");
        fileWriter.write("third line");
        fileWriter.close();
    }
 
    @After
    public void deleteFile() {
        String filePath = System.getProperty("user.dir") + File.separator + "tail_test.txt";
        File file = new File(filePath);
        file.delete();
    }

    // tail 1 argument test 
    @Test
    public void TailOneFileNameArgumentTest() throws Exception {
        Jsh jsh = new Jsh();
        
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        jsh.start("tail tail_test.txt", out);
        Scanner scn = new Scanner(in);
        assertEquals("first line", scn.nextLine());
        assertEquals("second line", scn.nextLine());
        assertEquals("third line", scn.nextLine());
        scn.close();
    }

    // tail with 3 arguments test
    @Test
    public void HeadStdinVersionNoArgumentsTest() throws Exception {
        Jsh jsh = new Jsh();

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        jsh.start("cat tail_test.txt | tail", out);
        Scanner scn = new Scanner(in);
        assertEquals("first line", scn.nextLine());
        assertEquals("second line", scn.nextLine());
        assertEquals("third line", scn.nextLine());
        scn.close();
    }

    // tail stdin 2 arguments test
    @Test
    public void HeadStdinVersionTwoArgumentsTest() throws Exception {
        Jsh jsh = new Jsh();

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        jsh.start("tail -n 2 tail_test.txt", out);
        Scanner scn = new Scanner(in);
        assertEquals("second line", scn.nextLine());
        assertEquals("third line", scn.nextLine());
        scn.close();
    }

    // tail stdin no argument test
    @Test
    public void TailStdinVersionNoArgumentsTest() throws Exception {
        Jsh jsh = new Jsh();

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        jsh.start("cat tail_test.txt | tail", out);
        Scanner scn = new Scanner(in);
        assertEquals("first line", scn.nextLine());
        assertEquals("second line", scn.nextLine());
        assertEquals("third line", scn.nextLine());
        scn.close();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // tail no argument test
    @Test
    public void TailMissingArgumentThrowsException() throws RuntimeException, IOException {
        Jsh jsh = new Jsh();
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("tail: missing arguments"));
        jsh.start("tail", console);
    }

    // tail wrong no. of arguments
    @Test
    public void TailWrongArgumentNumberThrowsException() throws RuntimeException, IOException {
        Jsh jsh = new Jsh();
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("tail: wrong arguments"));
        jsh.start("tail -n tail_test.txt", console);
    }
    
    // tail 3 argument but first one is not -n
    @Test
    public void TailThreeArgumentsWithWrongFirstArgumentThrowsException() throws RuntimeException, IOException {
        Jsh jsh = new Jsh();
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("tail: wrong argument -s"));
        jsh.start("tail -s 3 tail_test.txt", console);
    }

    // head obtain from stdin and first arg is not -n
    @Test
    public void TailStdinVersionWithWrongFIrstArgumentThrowsException() throws RuntimeException, IOException {
        Jsh jsh = new Jsh();
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("tail: wrong argument -s"));
        jsh.start("cat tail_test.txt |tail -s 3 ", console);
    }

    // tail 3 argument but second argument is not number
    @Test
    public void TailThreeArgumentsWithWrongSecondArgumentThrowsException() throws RuntimeException, IOException {
        Jsh jsh = new Jsh();
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("tail: wrong argument s"));
        jsh.start("tail -n s tail_test.txt", console);
    }

    // tail obtain from stdin and second arg is not number
    @Test
    public void TailStdinVersionWithWrongSecondArgumentThrowsException() throws RuntimeException, IOException {
        Jsh jsh = new Jsh();
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("tail: wrong argument s"));
        jsh.start("cat tail_test.txt | tail -n s", console);
    }

    // tail file does not exist
    @Test
    public void TailFileDoesNotExistThrowsException() throws RuntimeException, IOException {
        Jsh jsh = new Jsh();
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("tail: xxx does not exist"));
        jsh.start("tail -n 3 xxx", console);
    }
    
    // tail cannot open file
    @Test
    public void TailCannotOpenFileThrowsException() throws RuntimeException, IOException {
        Jsh jsh = new Jsh();
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("tail: cannot open target"));
        jsh.start("tail target", console);
    }
}