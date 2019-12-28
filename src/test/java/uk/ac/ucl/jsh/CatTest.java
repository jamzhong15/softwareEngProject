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

public class CatTest {
    
<<<<<<< HEAD
    Jsh jsh = new Jsh();
    
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void buildTestFile() throws IOException
    {
        jsh.setcurrentDirectory(folder.getRoot().getAbsolutePath());
        File src_folder = folder.newFolder("src");
        src_folder.mkdir();
        File cat_test_file = folder.newFile("cat_test.txt");
        String testedStrings1 = "first line\n";
        String testedStrings2 = "second line\n";
        FileOutputStream file_writer = new FileOutputStream(cat_test_file);
        file_writer.write(testedStrings1.getBytes());
        file_writer.write(testedStrings2.getBytes());

        file_writer.close();
    }

    @After
    public void deleteTestFile()
    {
        jsh.setcurrentDirectory(System.getProperty("user.dir"));
        folder.delete();
    }
    
    
    // cat test with pipe (obtain args from stdin)
    @Test
    public void catStdinTest() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);        
        jsh.start("echo hi |cat", out);
        Scanner scn = new Scanner(in);
        assertEquals("hi", scn.nextLine());
        scn.close();
    }

    // cat with directory test (with bugs i guess)
    @Test
    public void catDirectoryExistAndCanOpenTest() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);        
        jsh.start("cat cat_test.txt", out);
        Scanner scn = new Scanner(in);
        assertEquals("first line", scn.nextLine());
        assertEquals("second line", scn.nextLine());
        scn.close();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // cat with directory but cannot open bc not a file
    @Test
    public void catDirectoryExistButCannotOpenTest() throws Exception {
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("cat: cannot open src"));
        jsh.start("cat src", console);
    }

    // cat no argument test (no args from stdin)
    @Test
    public void CatNoArgumentThrowsException() throws RuntimeException, IOException {
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("cat: missing arguments"));
        jsh.start("cat", console);
    }

    // cat file not exist test
    @Test
    public void CatFileNotExistThrowsException() throws RuntimeException, IOException {
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("cat: file does not exist"));
        jsh.start("cat xxx", console);
    }
=======
    // @Before
    // public void buildTestFile() throws IOException
    // {
    //     String absoluteFilePath = System.getProperty("user.dir") + File.separator + "cat_test.txt";
    //     File testFile = new File(absoluteFilePath);
    //     String testedStrings1 = "first line\n";
    //     String testedStrings2 = "second line\n";

    //     FileOutputStream file_writer = new FileOutputStream(testFile);
    //     file_writer.write(testedStrings1.getBytes());
    //     file_writer.write(testedStrings2.getBytes());
    //     file_writer.close();
    // }

    // @After
    // public void deleteTestFile()
    // {
    //     File file = new File("cat_test.txt");
    //     file.delete();
    // }
    // // cat test with pipe (obtain args from stdin)
    // @Test
    // public void catStdinTest() throws Exception {
    //     Jsh jsh = new Jsh();
    //     PipedInputStream in = new PipedInputStream();
    //     PipedOutputStream out;
    //     out = new PipedOutputStream(in);        
    //     jsh.start("echo hi |cat", out);
    //     Scanner scn = new Scanner(in);
    //     assertEquals("hi", scn.nextLine());
    //     scn.close();
    // }

    // // cat with directory test (with bugs i guess)
    // @Test
    // public void catDirectoryExistAndCanOpenTest() throws Exception {
    //     Jsh jsh = new Jsh();
    //     PipedInputStream in = new PipedInputStream();
    //     PipedOutputStream out;
    //     out = new PipedOutputStream(in);        
    //     jsh.start("cat cat_test.txt", out);
    //     Scanner scn = new Scanner(in);
    //     assertEquals("first line", scn.nextLine());
    //     assertEquals("second line", scn.nextLine());
    //     scn.close();
    // }

    // @Rule
    // public ExpectedException thrown = ExpectedException.none();

    // // cat with directory but cannot open bc not a file
    // @Test
    // public void catDirectoryExistButCannotOpenTest() throws Exception {
    //     Jsh jsh = new Jsh();
    //     PrintStream console = null;
    //     console = System.out;
    //     thrown.expect(RuntimeException.class);
    //     thrown.expectMessage(CoreMatchers.equalTo("cat: cannot open src"));
    //     jsh.start("cat src", console);
    // }

    // // cat no argument test (no args from stdin)
    // @Test
    // public void CatNoArgumentThrowsException() throws RuntimeException, IOException {
    //     Jsh jsh = new Jsh();
    //     PrintStream console = null;
    //     console = System.out;
    //     thrown.expect(RuntimeException.class);
    //     thrown.expectMessage(CoreMatchers.equalTo("cat: missing arguments"));
    //     jsh.start("cat", console);
    // }

    // // cat file not exist test
    // @Test
    // public void CatFileNotExistThrowsException() throws RuntimeException, IOException {
    //     Jsh jsh = new Jsh();
    //     PrintStream console = null;
    //     console = System.out;
    //     thrown.expect(RuntimeException.class);
    //     thrown.expectMessage(CoreMatchers.equalTo("cat: file does not exist"));
    //     jsh.start("cat xxx", console);
    // }
>>>>>>> f4b3b170a0085c31fe86f446a247b23def254c2b

}