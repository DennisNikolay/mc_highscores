package com.lamename.mc;

import com.google.inject.Guice;
import com.google.inject.Injector;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


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
        injector = Guice.createInjector();
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite(AppIntegrationTest.class);
    }

    public void testSuccess(){
        assertTrue(true);
    }

}
