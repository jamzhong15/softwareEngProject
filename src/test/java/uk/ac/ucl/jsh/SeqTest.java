package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;


public class SeqTest {
    // sequence normal work test
    @Test
    public void SeqNormalTest() throws Exception {
        Jsh jsh = new Jsh();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        jsh.start("echo hello ; echo world", System.out);
        assertEquals("hello\nworld\n", outContent.toString());
    }

    // @Rule
    // public ExpectedException thrown = ExpectedException.none();
    // // 要不要测试这个呢 回答：感觉不需要吧，这个test比较general,可以另外分一类test class来写
    // @Test
    // public void HeadMissingArgumentThrowsException() throws RuntimeException, IOException {
    // PrintStream console = null;

    // console = System.out;
    // thrown.expect(RuntimeException.class);
    // thrown.expectMessage("xx: unknown application");
    // Jsh.start("echo hi;xx", console);
    // }
}