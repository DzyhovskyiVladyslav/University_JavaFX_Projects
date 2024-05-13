/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab4rai;

import java.util.Objects;

/**
 *
 * @author Vladyslav Dzyhovskyi
 */
public class Product {
    private String name;
    private double price;
    private boolean inStock;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
        this.inStock = true;
    }

    public Product incresePrice(double value){
        setPrice(getPrice() + value);
        return this;
    }

    public Product buyProd(){
        setInStock(false);
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, inStock);
    }

    @Override
    public String toString() {
        return "Product{" + "name='" + name + '\'' + ", price=" + price + ", inStock=" + inStock + '}';
    }
}
