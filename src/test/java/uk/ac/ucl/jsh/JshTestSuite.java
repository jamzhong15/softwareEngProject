package uk.ac.ucl.jsh;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    EchoCmdTest.class,
    CdTest.class,
    PwdTest.class,
    CatTest.class,
    HeadTest.class,
    LsTest.class,
    TailTest.class,
    WrongApplicationTest.class,
    GrepTest.class,
    SedTest.class
})
public class JshTestSuite {
    
}

