package com.example.orderbook;

import lombok.Data;

import java.util.Objects;

@Data
public class Order {
    String price;
    String quantity;

    public Order(String price, String quantity){
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "["+ price + ", " + quantity + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order quote = (Order) o;
        return Objects.equals(price, quote.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
