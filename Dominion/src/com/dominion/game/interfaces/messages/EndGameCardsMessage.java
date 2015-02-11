package com.dominion.game.interfaces.messages;

import com.dominion.game.Player;
import com.dominion.game.cards.Card;
import com.dominion.game.interfaces.PlayerInterface;

public class EndGameCardsMessage implements PlayerInterfaceMessage {

	private Player player;
	private String cards;
	
	public EndGameCardsMessage(Player player, String cards) {
		this.player = player;
		this.cards = cards;
	}
	
	@Override
	public void invoke(PlayerInterface playerInterface) {		
		playerInterface.notifyEndGameCards(player, cards);
	}
}
