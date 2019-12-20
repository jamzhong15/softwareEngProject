package uk.ac.ucl.jsh;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


import java.io.IOException;
import java.io.PrintStream;

public class CdTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void cdMissingArgumentThrowsException() throws RuntimeException, IOException {
    PrintStream console = null;

    console = System.out;
    thrown.expect(RuntimeException.class);
    thrown.expectMessage("cd: missing argument");
    Jsh.start("cd", console);
    }

    // cd too many arguments test 
    @Test
    public void cdTooManyArgumentsThrowsException() throws RuntimeException, IOException {
    PrintStream console = null;

    console = System.out; 
    thrown.expect(RuntimeException.class);
    thrown.expectMessage("cd: too many arguments");
    Jsh.start("cd arg1 arg2", console);
    }

    // cd cannot find directory
    @Test
    public void cdNoExistingDirectoryThrowsException() throws RuntimeException, IOException {
    PrintStream console = null;

    console = System.out;
    thrown.expect(RuntimeException.class);
    thrown.expectMessage("cd: xxx is not an existing directory");

    Jsh.start("cd xxx", console);
    }
}