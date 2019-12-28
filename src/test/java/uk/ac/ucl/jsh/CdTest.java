package uk.ac.ucl.jsh;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class CdTest {
<<<<<<< HEAD

    Jsh jsh = new Jsh();

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void buildTestFolder() throws Exception
    {
        jsh.setcurrentDirectory(folder.getRoot().getAbsolutePath());
        File virtualSrcFolder = folder.newFolder("src");
        File virtualMainFolder = new File(virtualSrcFolder.getAbsolutePath()+"/main");
        virtualMainFolder.mkdir();

        String absoluteFilePath = System.getProperty("user.dir") + File.separator + "cd_test.txt";
        File testFile = new File(absoluteFilePath);
        String testedStrings1 = "first line\n";

        FileOutputStream file_writer = new FileOutputStream(testFile);
        file_writer.write(testedStrings1.getBytes());
        file_writer.close();
    }
    
    @After
    public void resetUserDirectory()
    {
        File file = new File("cd_test.txt");
        file.delete();
        
        jsh.setcurrentDirectory(System.getProperty("user.dir"));
        folder.delete();
    }

    @Test
    public void cdCmdOneDirectory() throws Exception
    {
        String expected = jsh.getcurrentDirectory() + "/src";
        jsh.start("cd src", System.out);
        assertEquals(expected, jsh.getcurrentDirectory());
    }

    @Test
    public void cdCmdConsecutiveDirectories() throws Exception
    {
        String expected = jsh.getcurrentDirectory() + "/src/main";
        jsh.start("cd src/main", System.out);
        assertEquals(expected, jsh.getcurrentDirectory());
    }

    @Test
    public void cdCmdBacktracking() throws Exception
    {
        String expected = jsh.getcurrentDirectory();
        jsh.start("cd src ; cd ..", System.out);
        assertEquals(expected, jsh.getcurrentDirectory());
    }

=======
>>>>>>> f4b3b170a0085c31fe86f446a247b23def254c2b
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void cdMissingArgumentThrowsException() throws RuntimeException, IOException {
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("cd: missing argument"));
        jsh.start("cd", console);
    }

<<<<<<< HEAD
    // cd too many arguments test 
    @Test
    public void cdTooManyArgumentsThrowsException() throws RuntimeException, IOException {
        PrintStream console = null;
        console = System.out; 
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("cd: too many arguments"));
        jsh.start("cd arg1 arg2", console);
    }

    // cd cannot find directory test
    @Test
    public void cdNoExistingDirectoryThrowsException() throws RuntimeException, IOException {
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("cd: xxx is not an existing directory"));
        jsh.start("cd xxx", console);
    }

    // cd not a directory test
    @Test
    public void cdNoADirectoryThrowsException() throws RuntimeException, IOException {
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("cd: cd_test.txt is not an existing directory"));
        jsh.start("cd cd_test.txt", console);
    }
=======
    // // cd too many arguments test 
    // @Test
    // public void cdTooManyArgumentsThrowsException() throws RuntimeException, IOException {
    //     PrintStream console = null;
    //     Jsh jsh = new Jsh();

    //     console = System.out; 
    //     thrown.expect(RuntimeException.class);
    //     thrown.expectMessage(CoreMatchers.equalTo("cd: too many arguments"));
    //     jsh.start("cd arg1 arg2", console);
    // }

    // // cd cannot find directory
    // @Test
    // public void cdNoExistingDirectoryThrowsException() throws RuntimeException, IOException {
    //     Jsh jsh = new Jsh();
    //     PrintStream console = null;

    //     console = System.out;
    //     thrown.expect(RuntimeException.class);
    //     thrown.expectMessage(CoreMatchers.equalTo("cd: xxx is not an existing directory"));

    //     jsh.start("cd xxx", console);
    // }
>>>>>>> f4b3b170a0085c31fe86f446a247b23def254c2b
}