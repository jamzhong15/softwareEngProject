package uk.ac.ucl.jsh;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
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


        String absoluteFilePath = System.getProperty("user.dir") + File.separator + "cd_test.txt";
        File testFile = new File(absoluteFilePath);
        String testedStrings1 = "first line\n";

        FileOutputStream file_writer = new FileOutputStream(testFile);
        file_writer.write(testedStrings1.getBytes());
        file_writer.close();

        String absoluteFilePath1 = System.getProperty("user.dir") + File.separator + ".cd_test";
        File testFile1 = new File(absoluteFilePath1);

        FileOutputStream file_writer1 = new FileOutputStream(testFile1);
        file_writer1.write(testedStrings1.getBytes());
        file_writer1.close();
    }

    @After
    public void deleteTestFile()
    {
        jsh.setcurrentDirectory(System.getProperty("user.dir"));
        folder.delete();

        File file = new File("cd_test.txt");
        file.delete();

        File file1 = new File(".cd_test");
        file1.delete();
    }

    // @Test
    // public void hi() throws Exception {
    //     jsh.start("ls", System.out);
    //     jsh.start("cd src ; ls", System.out);
    // }
    
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

    // // too many arguments test
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void lsTooManyArgumentsThrowsException() throws RuntimeException, IOException {
        Jsh jsh = new Jsh();
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("ls: too many arguments"));
        jsh.start("ls arg1 arg2", console);
    }

    // no such directory test
    @Test
    public void lsNoSuchDirectoryThrowsException() throws RuntimeException, IOException {
        Jsh jsh = new Jsh();
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("ls: no such directory"));
        jsh.start("ls xxx", console);
    }
}