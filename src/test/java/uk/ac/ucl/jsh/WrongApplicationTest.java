package uk.ac.ucl.jsh;

import java.io.IOError;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WrongApplicationTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void WrongAppName() throws RuntimeException, IOException {
        PrintStream console = null;
    
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("xx: unknown application");
        Jsh.start("xx", console);
    }
}