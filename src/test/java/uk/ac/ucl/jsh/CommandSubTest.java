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

public class CommandSubTest {
    Jsh jsh = new Jsh();
    
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void buildTestFile() throws Exception {
        jsh.setcurrentDirectory(folder.getRoot().getAbsolutePath());
        File test_folder = folder.newFolder("testFolder");

        test_folder.mkdir();

        jsh.setcurrentDirectory(folder.getRoot().getAbsolutePath());
    
        File test1 = folder.newFile("test1.txt");
        String testedStrings1 = "abc\n";
        FileOutputStream file_writer1 = new FileOutputStream(test1);
        file_writer1.write(testedStrings1.getBytes());
        file_writer1.close();

        File test2 = folder.newFile("test2.txt");
        String testedStrings2 =  "def\n";
        FileOutputStream file_writer2 = new FileOutputStream(test2);
        file_writer2.write(testedStrings2.getBytes());
        file_writer2.close();
    }

    @After
    public void deleteTestFile() throws Exception {
        jsh.setcurrentDirectory(System.getProperty("user.dir"));
        folder.delete();

        File file = new File("test1.txt");
        file.delete();

        File file1 = new File("test2.txt");
        file1.delete();

    }

    @Test
    public void CommandSubSingleOutputTest() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);        
        jsh.start("echo `cat test1.txt`", out);
        Scanner scn = new Scanner(in);
        assertEquals("abc", scn.nextLine());
        scn.close();
    }

    @Test
    public void CommandSubMultipleOutTest() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);        
        jsh.start("echo `find -name *.txt`", out);
        Scanner scn = new Scanner(in);
        assertEquals("/test2.txt /test1.txt", scn.nextLine());
        scn.close();
    }

}