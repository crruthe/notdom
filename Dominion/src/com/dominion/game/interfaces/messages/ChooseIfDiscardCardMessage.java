package com.dominion.game.interfaces.messages;

import com.dominion.game.cards.Card;
import com.dominion.game.interfaces.PlayerInterface;

public class ChooseIfDiscardCardMessage implements PlayerInterfaceMessage {

	private Card card;
	private boolean result;
	
	public ChooseIfDiscardCardMessage(Card card) {
		this.card = card;
	}
	
	@Override
	public void invoke(PlayerInterface playerInterface) {
		result = playerInterface.chooseIfDiscardCard(card);
	}
	
	public boolean isYes() {
		return result;
	}
}
