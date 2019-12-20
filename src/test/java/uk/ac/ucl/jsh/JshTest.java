package uk.ac.ucl.jsh;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    EchoCmdTest.class,
    TailTest.class,
    CdTest.class,
    PwdTest.class,
    CatTest.class,
    HeadTest.class,
    LsTest.class,
    WrongApplicationTest.class,
    GrepTest.class
})
public class JshTest {
    
}

