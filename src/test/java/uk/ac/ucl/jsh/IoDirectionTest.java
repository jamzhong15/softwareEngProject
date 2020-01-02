package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

public class IoDirectionTest {
    Jsh jsh = new Jsh();

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void buildTestFolder() throws Exception
    {
        jsh.setcurrentDirectory(folder.getRoot().getAbsolutePath());
        File io_test_file = folder.newFile("io_test.txt");
        String testedStrings1 = "first line";
        FileOutputStream file_writer1 = new FileOutputStream(io_test_file);
        file_writer1.write(testedStrings1.getBytes());
        file_writer1.close();

        File io_test_file2 = folder.newFile("io_test2.txt");
        String testedStrings2 = "first line";
        FileOutputStream file_writer2 = new FileOutputStream(io_test_file2);
        file_writer2.write(testedStrings2.getBytes());
        file_writer2.close();
    }
    
    @After
    public void resetUserDirectory()
    {
        jsh.setcurrentDirectory(System.getProperty("user.dir"));
        folder.delete();
    }

    // < normal test
    @Test
    public void inputStreamRedirectionTest() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        jsh.start("cat < io_test.txt", out);
        out.close();
        Scanner scn = new Scanner(in);
        assertEquals("first line", scn.nextLine());
        scn.close();
    }

    // > normal test
    @Test
    public void outputStreamRedirectionTest() throws Exception
    {
        jsh.start("echo string one > io_test.txt", System.out);
        String path = folder.getRoot().getAbsolutePath() + File.separator + "io_test.txt";
        BufferedReader testFileReader = new BufferedReader(new FileReader(path));
        assertEquals("string one", testFileReader.readLine());
        testFileReader.close();
    }

    // combined use of < and > test
    @Test
    public void combinedInputIoAndOutputIOTest() throws Exception
    {
        jsh.start("cat < io_test.txt > combined_test.txt", System.out);
        String path = folder.getRoot().getAbsolutePath() + File.separator + "combined_test.txt";
        BufferedReader testFileReader = new BufferedReader(new FileReader(path));
        assertEquals("first line", testFileReader.readLine());
        testFileReader.close();
    }

    @Test
    public void globbedArgForInputstreamRedirectionTest() throws Exception
    {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jsh.start("cat < io*", out);
        out.close();
        Scanner scn = new Scanner(in);
        assertEquals("first line", scn.nextLine());
        assertEquals("first line", scn.nextLine());
        scn.close();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // too many >
    @Test
    public void IoTooManyFileOutputTest() throws Exception {
        Jsh jsh = new Jsh();
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("IO-redirection: too many redirection operand"));
        jsh.start("cat io_test.txt > > ioTest.txt", System.out);
    }

    // // too many <
    @Test
    public void IoTooManyFileInputTest() throws Exception {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("IO-redirection: too many redirection operand"));
        jsh.start("cat <  < io_test.txt", System.out);
    }

    // contains  < and >
    @Test
    public void noFilesGivenForOutIOTest() throws Exception {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("Outpustream redirection: parse error near '\\n'"));
        jsh.start("cat < io_test.txt >", System.out);
    }

    // contains >
    @Test
    public void OutputNullFileGivenTest() throws Exception {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("Outpustream redirection: parse error near '\\n'"));
        jsh.start("cat io_test.txt >", System.out);
    }

    @Test
    public void OutputTooManyFileGivenTest() throws Exception {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("Outputstream redirection: too many files given as outputstream"));
        jsh.start("cat io_test.txt > io_test.txt io_test.txt", System.out);
    }

    @Test
    public void combinedIOButOutputTooManyFileGivenTest() throws Exception
    {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("Outputstream redirection: too many files given as outputstream"));
        jsh.start("cat < io_test.txt > io_test.txt io_test.txt", System.out);
    }

    @Test
    public void InputNullFileGivenTest() throws Exception {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo(null));
        jsh.start("cat <", System.out);
    }

    @Test
    public void InputNullFileGivenTestCombinedIOcaseOne() throws Exception {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo(null));
        jsh.start("cat < > io_test.txt", System.out);
    }

    @Test
    public void InputNullFileGivenTestCombinedIOcaseTwo() throws Exception
    {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo(null));
        jsh.start("echo test > io_test.txt ; cat <", System.out);
    }

}