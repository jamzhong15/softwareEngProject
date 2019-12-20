package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class SeqTest {
    // sequence normal work test 很奇怪，scn只能拿到最后一个command的结果
    @Test
    public void SeqNormalTest() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);        
        Jsh.start("echo hi; echo hello", out);
        Scanner scn = new Scanner(in);
        assertEquals("hello", scn.next());
        scn.close();
    }

    // @Rule
    // public ExpectedException thrown = ExpectedException.none();
    // // 要不要测试这个呢
    // @Test
    // public void HeadMissingArgumentThrowsException() throws RuntimeException, IOException {
    // PrintStream console = null;

    // console = System.out;
    // thrown.expect(RuntimeException.class);
    // thrown.expectMessage("xx: unknown application");
    // Jsh.start("echo hi;xx", console);
    // }
}