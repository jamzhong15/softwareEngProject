package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
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

public class FindTest {

    Jsh jsh = new Jsh();

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void buildTestFolder() throws Exception
    {
        jsh.setcurrentDirectory(folder.getRoot().getAbsolutePath());
        File dockerfile_file = folder.newFile("Dockerfile");
        dockerfile_file.createNewFile();

        File src_folder = folder.newFolder("main");
        File CmdGrammar_file = new File(src_folder.getAbsolutePath()+"/Hi.g4");
        CmdGrammar_file.createNewFile();

        File dot_devcontainer_folder = folder.newFolder("devcontainer");
        File sub_dockerfile_file = new File(dot_devcontainer_folder.getAbsolutePath()+"/Dockerfile");
        sub_dockerfile_file.createNewFile();
    }
    
    @After
    public void resetUserDirectory()
    {
        jsh.setcurrentDirectory(System.getProperty("user.dir"));
        folder.delete();
    }
    
    //find 2 args test
    
    @Test
    public void findTwoArgsTest() throws Exception {
        ArrayList<String> paths = new ArrayList<>();
        paths.add("/Dockerfile");
        paths.add("/devcontainer/Dockerfile");

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jsh.start("find -name Dockerfile", out);
        out.close();
        Scanner scn = new Scanner(in);
        String[] fileNames = scn.nextLine().split(System.getProperty("line.separator"));
        for (String file : fileNames)
        {
            assertTrue("wrong file path displayed", paths.contains(file));
        }
        scn.close();
    }

    //find 3 args test
    @Test
    public void findThreeArgsTest() throws Exception {
        
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        jsh.start("find main -name *.g4", out);
        out.close();
        Scanner scn = new Scanner(in);
        assertEquals("/main/Hi.g4", scn.nextLine());
        scn.close();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // find without argument (or 1 argument)
    @Test
    public void findAppArgsLessThanTwo() throws RuntimeException, IOException {
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("find: missing arguments"));
        jsh.start("find", console);
    }
    
    // find more than 3 arguments
    @Test
    public void findAppArgsMoreThanThree() throws RuntimeException, IOException {
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("find: too many arguments"));
        jsh.start("find x x x x", console);
    }

    // find 2 arguments but first arg is not -name
    @Test
    public void findTwoAppArgsButFirstArgsNot_nameThrowsException() throws RuntimeException, IOException {
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("find: invalid arguments"));
        jsh.start("find -namee x", console);
    }

    // find 3 arguments but second arg is not -name
    @Test
    public void findThreeAppArgsButSecondArgsNot_nameThrowsException() throws RuntimeException, IOException {
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("find: invalid arguments"));
        jsh.start("find x -namee x", console);
    }
}