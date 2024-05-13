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
public class OrderTest {
    
    static Order o;
    Order expected;
    Order result;


    @BeforeClass
    public static void setUpClass() {
        o = new Order();
        o.add(new Product("Book", 600));
        o.add(new Product("Toy", 250));
    }

    @Test
    public void addProductTest() {
        expected = new Order();
        expected.add(new Product("Book", 600));
        expected.add(new Product("Toy", 250));
        expected.add(new Product("Paper", 100));
        result = o;
        result.add(new Product("Paper", 100));
        assertEquals(expected, result);
    }

    @Test
    public void removeProductTest() {
        expected = new Order();
        expected.add(new Product("Book", 600));
        result = o;
        result.remove(1);
        assertEquals(expected, result);
    }
    
}
