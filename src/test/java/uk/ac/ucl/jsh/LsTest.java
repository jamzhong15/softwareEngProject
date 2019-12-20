package uk.ac.ucl.jsh;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class LsTest {
    // ls no argument test
    @Test
    public void lsWithoutArgument() throws Exception {
        Jsh jsh = new Jsh();
        String currentDirectory = jsh.getcurrentDirectory();
        File currDir;
        currDir = new File(currentDirectory);
        ArrayList<String> listFiles = new ArrayList<>();
        File[] listOfFiles = currDir.listFiles();
        boolean atLeastOnePrinted = false;
        for (File file : listOfFiles) {
            if (!file.getName().startsWith(".")) {
                listFiles.add(file.getName().toString());
                atLeastOnePrinted = true;
            }
        }
        String listString = String.join("\t", listFiles);
        if (atLeastOnePrinted) {
            listString.concat("\r\n");
        }
        jsh.start("ls", System.out);
        assertEquals("analysis	test	Dockerfile	target	pom.xml	jsh	README.md	coverage	src", listString);
    }

    // ls one argument test
    @Test
    public void lsWithOneArgument() throws Exception {
        Jsh jsh = new Jsh();
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);        
        jsh.start("ls src", out);
        Scanner scn = new Scanner(in);
        assertEquals("test", scn.next());
        scn.close();
    }

    // too many arguments test
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void lsTooManyArgumentsThrowsException() throws RuntimeException, IOException {
        Jsh jsh = new Jsh();
        PrintStream console = null;
        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("ls: too many arguments");
        jsh.start("ls arg1 arg2", console);
    }

    // no such directory test
    @Test
    public void lsNoSuchDirectoryThrowsException() throws RuntimeException, IOException {
        Jsh jsh = new Jsh();
        PrintStream console = null;

        console = System.out;
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("ls: no such directory");
        jsh.start("ls xxx", console);
    }
}