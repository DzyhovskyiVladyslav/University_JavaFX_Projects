/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab4rai;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vladyslav Dzyhovskyi
 */
public class OrderTestTest {
    
    public OrderTestTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of setUp method, of class OrderTest.
     */
    @Test
    public void testSetUp() {
        System.out.println("setUp");
        OrderTest instance = new OrderTest();
        instance.setUp();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addProductTest method, of class OrderTest.
     */
    @Test
    public void testAddProductTest() {
        System.out.println("addProductTest");
        OrderTest instance = new OrderTest();
        instance.addProductTest();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeProductTest method, of class OrderTest.
     */
    @Test
    public void testRemoveProductTest() {
        System.out.println("removeProductTest");
        OrderTest instance = new OrderTest();
        instance.removeProductTest();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
