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
import java.io.IOException;
import java.io.PrintStream;

public class CdTest {


    @Rule
    public TemporaryFolder folder= new TemporaryFolder();

    @Before
    public void buildTestFolder() throws Exception
    {
        Jsh jsh = new Jsh();
        File testFolder= folder.newFolder("cdTestFolder");
        jsh.setcurrentDirectory(testFolder.getCanonicalPath());

    }
    
    @After
    public void resetUserDirectory()
    {
        // Jsh jsh = new Jsh();
        // jsh.setcurrentDirectory(System.getProperty("user.dir"));
    }

    @Test
    public void cdCmdOneDirectory() throws Exception
    {
        Jsh jsh = new Jsh();
        jsh.start("cd src", System.out);
        assertEquals("/workspaces/jsh-team-6/src", jsh.getcurrentDirectory());
    }

    @Test
    public void cdCmdConsecutiveDirectories() throws Exception
    {
        Jsh jsh = new Jsh();
        jsh.start("cd src/main", System.out);
        assertEquals("/workspaces/jsh-team-6/src/main", jsh.getcurrentDirectory());
    }

    @Test
    public void cdCmdBacktracking() throws Exception
    {
        Jsh jsh = new Jsh();
        jsh.start("cd src ; cd ..", System.out);
        assertEquals("/workspaces/jsh-team-6", jsh.getcurrentDirectory());
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void cdMissingArgumentThrowsException() throws RuntimeException, IOException {
        Jsh jsh = new Jsh();
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("cd: missing argument"));
        jsh.start("cd", console);
    }

    // cd too many arguments test 
    @Test
    public void cdTooManyArgumentsThrowsException() throws RuntimeException, IOException {
        PrintStream console = null;
        Jsh jsh = new Jsh();

        console = System.out; 
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("cd: too many arguments"));
        jsh.start("cd arg1 arg2", console);
    }

    // cd cannot find directory
    @Test
    public void cdNoExistingDirectoryThrowsException() throws RuntimeException, IOException {
        Jsh jsh = new Jsh();
        PrintStream console = null;

        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("cd: xxx is not an existing directory"));

        jsh.start("cd xxx", console);
    }
}