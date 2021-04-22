package com.example.orderbook;


import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.TreeSet;

@Component
public class OrderBook {
    private TreeSet<Order> bids;
    private TreeSet<Order> asks;

    public OrderBook(){
        this.bids = new TreeSet<>(new BidsComparator());
        this.asks = new TreeSet<>(new AsksComparator());
    }

    public void addBid(String price, String quantity){
        if(quantity.equals("0.00000000")){
            this.bids.remove(new Order(price, quantity));
        }
        else{
            this.bids.add(new Order(price, quantity));
            if(this.bids.size() > Constants.ORDERBOOK_SIZE ){
                this.bids.pollLast();
            }
        }
    }

    public void addAsk(String price, String quantity){

        if(quantity.equals("0.00000000")){
            this.asks.remove(new Order(price, quantity));
        }
        else{
            this.asks.add(new Order(price, quantity));
            if( this.asks.size() > Constants.ORDERBOOK_SIZE ) {
                this.asks.pollLast();
            }
        }
    }

    @Override
    public String toString() {
        return "[" +
                "bids=" + bids.toString() +
                ", asks=" + asks.toString() +
                ']';
    }
}

class BidsComparator implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        return Double.compare(Double.parseDouble(o2.price), Double.parseDouble(o1.price));
    }
}

class AsksComparator implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        return Double.compare(Double.parseDouble(o1.price), Double.parseDouble(o2.price));
    }
}
