package com.dominion.game.interfaces.messages;

import java.util.List;

import com.dominion.game.cards.Card;
import com.dominion.game.interfaces.PlayerInterface;

public class UpdateTrashPileMessage implements PlayerInterfaceMessage {

	private List<Card> cards;
	
	public UpdateTrashPileMessage(List<Card> cards) {		
		this.cards = cards;
	}
	
	@Override
	public void invoke(PlayerInterface playerInterface) {		
		playerInterface.updateTrashPile(cards);
	}
}
