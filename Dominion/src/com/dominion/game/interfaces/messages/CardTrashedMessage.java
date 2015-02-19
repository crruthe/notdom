package com.dominion.game.interfaces.messages;

import com.dominion.game.Player;
import com.dominion.game.cards.Card;
import com.dominion.game.interfaces.PlayerInterface;

public class CardTrashedMessage implements PlayerInterfaceMessage {

	private Player player;
	private Card card;
	
	public CardTrashedMessage(Player player, Card card) {
		this.player = player;
		this.card = card;
	}
	
	@Override
	public void invoke(PlayerInterface playerInterface) {		
		playerInterface.notifyCardTrashed(player, card);
	}
}
