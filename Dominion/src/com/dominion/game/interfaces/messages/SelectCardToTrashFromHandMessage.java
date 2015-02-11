package com.dominion.game.interfaces.messages;

import java.util.List;

import com.dominion.game.cards.Card;
import com.dominion.game.interfaces.PlayerInterface;

public class SelectCardToTrashFromHandMessage implements PlayerInterfaceMessage {

	private List<Card> cards;
	private Card result;
	
	public SelectCardToTrashFromHandMessage(List<Card> cards) {
		this.cards = cards;
	}
	
	@Override
	public void invoke(PlayerInterface playerInterface) {
		result = playerInterface.selectCardToTrashFromHand(cards);
	}
	
	public Card getCard() {
		return result;
	}
}
