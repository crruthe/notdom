package com.dominion.game.interfaces.messages;

import java.util.List;

import com.dominion.game.cards.Card;
import com.dominion.game.interfaces.PlayerInterface;

public class SelectCardToPutOnDeckMessage implements PlayerInterfaceMessage {

	private List<Card> cards;
	private Card result = null;
	
	public SelectCardToPutOnDeckMessage(List<Card> cards) {
		this.cards = cards;
	}
	
	@Override
	public void invoke(PlayerInterface playerInterface) {
		result = playerInterface.selectCardToPutOnDeck(cards);
	}
	
	public Card getCard() {
		return result;
	}
}
