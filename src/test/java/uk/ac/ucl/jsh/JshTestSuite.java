package uk.ac.ucl.jsh;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    CatTest.class,
    CdTest.class,
    EchoCmdTest.class,
    HeadTest.class,
    LsTest.class,
    PwdTest.class,
    TailTest.class
})
public class JshTestSuite {
    
}

