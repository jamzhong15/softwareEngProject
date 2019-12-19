package uk.ac.ucl.jsh;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

public class JshTest {
    public JshTest()
    {
    }

    @Test (expected = RuntimeException.class)
    public void cdEmptyInputThrowsException() throws IOException 
    {
        Jsh.eval("cd", System.out);
    }

    
}
