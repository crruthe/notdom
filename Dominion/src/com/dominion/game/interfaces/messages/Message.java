package com.dominion.game.interfaces.messages;

public class Message {
	private String message; 
	private Object data;
	
	public Message(String message, Object data) {
		this.message = message;
		this.data = data;
	}
}
