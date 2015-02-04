package com.dominion.game.interfaces.messages;

import com.dominion.game.Player;
import com.dominion.game.cards.Card;
import com.dominion.game.interfaces.PlayerInterface;

public class CardGainedMessage implements NotifyMessage {

	private Player player;
	private Card card;
	
	public CardGainedMessage(Player player, Card card) {
		this.player = player;
		this.card = card;
	}
	
	@Override
	public void notify(PlayerInterface playerInterface) {		
		playerInterface.notifyCardGained(player, card);
	}
}
