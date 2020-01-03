package uk.ac.ucl.jsh;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class LsTest {
    Jsh jsh = new Jsh();

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void buildTestFile() throws IOException
    {
        jsh.setcurrentDirectory(folder.getRoot().getAbsolutePath());
        File src_folder = folder.newFolder("src");
        File test_folder = folder.newFolder("src", "test");
        File main_folder = folder.newFolder("src", "main");
        File dot_folder = folder.newFolder("src", ".hi");
        File target_folder = folder.newFolder("target");
        File hello_folder = folder.newFolder("target", "hello");
        src_folder.mkdirs();
        test_folder.mkdirs();
        main_folder.mkdirs();
        dot_folder.mkdirs();
        target_folder.mkdirs();
        hello_folder.mkdirs();
    }

    @After
    public void deleteTestFile()
    {
        jsh.setcurrentDirectory(System.getProperty("user.dir"));
        folder.delete();
    }
    
    // ls no argument test
    @Test
    public void lsWithoutArgument() throws Exception {
        String currentDirectory = jsh.getcurrentDirectory();
        File currDir = new File(currentDirectory);
        ArrayList<String> listFiles = new ArrayList<>();
        File[] listOfFiles = currDir.listFiles();

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jsh.start("ls", out);
        Scanner scn = new Scanner(in);
        String contentString = scn.nextLine();
        String[] files = contentString.split("\t");
        
        // obtaining expected files names
        for (File file : listOfFiles) {
                listFiles.add(file.getName().toString());
        }
        for (String fileName : files) {
            assertTrue("wrong files displayed", listFiles.contains(fileName));
        }
        scn.close();
    }

    //ls one argument test
    @Test
    public void lsWithOneArgument() throws Exception {
        Jsh jsh = new Jsh();
        ArrayList<String> folders = new ArrayList<>();
        folders.add("test");
        folders.add("main");

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jsh.start("ls src", out);
        Scanner scn = new Scanner(in);
        String[] files = scn.nextLine().split("\t");
        for (String fileName : files) {
            assertTrue("wrong files displayed", folders.contains(fileName));
        }
        scn.close();
    }

    //multiple arguments test
    @Test
    public void lsMultipleArgumentTest() throws Exception {
        Jsh jsh = new Jsh();
        ArrayList<String> folders = new ArrayList<>();
        folders.add("main");
        folders.add("test");
        folders.add("hello");

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jsh.start("ls src target", out);
        Scanner scn = new Scanner(in);
        
        assertEquals("src:", scn.nextLine());
        String[] files = scn.nextLine().split("\t");
        for (String fileName : files)
        {
            assertTrue("wrong files displayed", folders.contains(fileName));
        }
        
        assertEquals("target:", scn.nextLine());
        files = scn.nextLine().split("\t");
        for (String fileName : files)
        {
            assertTrue("wrong files displayed", folders.contains(fileName));
        }

        scn.close();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // no such directory test

    
    @Test
    public void lsInvalidArgumentThrowsException() throws RuntimeException, IOException
    {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("ls: cannot access 'xxx': No such file or directory"));
        jsh.start("ls xxx", System.out);
    }

    @Test
    public void lsInvalidMultipleArgumentThrowsException() throws RuntimeException, IOException
    {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("ls: cannot access 'xxx': No such file or directory"));
        jsh.start("ls src xxx", System.out);
    }
}