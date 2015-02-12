package com.dominion.game.interfaces.messages;

import java.util.List;

import com.dominion.game.Player;
import com.dominion.game.cards.Card;
import com.dominion.game.interfaces.PlayerInterface;

public class HandRevealedMessage implements PlayerInterfaceMessage {

	private Player player;
	private List<Card> cards;
	
	public HandRevealedMessage(Player player, List<Card> cards) {
		this.player = player;
		this.cards = cards;
	}
	
	@Override
	public void invoke(PlayerInterface playerInterface) {		
		playerInterface.notifyHandRevealed(player, cards);
	}
}
