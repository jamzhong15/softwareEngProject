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

public class cdEmptyInputTest {

    // @Test (expected = RuntimeException.class)
    // public void cdEmptyInputThrowsException() throws IOException {
    //     Jsh.eval("cd", System.out);
    //     // throw new RuntimeException();
    // }

    // @Rule
    // public ExpectedException thrown = ExpectedException.none();

    // @Test
    // public void cdEmptyInputThrowsException() throws RuntimeException, IOException {
    // PrintStream console = null;

    // console = System.out; // 获取System.out 输出流的句柄
    // thrown.expect(RuntimeException.class);
    // thrown.expectMessage("cd: missing argument");
    // Jsh.eval("cd", console);
    //   try {
    //       Jsh.eval("cd", System.out); 
    //       fail();
    //   } catch (RuntimeException e) {
    //     assertTrue(e.getMessage().contains("cd: missing argument"));

        //   assertEquals("cd: missing argument", e.getMessage());
    //   Fail("no exception thrown");
}