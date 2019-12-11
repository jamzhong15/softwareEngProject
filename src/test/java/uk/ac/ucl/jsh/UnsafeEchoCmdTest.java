package uk.ac.ucl.jsh;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class UnsafeEchoCmdTest {
    PrintStream console = null; // 声明（为null）：输出流 (字符设备) console
	ByteArrayOutputStream bytes = null; // 声明（为null）：bytes 用于缓存console 重定向过来的字符流
    
    @Test
    public void testJsh() throws Exception {
        bytes = new ByteArrayOutputStream(); // 分配空间
        console = System.out; // 获取System.out 输出流的句柄
        System.setOut(new PrintStream(bytes));  // 将原本输出到控制台Console的字符流重定向到bytes
        Jsh.eval("echo foo", console);
        // String s = new String("foo");
        assertEquals("foo", bytes.toString().trim());
        System.setOut(System.out);
    }
    


    
}