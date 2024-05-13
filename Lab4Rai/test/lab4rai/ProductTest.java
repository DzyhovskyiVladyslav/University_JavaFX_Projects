/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab4rai;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vladyslav Dzyhovskyi
 */
public class ProductTest {
    
    static Product p1;
    static Product p2;
    Product expected;
    Product result;
    
    @BeforeClass
    public static void setUpClass() {
        p1 = new Product("Book", 600);
        p2 = new Product("Toy", 250);
    }

    @Test (expected = NullPointerException.class)
    public void incresePriceTest() {
        expected = new Product("Book", 1600);
        result = p1.incresePrice(1000);
        assertSame(expected, result);
    }

    @Test 
    public void buyProductTest() {
        expected = new Product("Toy", 250);
        expected.setInStock(false);
        result = p2;
        result.buyProd();
        assertSame(expected.isInStock(), result.isInStock());
    }
}
