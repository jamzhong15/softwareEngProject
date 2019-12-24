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


public class TailTest {

    // @Before
    // public void buildTestFile() throws IOException
    // {
    //     String absoluteFilePath = System.getProperty("user.dir") + File.separator + "tail_test.txt";
    //     File testFile = new File(absoluteFilePath);
    //     ArrayList<String> testedStrings = new ArrayList<>();
    //     testedStrings.add("first line\n");
    //     testedStrings.add("second line");
    //     FileOutputStream file_writer = new FileOutputStream(testFile);
    //     for (String string : testedStrings)
    //     {
    //         file_writer.write(string.getBytes());
    //     }
    //     file_writer.close();
    // }

    // @After
    // public void deleteTestFile()
    // {
    //     File file = new File("tail_test.txt");
    //     file.delete();
    // }

    // // tail 1 filename argument test something wrong here
    // @Before
    // public void createNewFile() throws IOException {
    //     String filePath = System.getProperty("user.dir") + File.separator + "testFile.txt";
    //     File file = new File(filePath);
    //     file.createNewFile();
    //     FileWriter fileWriter = new FileWriter(file);
    //     String data1 = "first line\n";
    //     String data2 = "second line";
    //     fileWriter.write(data1);
    //     fileWriter.write(data2);
    //     fileWriter.close();
    // }
 
    // @After
    // public void deleteFile() {
    //     String filePath = System.getProperty("user.dir") + File.separator + "testFile.txt";
    //     File file = new File(filePath);
    //     file.delete();
    // }

    // @Test
    // public void TailOneFileNameArgumentTest() throws Exception {
    //     Jsh jsh = new Jsh();
        
    //     PipedInputStream in = new PipedInputStream();
    //     PipedOutputStream out;
    //     out = new PipedOutputStream(in);
    //     jsh.start("tail tail_test.txt", out);
    //     Scanner scn = new Scanner(in);
    //     assertEquals("first line", scn.nextLine());
    //     scn.close();
    // }



    // // tail with 3 arguments test something wrong also
    // @Test
    // public void TailThreeArgumentsTest() throws Exception {
    //     Jsh jsh = new Jsh();

    //     PipedInputStream in = new PipedInputStream();
    //     PipedOutputStream out;
    //     out = new PipedOutputStream(in);
    //     jsh.start("tail -n 1 tail_test.txt", out);
    //     Scanner scn = new Scanner(in);
    //     assertEquals("second line", scn.nextLine());
    //     scn.close();
    // }


    // @Rule
    // public ExpectedException thrown = ExpectedException.none();

    // // tail no argument test
    // @Test
    // public void TailMissingArgumentThrowsException() throws RuntimeException, IOException {
    //     Jsh jsh = new Jsh();
    //     PrintStream console = null;
    //     console = System.out;
    //     thrown.expect(RuntimeException.class);
    //     thrown.expectMessage(CoreMatchers.equalTo("tail: missing arguments"));
    //     jsh.start("tail", console);
    // }

    // // tail wrong no. of arguments
    // @Test
    // public void TailWrongArgumentNumberThrowsException() throws RuntimeException, IOException {
    //     Jsh jsh = new Jsh();
    //     PrintStream console = null;
    //     console = System.out;
    //     thrown.expect(RuntimeException.class);
    //     thrown.expectMessage(CoreMatchers.equalTo("tail: wrong arguments"));
    //     jsh.start("tail -n Dockerfile", console);
    // }
    
    // // tail 3 argument but first one is not -n
    // @Test
    // public void TailThreeArgumentsWithWrongFirstArgumentThrowsException() throws RuntimeException, IOException {
    //     Jsh jsh = new Jsh();
    //     PrintStream console = null;
    //     console = System.out;
    //     thrown.expect(RuntimeException.class);
    //     thrown.expectMessage(CoreMatchers.equalTo("tail: wrong argument -s"));
    //     jsh.start("tail -s 3 Dockerfile", console);
    // }

    // // tail 3 argument but second argument is not number
    // @Test
    // public void TailThreeArgumentsWithWrongSecondArgumentThrowsException() throws RuntimeException, IOException {
    //     Jsh jsh = new Jsh();
    //     PrintStream console = null;
    //     console = System.out;
    //     thrown.expect(RuntimeException.class);
    //     thrown.expectMessage(CoreMatchers.equalTo("tail: wrong argument s"));
    //     jsh.start("tail -n s Dockerfile", console);
    // }

    // // tail file does not exist
    // @Test
    // public void TailFileDoesNotExistThrowsException() throws RuntimeException, IOException {
    //     Jsh jsh = new Jsh();
    //     PrintStream console = null;
    //     console = System.out;
    //     thrown.expect(RuntimeException.class);
    //     thrown.expectMessage(CoreMatchers.equalTo("tail: xxx does not exist"));
    //     jsh.start("tail -n 3 xxx", console);
    // }
    
    // // tail cannot open file
    // @Test
    // public void TailCannotOpenFileThrowsException() throws RuntimeException, IOException {
    //     Jsh jsh = new Jsh();
    //     PrintStream console = null;
    //     console = System.out;
    //     thrown.expect(RuntimeException.class);
    //     thrown.expectMessage(CoreMatchers.equalTo("tail: cannot open target"));
    //     jsh.start("tail target", console);
    // }
}