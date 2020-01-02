package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

public class HeadTest {

    Jsh jsh = new Jsh();

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void buildTestFile() throws IOException
    {
        jsh.setcurrentDirectory(folder.getRoot().getAbsolutePath());
        File target_folder = folder.newFolder("targetFolder");
        target_folder.mkdir();
        File head_test_File = folder.newFile("head_test.txt");
        String testedStrings1 = "first line\n";
        String testedStrings2 = "second line\n";
        String testedStrings3 = "third line\n";

        FileOutputStream file_writer = new FileOutputStream(head_test_File);
        file_writer.write(testedStrings1.getBytes());
        file_writer.write(testedStrings2.getBytes());
        file_writer.write(testedStrings3.getBytes());

        file_writer.close();

        File head_test_File1 = folder.newFile("head_test1.txt");
        String testedStrings4 = "1 line\n";
        String testedStrings5 = "2 line\n";
        String testedStrings6 = "3 line\n";

        FileOutputStream file_writer1 = new FileOutputStream(head_test_File1);
        file_writer1.write(testedStrings4.getBytes());
        file_writer1.write(testedStrings5.getBytes());
        file_writer1.write(testedStrings6.getBytes());
        
        file_writer1.close();
    }

    @After
    public void deleteTestFile()
    {
        jsh.setcurrentDirectory(System.getProperty("user.dir"));
        folder.delete();
    }

    // head 1 filename argument test
    @Test
    public void HeadOneFileNameArgumentTest() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        jsh.start("head head_test.txt", out);
        Scanner scn = new Scanner(in);
        assertEquals("first line", scn.nextLine());
        assertEquals("second line", scn.nextLine());
        assertEquals("third line", scn.nextLine());
        scn.close();
    }

     // head 3 arguments test
    @Test
    public void HeadThreeArgumentsTest() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        jsh.start("head -n 2 head_test.txt", out);
        Scanner scn = new Scanner(in);
        assertEquals("first line", scn.nextLine());
        assertEquals("second line", scn.nextLine());
        scn.close();
    } 

     // head multiple files with number test
     @Test
     public void HeadMultipleFilesTest() throws Exception {
         PipedInputStream in = new PipedInputStream();
         PipedOutputStream out;
         out = new PipedOutputStream(in);
         jsh.start("head -n 2 head_test.txt head_test1.txt", out);
         Scanner scn = new Scanner(in);
         assertEquals("first line", scn.nextLine());
         assertEquals("second line", scn.nextLine());
         assertEquals("1 line", scn.nextLine());
         assertEquals("2 line", scn.nextLine());
         scn.close();
     } 

     // head multiple files without number test
     @Test
     public void HeadMultipleFilesWithoutNumberTest() throws Exception {
         PipedInputStream in = new PipedInputStream();
         PipedOutputStream out;
         out = new PipedOutputStream(in);
         jsh.start("head head_test.txt head_test1.txt", out);
         Scanner scn = new Scanner(in);
         assertEquals("first line", scn.nextLine());
         assertEquals("second line", scn.nextLine());
         assertEquals("third line", scn.nextLine());
         assertEquals("1 line", scn.nextLine());
         assertEquals("2 line", scn.nextLine());
         assertEquals("3 line", scn.nextLine());
         scn.close();
     } 

    // head stdin no argument test
    @Test
    public void HeadStdinVersionNoArgumentsTest() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        jsh.start("cat head_test.txt | head", out);
        Scanner scn = new Scanner(in);
        assertEquals("first line", scn.nextLine());
        assertEquals("second line", scn.nextLine());
        assertEquals("third line", scn.nextLine());
        scn.close();
    }

    // head stdin 2 args test
    @Test
    public void HeadStdinVersionTwoArgumentsTest() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        jsh.start("cat head_test.txt | head -n 4", out);
        Scanner scn = new Scanner(in);
        assertEquals("first line", scn.nextLine());
        assertEquals("second line", scn.nextLine());
        assertEquals("third line", scn.nextLine());
        scn.close();
    }

    
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // head no argument test
    @Test
    public void HeadMissingArgumentThrowsException() throws RuntimeException, IOException {
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("head: missing arguments"));
        jsh.start("head", console);
    }

    // Head wrong no. of arguments
    @Test
    public void HeadWrongArgumentNumberThrowsException() throws RuntimeException, IOException {
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("head: wrong arguments"));
        jsh.start("head -n head_test.txt", console);
    }

    // head 3 argument but second arg is not number
    @Test
    public void HeadStdinVersionWithWrongFirstArgumentThrowsException() throws RuntimeException, IOException {
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("head: wrong argument s"));
        jsh.start("head -n s head_test.txt", console);
    }

    // head obtain from stdin and second arg is not number
    @Test
    public void HeadStdinVersionWithWrongSecondArgumentThrowsException() throws RuntimeException, IOException {
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("head: wrong argument s"));
        jsh.start("cat head_test.txt | head -n s", console);
    }

    // head file does not exist
    @Test
    public void HeadFileDoesNotExistThrowsException() throws RuntimeException, IOException {
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("head: xxx does not exist"));
        jsh.start("head -n 3 xxx", console);
    }
    
    // head cannot open file
    @Test
    public void HeadCannotOpenFileThrowsException() throws RuntimeException, IOException {
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("head: cannot open targetFolder"));
        jsh.start("head targetFolder", console);
    }
    
    // head unsafe command error test
    @Test
    public void HeadUnsafeCommandErrorTest() throws Exception {
        Jsh jsh = new Jsh();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        jsh.start("_head", System.out);
        assertEquals("head: missing arguments\n", outContent.toString());
    }

    // head unsafe command successful test
    @Test
    public void HeadUnsafeCommandTest() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        jsh.start("_head -n 2 head_test.txt", out);
        Scanner scn = new Scanner(in);
        assertEquals("first line", scn.nextLine());
        assertEquals("second line", scn.nextLine());
        scn.close();
    }
}