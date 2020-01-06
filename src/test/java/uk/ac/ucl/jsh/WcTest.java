package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;

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

public class WcTest
{
    Jsh jsh = new Jsh();

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void buildTestFolder() throws Exception
    {
        jsh.setcurrentDirectory(folder.getRoot().getAbsolutePath());
        File test_folder = folder.newFolder("testFolder");
        test_folder.mkdir();
        File wc_test_file = folder.newFile("wcTest.txt");
        String testedStrings1 = "first line\n";
        String testedStrings2 = "second line\n";
        String testedStrings3 = "\n";
        String testedStrings4 = "third line\n";
        


        FileOutputStream file_writer = new FileOutputStream(wc_test_file);
        file_writer.write(testedStrings1.getBytes());
        file_writer.write(testedStrings2.getBytes());
        file_writer.write(testedStrings3.getBytes());
        file_writer.write(testedStrings4.getBytes());
        file_writer.close();
        
    }
    
    @After
    public void resetUserDirectory()
    {
        jsh.setcurrentDirectory(System.getProperty("user.dir"));
        folder.delete();
    }    

    @Test
    public void wcCharCountTwoArguments() throws RuntimeException, IOException
    {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jsh.start("wc -m wcTest.txt", out);
        Scanner scn = new Scanner(in);
        assertEquals("35", scn.nextLine());
        scn.close();
    }

    @Test
    public void wcWordCountTwoArguments() throws RuntimeException, IOException
    {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jsh.start("wc -w wcTest.txt", out);
        Scanner scn = new Scanner(in);
        assertEquals("6", scn.nextLine());
        scn.close();
    }

    @Test
    public void wcLineCountTwoArguments() throws RuntimeException, IOException
    {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jsh.start("wc -l wcTest.txt", out);
        Scanner scn = new Scanner(in);
        assertEquals("4", scn.nextLine());
        scn.close();
    }

    @Test
    public void wcOneArgument() throws RuntimeException, IOException
    {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jsh.start("wc wcTest.txt", out);
        Scanner scn = new Scanner(in);
        assertEquals("4\t6\t35", scn.nextLine());
        scn.close();
    }

    @Test
    public void wcStdinCharCount() throws RuntimeException, IOException
    {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jsh.start("cat wcTest.txt | wc -m", out);
        Scanner scn = new Scanner(in);
        assertEquals("35", scn.nextLine());
        scn.close();
    }

    @Test
    public void wcStdinWordCount() throws RuntimeException, IOException
    {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jsh.start("cat wcTest.txt | wc -w", out);
        Scanner scn = new Scanner(in);
        assertEquals("6", scn.nextLine());
        scn.close();
    }

    @Test
    public void wcStdinLineCount() throws RuntimeException, IOException
    {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jsh.start("cat wcTest.txt | wc -l", out);
        Scanner scn = new Scanner(in);
        assertEquals("4", scn.nextLine());
        scn.close();
    }

    @Test
    public void wcStdinNoArguments() throws RuntimeException, IOException
    {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jsh.start("cat wcTest.txt | wc", out);
        Scanner scn = new Scanner(in);
        assertEquals("4\t6\t35", scn.nextLine());
        scn.close();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void wcNoArgumentThrowsException() throws RuntimeException, IOException
    {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("wc: missing arguments"));
        jsh.start("wc", System.out);
    }

    @Test
    public void wcFileExistButCannotOpenThrowsException() throws RuntimeException, IOException
    {
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("wc: cannot open testFolder"));
        jsh.start("wc -m testFolder", console);
    }

    @Test
    public void wcFileNotExitstThrowsException() throws RuntimeException, IOException
    {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("wc: xxx does not exist"));
        jsh.start("wc -m xxx", System.out);
    }
}