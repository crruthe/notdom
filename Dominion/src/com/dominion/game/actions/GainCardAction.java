package com.dominion.game.actions;

import com.dominion.game.Player;
import com.dominion.game.cards.Card;

public class GainCardAction implements CardAction {
	private final int gainCost;
	
	public GainCardAction(int gainCost) {
		this.gainCost = gainCost;
	}
	
	@Override
	public void execute(Player player) {
		Card card = player.getCardToGain(gainCost);
		
		if (card != null) {
			player.gainCardFromSupply(card.getName());
		}
	}
}
