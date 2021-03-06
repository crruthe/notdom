package com.dominion.game.actions;

import com.dominion.game.cards.Card;

public class GainCardAction extends CardAction {
	private final int gainCost;
	
	public GainCardAction(int gainCost) {
		this.gainCost = gainCost;
	}
	
	@Override
	public void execute() {
		Card card = player.getPlayerInterface().getCardToGain(gainCost);
		player.gainCard(card);
	}
}
