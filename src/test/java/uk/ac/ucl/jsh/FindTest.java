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

public class FindTest {

    @Before
    public void buildTestFile() throws IOException {
        File dir = new File(System.getProperty("user.dir") + File.separator + "testFolder");
        dir.mkdir();

        String absoluteFilePath = System.getProperty("user.dir") + File.separator + "find_test.txt";
        File testFile = new File(absoluteFilePath);
        String testedStrings1 = "first line\n";
        String testedStrings2 = "second line\n";
        String testedStrings3 = "third line\n";

        FileOutputStream file_writer = new FileOutputStream(testFile);
        file_writer.write(testedStrings1.getBytes());
        file_writer.write(testedStrings2.getBytes());
        file_writer.write(testedStrings3.getBytes());

        file_writer.close();

        String absoluteFilePath1 = System.getProperty("user.dir") + File.separator + "testFolder" + File.separator + "find_test1.txt";
        File testFile1 = new File(absoluteFilePath1);
        String testedStrings4 = "first line\n";

        FileOutputStream file_writer1 = new FileOutputStream(testFile1);
        file_writer1.write(testedStrings4.getBytes());
        file_writer1.close();
    }

    @After
    public void deleteTestFile()
    {
        File file = new File("find_test.txt");
        file.delete();

        File file1 = new File("testFolder/find_test1.txt");
        file1.delete();

        File dir = new File("testFolder");
        dir.delete();
    }

    // find 2 args test
    // @Test
    // public void findTwoArgsTest() throws Exception {
    //     Jsh jsh = new Jsh();
    //     PipedInputStream in = new PipedInputStream();
    //     PipedOutputStream out = new PipedOutputStream(in);
    //     jsh.start("find -name find_test.txt", out);
    //     Scanner scn = new Scanner(in);
    //     assertEquals("/find_test.txt", scn.nextLine());
    //     scn.close();
    // }

    // find 3 args test
    @Test
    public void findThreeArgsTest() throws Exception {
        Jsh jsh = new Jsh();
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        jsh.start("find src -name *.g4", out);
        Scanner scn = new Scanner(in);
        assertEquals("/src/main/antlr4/uk/ac/ucl/jsh/CmdGrammar.g4", scn.nextLine());
        scn.close();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // find without argument (or 1 argument)
    @Test
    public void findAppArgsLessThanTwo() throws RuntimeException, IOException {
        Jsh jsh = new Jsh();
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("find: missing arguments"));
        jsh.start("find", console);
    }
    
    // find more than 3 arguments
    @Test
    public void findAppArgsMoreThanThree() throws RuntimeException, IOException {
        Jsh jsh = new Jsh();
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("find: too many arguments"));
        jsh.start("find x x x x", console);
    }

    // find 2 arguments but first arg is not -name
    @Test
    public void findTwoAppArgsButFirstArgsNot_nameThrowsException() throws RuntimeException, IOException {
        Jsh jsh = new Jsh();
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("find: invalid arguments"));
        jsh.start("find -namee x", console);
    }

    // find 3 arguments but second arg is not -name
    @Test
    public void findThreeAppArgsButSecondArgsNot_nameThrowsException() throws RuntimeException, IOException {
        Jsh jsh = new Jsh();
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("find: invalid arguments"));
        jsh.start("find x -namee x", console);
    }
}