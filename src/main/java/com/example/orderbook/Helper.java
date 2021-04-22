package com.example.orderbook;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class Helper {

    public void processSnapshot(JSONObject snapshot, OrderBook orderBook){
        JSONArray asks = snapshot.getJSONArray(Constants.ASKS);
        for( int i = 0; i < 10; i++ ){
            JSONArray ask = asks.getJSONArray(i);
            String  price = ask.getString(0);
            String quantity = ask.getString(1);
            orderBook.addAsk(price, quantity);
        }

        JSONArray bids = snapshot.getJSONArray(Constants.BIDS);
        for( int i = 0; i < 10; i++ ){
            JSONArray bid = bids.getJSONArray(i);
            String price = bid.getString(0);
            String quantity = bid.getString(1);
            orderBook.addBid(price, quantity);
        }
    }

    public void processUpdate(JSONObject update, OrderBook orderBook){
        JSONArray changes = update.getJSONArray(Constants.CHANGES).getJSONArray(0);
        if(changes.getString(0).equals(Constants.BUY)){
            orderBook.addBid(changes.getString(1), changes.getString(2));
        }
        else{
            orderBook.addAsk(changes.getString(1), changes.getString(2));
        }
    }

    public void processMessage(String payload, OrderBook orderBook) {
        JSONObject payloadJson = new JSONObject(payload);

        if( payloadJson.has(Constants.TYPE)) {
            String type = payloadJson.getString(Constants.TYPE);
            switch (type) {
                case Constants.SUBSCRIPTIONS:
                    System.out.println("Subscription Message -- " + payload);
                    break;
                case Constants.SNAPSHOT:
                    this.processSnapshot(payloadJson, orderBook);
                    break;
                case Constants.L2UPDATE:
                    this.processUpdate(payloadJson, orderBook);
                    break;
            }
        }
    }
}
