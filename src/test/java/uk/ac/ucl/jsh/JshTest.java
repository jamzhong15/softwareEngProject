package uk.ac.ucl.jsh;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    EchoCmdTest.class,
    UnsafeEchoCmdTest.class
})
public class JshTest {
<<<<<<< HEAD
=======
    public JshTest() {
    }

    // @Test
    // public void testJsh() throws Exception {
    //     PipedInputStream in = new PipedInputStream();
    //     PipedOutputStream out;
    //     out = new PipedOutputStream(in);
    //     Jsh.eval("echo foo", out);
    //     Scanner scn = new Scanner(in);
    //     assertEquals(scn.nextLine(),"foo");
    // }

>>>>>>> yingming
    
}

