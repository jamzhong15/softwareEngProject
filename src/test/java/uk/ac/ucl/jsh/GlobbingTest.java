package uk.ac.ucl.jsh;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class GlobbingTest {
    Jsh jsh = new Jsh();
    
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void buildTestFile() throws Exception {
        jsh.setcurrentDirectory(folder.getRoot().getAbsolutePath());
        File test_folder = folder.newFolder("testFolder");
        File sub_folder1 = folder.newFolder("testFolder", "sub1");
        File sub_folder2 = folder.newFolder("testFolder", "sub2");
        test_folder.mkdir();
        sub_folder1.mkdir();
        sub_folder2.mkdir();

        jsh.setcurrentDirectory(folder.getRoot().getAbsolutePath());
        // File globbing1 = folder.newFile("globbing1.txt");
        // globbing1.createNewFile();

        // String absoluteFilePath = folder.getRoot().getAbsolutePath() + File.separator + "globbing1.txt";
        File globbing1 = folder.newFile("globbing1.txt");
        String testedStrings1 = "globbing1\n";
        FileOutputStream file_writer1 = new FileOutputStream(globbing1);
        file_writer1.write(testedStrings1.getBytes());
        file_writer1.close();

        // String absoluteFilePath1 = folder.getRoot().getAbsolutePath() + File.separator + "sub1" + File.separator + "globbing2.txt";
        // File globbing2 = new File(absoluteFilePath1);
        // String testedStrings2 = "globbing2\n";
        // FileOutputStream file_writer2 = new FileOutputStream(globbing2);
        // file_writer2.write(testedStrings2.getBytes());
        // file_writer2.close();

        // String absoluteFilePath2 = folder.getRoot().getAbsolutePath() + File.separator + "sub2" + File.separator + "globbing3.txt";
        // File globbing3 = new File(absoluteFilePath2);
        // String testedStrings3 = "globbing3\n";
        // FileOutputStream file_writer3 = new FileOutputStream(globbing3);
        // file_writer3.write(testedStrings3.getBytes());
        // file_writer3.close();
    }

    @After
    public void deleteTestFile() throws Exception {
        jsh.setcurrentDirectory(System.getProperty("user.dir"));
        folder.delete();

        File file = new File("globbing1.txt");
        file.delete();

        // File file1 = new File("globbing2.txt");
        // file1.delete();

        // File file2 = new File("globbing3.txt");
        // file2.delete();
    }

    @Test
    public void globbingCatTest() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);        
        jsh.start("cat *.txt", out);
        Scanner scn = new Scanner(in);
        assertEquals("globbing1", scn.nextLine());
        // assertEquals("globbing2", scn.nextLine());
        // assertEquals("globbing3", scn.nextLine());
        scn.close();
    }

}