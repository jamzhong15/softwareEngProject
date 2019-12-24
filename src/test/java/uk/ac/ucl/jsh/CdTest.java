package uk.ac.ucl.jsh;

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


import java.io.IOException;
import java.io.PrintStream;

public class CdTest {
    // @Rule
    // public ExpectedException thrown = ExpectedException.none();

    // @Test
    // public void cdMissingArgumentThrowsException() throws RuntimeException, IOException {
    //     Jsh jsh = new Jsh();
    //     PrintStream console = null;
    //     console = System.out;
    //     thrown.expect(RuntimeException.class);
    //     thrown.expectMessage(CoreMatchers.equalTo("cd: missing argument"));
    //     jsh.start("cd", console);
    // }

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
}