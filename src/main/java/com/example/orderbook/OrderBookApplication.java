package com.example.orderbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.TextMessage;

@SpringBootApplication
public class OrderBookApplication implements ApplicationRunner {

	@Autowired
	private WebSocketClient webSocketClient;

	public static void main(String[] args) {
		SpringApplication.run(OrderBookApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		String product = args.getNonOptionArgs().get(0);
		System.out.println( "Getting OrderBook For the Product - " + product);
		String payload = "{\"type\": \"subscribe\",\"product_ids\": [\"" + product +"\"], \"channels\": [\"level2\"]}";
		webSocketClient.getClientSession().sendMessage(new TextMessage(payload));

        while(true) Thread.sleep(60000);
	}
}
