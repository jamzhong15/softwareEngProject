package uk.ac.ucl.jsh;

import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.PrintStream;

public class CdTest {
//     @Test (expected = RuntimeException.class)
//     public void cdEmptyInputThrowsException() throws IOException {
//         Jsh.eval("cd", System.out);
//         // throw new RuntimeException();
//     }

    // cd no argument test
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void cdMissingArgumentThrowsException() throws RuntimeException, IOException {
    PrintStream console = null;

    console = System.out;
    thrown.expect(RuntimeException.class);
    thrown.expectMessage("cd: missing argument");
    Jsh.eval("cd", console);
    // throw new RuntimeException("cd: missing argument");
    }

    // cd too many arguments test 
    @Test
    public void cdTooManyArgumentsThrowsException() throws RuntimeException, IOException {
    PrintStream console = null;

    console = System.out; 
    thrown.expect(RuntimeException.class);
    thrown.expectMessage("cd: too many arguments");
    Jsh.eval("cd arg1 arg2", console);
    }

    // cd cannot find directory
    @Test
    public void cdNoExistingDirectoryThrowsException() throws RuntimeException, IOException {
    PrintStream console = null;

    console = System.out;
    thrown.expect(RuntimeException.class);
    thrown.expectMessage("cd: xxx is not an existing directory");

    Jsh.eval("cd xxx", console);
    }
}