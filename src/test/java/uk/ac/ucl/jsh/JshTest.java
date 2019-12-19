package uk.ac.ucl.jsh;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

<<<<<<< HEAD
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
=======

public class JshTest {

    @Test
    public void runTest() throws Exception
    {
        Result result = JUnitCore.runClasses(JshTestSuite.class);
>>>>>>> master

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }
}

