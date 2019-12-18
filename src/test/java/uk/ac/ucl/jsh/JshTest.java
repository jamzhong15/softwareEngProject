package uk.ac.ucl.jsh;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    EchoCmdTest.class,
    CdTest.class,
    PwdTest.class
})
public class JshTest {
    
}

