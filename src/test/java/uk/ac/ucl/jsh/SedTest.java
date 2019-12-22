package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SedTest
{
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void SedMissingArgumentThrowsException() throws RuntimeException, IOException
    {
        Jsh jsh = new Jsh();
        PrintStream console = null;

        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("sed: missing arguments"));
        jsh.start("sed", console);
    }

    @Test
    public void SedWrongReplacementFormatThrowsException() throws RuntimeException, IOException
    {
        Jsh jsh = new Jsh();
        PrintStream console = null;

        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("sed: wrong replacement format, replace a/ with s/"));
        jsh.start("sed a/hi/hello test", console);
    }

    
}