package uk.ac.ucl.jsh;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    EchoTest.class,
    CdTest.class,
    PwdTest.class,
    CatTest.class,
    HeadTest.class,
    LsTest.class,
    TailTest.class,
    WrongApplicationTest.class,
    GrepTest.class,
    SedTest.class,
    SeqTest.class,
    FindTest.class,
    WcTest.class,
    IoRedirectionTest.class,
    CommandSubTest.class,
    GlobbingTest.class,
    MainTest.class
})
public class JshTestSuite {
    
}

