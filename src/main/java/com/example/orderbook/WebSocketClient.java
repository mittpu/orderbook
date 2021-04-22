package com.example.orderbook;

import com.google.gson.Gson;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.util.concurrent.ExecutionException;

@Component
public class WebSocketClient extends TextWebSocketHandler {
    private WebSocketSession clientSession;

    @Autowired
    private OrderBook orderBook;

    @Autowired
    private Helper helper;

    @Autowired
    private Gson gson;

    public WebSocketSession getClientSession() {
        return clientSession;
    }

    public WebSocketClient() throws ExecutionException, InterruptedException {
        StandardWebSocketClient webSocketClient = new StandardWebSocketClient();
        this.clientSession = webSocketClient.doHandshake(this, new WebSocketHttpHeaders(), URI.create("wss://ws-feed.pro.coinbase.com")).get();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        helper.processMessage(payload, orderBook);
        System.out.println(gson.toJson(orderBook));
    }
}
