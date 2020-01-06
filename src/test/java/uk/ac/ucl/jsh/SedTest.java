package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SedTest
{
    @Before
    public void buildTestFile() throws IOException
    {
        String absoluteFilePath = System.getProperty("user.dir") + File.separator + "sed_test.txt";
        File testFile = new File(absoluteFilePath);
        String testedStrings1 = "first line : this is first, first..\n";
        String testedStrings2 = "second line : this is second, @second@\n";

        FileOutputStream file_writer = new FileOutputStream(testFile);
        file_writer.write(testedStrings1.getBytes());
        file_writer.write(testedStrings2.getBytes());
        file_writer.close();
    }

    @After
    public void deleteTestFile()
    {
        File file = new File("sed_test.txt");
        file.delete();
    }

    @Test
    public void testMetaCharacterBackSlashDelimiter() throws Exception
    {
        Jsh jsh = new Jsh();
        
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        OutputStreamWriter writer = new OutputStreamWriter(out);
        BufferedReader originalFileReader = new BufferedReader(new FileReader("sed_test.txt"));

        String str;
        while ((str = originalFileReader.readLine()) != null)
        {
            writer.write((str.replaceFirst("first", "FIRST")));
            writer.write(System.getProperty("line.separator"));
            writer.flush();
        }
        writer.close();
        originalFileReader.close();

        jsh.start("sed s\\first\\FIRST sed_test.txt", System.out);
        BufferedReader editedFileReader = new BufferedReader(new FileReader("sed_test.txt"));
        BufferedReader pipeInReader = new BufferedReader(new InputStreamReader(in));
        
        String expectedStr;
        while ((expectedStr = pipeInReader.readLine()) != null)
        {
            String actualStr = editedFileReader.readLine();
            assertEquals(expectedStr, actualStr);
        }
        editedFileReader.close();
    }

    // sed without the g specifier, and operate on sed_test.txt file
    @Test
    public void SedNotGlobalAndOneFileNameArgumentTest() throws Exception
    {
        Jsh jsh = new Jsh();
        
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        OutputStreamWriter writer = new OutputStreamWriter(out);
        BufferedReader originalFileReader = new BufferedReader(new FileReader("sed_test.txt"));

        String str;
        while ((str = originalFileReader.readLine()) != null)
        {
            writer.write((str.replaceFirst("first", "FIRST")));
            writer.write(System.getProperty("line.separator"));
            writer.flush();
        }
        writer.close();
        originalFileReader.close();

        jsh.start("sed s/first/FIRST sed_test.txt", System.out);
        BufferedReader editedFileReader = new BufferedReader(new FileReader("sed_test.txt"));
        BufferedReader pipeInReader = new BufferedReader(new InputStreamReader(in));
        
        String expectedStr;
        while ((expectedStr = pipeInReader.readLine()) != null)
        {
            String actualStr = editedFileReader.readLine();
            assertEquals(expectedStr, actualStr);
        }
        editedFileReader.close();
    }

    // sed without the g specifier, and reading form stdin
    @Test
    public void SedNotGlobalWithOneArgumentTest() throws Exception
    {
        Jsh jsh = new Jsh();
        
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        OutputStreamWriter writerExpStr = new OutputStreamWriter(out);
        BufferedReader originalFileReader = new BufferedReader(new FileReader("sed_test.txt"));

        String str;
        while ((str = originalFileReader.readLine()) != null)
        {
            writerExpStr.write((str.replaceFirst("first", "FIRST")));
            writerExpStr.write(System.getProperty("line.separator"));
            writerExpStr.flush();
        }
        writerExpStr.close();
        originalFileReader.close();

        PipedInputStream stdin = new PipedInputStream();
        PipedOutputStream stdout = new PipedOutputStream(stdin);
        jsh.start("cat sed_test.txt | sed s/first/FIRST", stdout);
        
        BufferedReader stdinReader = new BufferedReader(new InputStreamReader(stdin));
        BufferedReader inReader = new BufferedReader(new InputStreamReader(in));

        String expectedStr;
        while ((expectedStr = inReader.readLine()) != null)
        {
            String actualStr = stdinReader.readLine();
            assertEquals(expectedStr, actualStr);
        }
    }

    // sed with the g specifier, and operate on sed_test.txt file
    @Test
    public void SedGlobalAndOneFileNameArgumentTest() throws Exception
    {
        Jsh jsh = new Jsh();
        
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        OutputStreamWriter writer = new OutputStreamWriter(out);
        BufferedReader originalFileReader = new BufferedReader(new FileReader("sed_test.txt"));

        String str;
        while ((str = originalFileReader.readLine()) != null)
        {
            writer.write((str.replaceAll("first", "FIRST")));
            writer.write(System.getProperty("line.separator"));
            writer.flush();
        }
        writer.close();
        originalFileReader.close();

        jsh.start("sed s/first/FIRST/g sed_test.txt", System.out);
        BufferedReader editedFileReader = new BufferedReader(new FileReader("sed_test.txt"));
        BufferedReader pipeInReader = new BufferedReader(new InputStreamReader(in));
        
        String expectedStr;
        while ((expectedStr = pipeInReader.readLine()) != null)
        {
            String actualStr = editedFileReader.readLine();
            assertEquals(expectedStr, actualStr);
        }
        editedFileReader.close();
    }

    // sed with global specifier and read from stdin
    @Test
    public void SedGlobalWithOneArgumentTest() throws Exception
    {
        Jsh jsh = new Jsh();
        
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        OutputStreamWriter writerExpStr = new OutputStreamWriter(out);
        BufferedReader originalFileReader = new BufferedReader(new FileReader("sed_test.txt"));

        String str;
        while ((str = originalFileReader.readLine()) != null)
        {
            writerExpStr.write((str.replaceAll("first", "FIRST")));
            writerExpStr.write(System.getProperty("line.separator"));
            writerExpStr.flush();
        }
        writerExpStr.close();
        originalFileReader.close();

        PipedInputStream stdin = new PipedInputStream();
        PipedOutputStream stdout = new PipedOutputStream(stdin);
        jsh.start("cat sed_test.txt | sed s/first/FIRST/g", stdout);
        
        BufferedReader stdinReader = new BufferedReader(new InputStreamReader(stdin));
        BufferedReader inReader = new BufferedReader(new InputStreamReader(in));

        String expectedStr;
        while ((expectedStr = inReader.readLine()) != null)
        {
            String actualStr = stdinReader.readLine();
            assertEquals(expectedStr, actualStr);
        }
    }

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

    @Test
    public void SedWrongGlobalSpecifierTHrowsException() throws RuntimeException, IOException
    {
        Jsh jsh = new Jsh();
        PrintStream console = null;

        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("sed: wrong global specifier, replace /global with /g"));
        jsh.start("sed s/hi/hello/global test", console);
    }
    
    @Test
    public void SedTooManyArgumentsInREPLACEMENTThrowsException() throws RuntimeException, IOException
    {
        Jsh jsh = new Jsh();
        PrintStream console = null;

        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("sed: too many arguments. Try s/hi/hello/g"));
        jsh.start("sed s/hi/hello/g/this test", console);
    }

    @Test
    public void SedCannotReadFromStdinThrowsException() throws RuntimeException, IOException
    {
        Jsh jsh = new Jsh();
        PrintStream console = null;

        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("sed: cannot read from stdin, please provide filename"));
        jsh.start("sed s/hi/hello", console);
    }

    @Test
    public void SedCannotOpenFileThrowsException() throws RuntimeException, IOException
    {
        Jsh jsh = new Jsh();
        PrintStream console = null;

        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("sed: file HelloWorld.txt does not exist"));
        jsh.start("sed s/hi/hello HelloWorld.txt", console);
    }

    @Test
    public void SedFileExistsButCannotBeOpenedThrowsException() throws RuntimeException, IOException
    {
        Jsh jsh = new Jsh();
        PrintStream console = null;

        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(CoreMatchers.equalTo("sed: cannot open src"));
        jsh.start("sed s/hi/hello src", console);
    }
}