package com.dominion.game;

import java.util.LinkedList;

import com.dominion.game.interfaces.PlayerInterface;
import com.dominion.game.interfaces.messages.NotifyMessage;

public class PlayerBroadcaster {
	private LinkedList<PlayerInterface> playerInterfaces = new LinkedList<PlayerInterface>();
	
	public void registerInterface(PlayerInterface playerInterface) {
		playerInterfaces.add(playerInterface);
	}
	
	public void notify(NotifyMessage message) {
		for (PlayerInterface p: playerInterfaces) {
			message.notify(p);
		}
	}
}
 