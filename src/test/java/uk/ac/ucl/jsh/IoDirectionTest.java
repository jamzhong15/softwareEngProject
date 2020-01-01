package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileOutputStream;
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
    // @Rule
    // public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void buildTestFolder() throws Exception
    {
        // jsh.setcurrentDirectory(folder.getRoot().getAbsolutePath());
        // File virtualSrcFolder = folder.newFolder("io_test");
        // File virtualMainFolder = new File(virtualSrcFolder.getAbsolutePath()+"/main");
        // virtualMainFolder.mkdir();

        String absoluteFilePath = System.getProperty("user.dir") + File.separator + "io_test.txt";
        File testFile = new File(absoluteFilePath);
        String testedStrings1 = "first line\n";

        FileOutputStream file_writer = new FileOutputStream(testFile);
        file_writer.write(testedStrings1.getBytes());
        file_writer.close();
    }
    
    @After
    public void resetUserDirectory()
    {
        // jsh.setcurrentDirectory(System.getProperty("user.dir"));

        File file = new File("io_test.txt");
        file.delete();
        
        // jsh.setcurrentDirectory(System.getProperty("user.dir"));
        // folder.delete();
    }

    // < normal test
    // @Test
    // public void IoInputTest() throws Exception {
    //     Jsh jsh = new Jsh();
        
    //     PipedInputStream in = new PipedInputStream();
    //     PipedOutputStream out;
    //     out = new PipedOutputStream(in);
    //     jsh.start("echo < io_test.txt", out);
    //     Scanner scn = new Scanner(in);
    //     assertEquals("first line", scn.nextLine());
    //     scn.close();
    // }

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

    // too many <
    @Test
    public void IoTooManyFileInputTest() throws Exception {
        Jsh jsh = new Jsh();
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("IO-redirection: too many redirection operand"));
        jsh.start("cat <  < io_test.txt", System.out);
    }

    // contains  < and >
    // indexOfOutputRedir + 1 >= appArgs.size()
    @Test
    public void IoTooManyFileGivenTest() throws Exception {
        Jsh jsh = new Jsh();
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("Outputstream redirection: too many files given as outputstream"));
        jsh.start("cat < io_test.txt >", System.out);
    }

    // contains >
    // IndexOfOutputRedir + 1 == appArgs.size()
    @Test
    public void OutputNullFileGivenTest() throws Exception {
        Jsh jsh = new Jsh();
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("Outputstream redirection: null file given as outputstream"));
        jsh.start("cat io_test.txt >", System.out);
    }

    // IndexOfOutputRedir + 1 > appArgs.size()
    // @Test
    // public void OutputTooManyFileGivenTest() throws Exception {
    //     Jsh jsh = new Jsh();
    //     thrown.expect(RuntimeException.class);
    //     thrown.expectMessage(CoreMatchers.equalTo("Outputstream redirection: too many files given as outputstream"));
    //     jsh.start("cat io_test.txt > io_test.txt io_test.txt", System.out);
    // }

    // contains <
    // indexOfInputRedif + 1 == appArgs.size()
    @Test
    public void InputNullFileGivenTest() throws Exception {
        Jsh jsh = new Jsh();
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("Inputstream redirection: null file given as inputstream"));
        jsh.start("cat io_test.txt <", System.out);
    }

}