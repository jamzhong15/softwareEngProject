package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
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

public class GlobbingTest {
    Jsh jsh = new Jsh();
    
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void buildTestFile() throws Exception {
        jsh.setcurrentDirectory(folder.getRoot().getAbsolutePath());
        File test_folder = folder.newFolder("testFolder");
 
        test_folder.mkdir();

        jsh.setcurrentDirectory(folder.getRoot().getAbsolutePath());

        File globbing1 = folder.newFile("globbing1.txt");
        String testedStrings1 = "globbing1\n";
        FileOutputStream file_writer1 = new FileOutputStream(globbing1);
        file_writer1.write(testedStrings1.getBytes());
        file_writer1.close();

        File globbing2 = folder.newFile("globbing2.txt");
        String testedStrings2 =  "globbing2\n";
        FileOutputStream file_writer2 = new FileOutputStream(globbing2);
        file_writer2.write(testedStrings2.getBytes());
        file_writer2.close();

    }

    @After
    public void deleteTestFile() throws Exception {
        jsh.setcurrentDirectory(System.getProperty("user.dir"));
        folder.delete();

        File file = new File("globbing1.txt");
        file.delete();

        File file1 = new File("globbing2.txt");
        file1.delete();

    }

    @Test

    public void globbingCatTest() throws Exception 
    {
        ArrayList<String> expected_contents = new ArrayList<>();
        expected_contents.add("globbing1");
        expected_contents.add("globbing2");

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);        
        jsh.start("cat *.txt", out);
        Scanner scn = new Scanner(in);
        
        ArrayList<String> actual_contents = new ArrayList<>();
        actual_contents.add(scn.nextLine());
        actual_contents.add(scn.nextLine());
        scn.close();
        for (String actual : actual_contents)
        {
            assertTrue("wrong file contents", expected_contents.contains(actual));
        }
    }

    @Test
    public void globbingCatTestWithQuote() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);        
        jsh.start("cat 'globbing2.txt'", out);
        Scanner scn = new Scanner(in);
        assertEquals("globbing2", scn.nextLine());
        scn.close();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void cannotMatch() throws RuntimeException, IOException {
        Jsh jsh = new Jsh();
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("cat: file does not exist"));
        jsh.start("cat *.abc", console);
    }

}