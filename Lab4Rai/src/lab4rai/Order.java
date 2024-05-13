/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab4rai;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Vladyslav Dzyhovskyi
 */
public class Order {
    private int number;
    private ArrayList<Product> list;

    public Order() {
        number = (int) (Math.random() * 10000);
        list = new ArrayList<Product>();
    }

    public void add(Product product){
        if (product.isInStock()) {
            list.add(product);
        }
    }

    public void remove(int n){
        if (list.get(n) != null) {
            list.remove(n);
        }
    }

    public void remove(Product product){
        if (product != null) {
            list.remove(product);
        }
    }
    
    public Product getProduct(int n) {
        if (list.get(n) != null) {
            return list.get(n);
        }
        return null;
    }

    public int getOrderNumber() {
        return number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, list);
    }
    
    @Override
    public boolean equals(Object o) {
        return true;
    }


    @Override
    public String toString() {
        String res = "";
        res = "Oreder{" + "number=" + number + ",\n list:\n"; 
        for (Product product : list) {
            res = res + product.toString() + "\n";
        }       
        res = res + '}';
        return res;
    }
}
