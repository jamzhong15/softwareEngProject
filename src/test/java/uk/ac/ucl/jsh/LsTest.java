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
import java.io.PrintStream;
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
        src_folder.mkdirs();
        test_folder.mkdirs();
        main_folder.mkdirs();

        File file1 = new File(main_folder.getAbsolutePath()+"/file1.txt");

        File file2 = new File(test_folder.getAbsolutePath()+"/file2.txt");
        file1.mkdir();
        file2.mkdir();

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

    // ls multiple arguments test
    @Test
    public void lsWithMultipleArguments() throws Exception
    {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jsh.start("cd src ; ls main test", out);
        Scanner scn = new Scanner(in);
        assertEquals("main:", scn.nextLine());
        assertEquals("file1.txt\t", scn.nextLine());
        assertEquals("test:", scn.nextLine());
        assertEquals("file2.txt\t", scn.nextLine());
        scn.close();

    }

    // too many arguments test
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // no such directory test
    @Test
    public void lsNoSuchDirectoryThrowsException() throws RuntimeException, IOException {
        Jsh jsh = new Jsh();
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("ls: cannot access 'xxx': No such file or directory"));
        jsh.start("ls xxx", console);
    }

    @Test
    public void lsInvalidArgumentMultipleThrowsException() throws RuntimeException, IOException
    {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("ls: cannot access 'imaginary': No such file or directory"));
        jsh.start("ls src imaginary", System.out);
    }
}