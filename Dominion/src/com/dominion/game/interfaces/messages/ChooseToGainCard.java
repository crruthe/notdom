package com.dominion.game.interfaces.messages;

import com.dominion.game.cards.Card;
import com.dominion.game.interfaces.PlayerInterface;

public class ChooseToGainCard implements PlayerInterfaceMessage {

	private Card card;
	private boolean result;
	
	public ChooseToGainCard(Card card) {
		this.card = card;
	}
	
	@Override
	public void invoke(PlayerInterface playerInterface) {
		result = playerInterface.chooseIfGainCard(card);
	}
	
	public boolean isYes() {
		return result;
	}
}