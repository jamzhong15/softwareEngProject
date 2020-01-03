package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

public class IoRedirectionTest {
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

        File io_test_file3 = folder.newFile("io_test3.txt");
        String testedStrings3 = "from(test3.txt)";
        FileOutputStream file_writer3 = new FileOutputStream(io_test_file3);
        file_writer3.write(testedStrings3.getBytes());
        file_writer3.close();

    }
    
    @After
    public void resetUserDirectory()
    {
        jsh.setcurrentDirectory(System.getProperty("user.dir"));
        folder.delete();
    }

    @Test
    public void doubleQuotedArgumentsWithEmbeddedBackquotesTest() throws Exception
    {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jsh.start("echo Hello \"`echo \"World\"`\"", out);
        out.close();
        Scanner scn = new Scanner(in);
        assertEquals("Hello World", scn.nextLine());
        scn.close();
    }

    @Test
    public void singleQuotedArgumentsIgnoringEmbeddedBackquotesTest() throws Exception
    {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jsh.start("echo Hello '`echo \"World\"`'", out);
        out.close();
        Scanner scn = new Scanner(in);
        assertEquals("Hello `echo \"World\"`", scn.nextLine());
        scn.close();
    }

    // < normal test
    @Test
    public void inputStreamRedirectionTestOne() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        jsh.start("cat < io_test.txt", out);
        out.close();
        Scanner scn = new Scanner(in);
        assertEquals("first line", scn.nextLine());
        scn.close();
    }

    // < normal test with multiple filenames
    @Test
    public void inputStreamRedirectionTestTwo() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        jsh.start("cat < io_test.txt io_test3.txt", out);
        out.close();
        Scanner scn = new Scanner(in);
        assertEquals("from(test3.txt)", scn.nextLine());
        scn.close();
    }

    // > normal test with multiple filenames
    @Test
    public void outputStreamRedirectionMultipleArgsTestOne() throws Exception
    {
        jsh.start("echo string one > io_test.txt io_test2.txt", System.out);
        String path = folder.getRoot().getAbsolutePath() + File.separator + "io_test.txt";
        BufferedReader testFileReader = new BufferedReader(new FileReader(path));
        assertEquals("string one io_test2.txt", testFileReader.readLine());
        testFileReader.close();
    }

    @Test
    public void outputStreamRedirectionMultipleArgsTestTwo() throws Exception
    {
        jsh.start("cat io_test.txt > two_args.txt io_test2.txt", System.out);
        String path = folder.getRoot().getAbsolutePath() + File.separator + "two_args.txt";
        BufferedReader testFileReader = new BufferedReader(new FileReader(path));
        assertEquals("first line", testFileReader.readLine());
        assertEquals("first line", testFileReader.readLine());
        testFileReader.close();
    }    

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
    public void combinedInputIoAndOutputIOTestOne() throws Exception
    {
        jsh.start("cat < io_test.txt > combined_test.txt", System.out);
        String path = folder.getRoot().getAbsolutePath() + File.separator + "combined_test.txt";
        BufferedReader testFileReader = new BufferedReader(new FileReader(path));
        assertEquals("first line", testFileReader.readLine());
        testFileReader.close();
    }

    @Test
    public void combinedInputIoAndOutputIOTestTwo() throws Exception
    {
        jsh.start("cat < io_test.txt io_test2.txt > combined_test.txt", System.out);
        String path = folder.getRoot().getAbsolutePath() + File.separator + "combined_test.txt";
        BufferedReader testFileReader = new BufferedReader(new FileReader(path));
        assertEquals("first line", testFileReader.readLine());
        testFileReader.close();
    }

    @Test
    public void combinedInputIoAndOutputIOTestThree() throws Exception
    {
        jsh.start("cat < io_test.txt > combined_test.txt io_test2.txt", System.out);
        String path = folder.getRoot().getAbsolutePath() + File.separator + "combined_test.txt";
        BufferedReader testFileReader = new BufferedReader(new FileReader(path));
        assertEquals("first line", testFileReader.readLine());
        testFileReader.close();
    }


    @Test
    public void globbedArgForInputstreamRedirectionTest() throws Exception
    {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("first line");
        expected.add("from(test3.txt)");

        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jsh.start("cat < io*", out);
        out.close();
        Scanner scn = new Scanner(in);
        assertTrue("wrong contents", expected.contains(scn.nextLine()));
        assertTrue("wrong contents", expected.contains(scn.nextLine()));
        assertTrue("wrong contents", expected.contains(scn.nextLine()));
        scn.close();
    }

    @Test
    public void globbedArgForOutputstreamRedirectionTest() throws Exception
    {
        jsh.start("echo testing > io*", System.out);

        String path1 = folder.getRoot().getAbsolutePath() + File.separator + "io_test.txt";
        BufferedReader testFileReader1 = new BufferedReader(new FileReader(path1));
        assertEquals("testing", testFileReader1.readLine());
        testFileReader1.close();

        String path2 = folder.getRoot().getAbsolutePath() + File.separator + "io_test2.txt";
        BufferedReader testFileReader2 = new BufferedReader(new FileReader(path2));
        assertEquals("testing", testFileReader2.readLine());
        testFileReader2.close();
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

    @Test
    public void InputNullFileGivenTestCombinedIOcaseOne() throws Exception {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo(null));
        jsh.start("cat < > io_test.txt", System.out);
    }

    // contains >
    @Test
    public void OutputNullFileGivenTest() throws Exception {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("Outpustream redirection: parse error near '\\n'"));
        jsh.start("cat io_test.txt >", System.out);
    }

    @Test
    public void InputNullFileGivenTest() throws Exception {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo(null));
        jsh.start("cat <", System.out);
    }

    @Test
    public void InputNullFileGivenTestCombinedIOcaseTwo() throws Exception
    {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo(null));
        jsh.start("echo test > io_test.txt ; cat <", System.out);
    }

}