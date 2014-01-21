package com.dominion.game.actions;


public class PlusCardAction extends CardAction {
	private int numCards;
	
	public PlusCardAction(int numCards) {
		this.numCards = numCards;
	}

	@Override
	public void execute() {
		for (int i = 0; i < numCards; i++) {
			player.getCardHand().addCard(player.drawCard());
		}
	}
}
