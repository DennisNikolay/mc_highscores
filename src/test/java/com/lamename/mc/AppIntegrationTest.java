package com.lamename.mc;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.lamename.mc.repositories.PlayerScoreRepository;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import javax.persistence.EntityManager;


/**
 * Unit test for simple App.
 */
public class AppIntegrationTest
    extends TestCase
{

    protected Injector injector;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppIntegrationTest( String testName )
    {
        super(testName);
        injector = Guice.createInjector(new TestDatabaseModule());
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite(AppIntegrationTest.class);
    }


    public void testInjectionAndJpaSetupWorks()
    {
        PlayerScoreRepository ps = injector.getInstance(PlayerScoreRepository.class);
        assertNotNull(ps);
        EntityManager em = injector.getInstance(EntityManager.class);
        assertNotNull(em);
    }

    public void testDatabaseAccessWorks()
    {

    }

}
