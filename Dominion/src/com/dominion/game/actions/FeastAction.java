package com.dominion.game.actions;

import com.dominion.game.cards.Card;

public class FeastAction extends CardAction {
	private Card trashCard;
	
	public FeastAction(Card trashCard) {
		this.trashCard = trashCard;
	}

	@Override
	public void execute() {
		player.trashCard(trashCard);
		
		CardAction gainCardAction = new GainCardAction(5);
		gainCardAction.setPlayer(player);
		gainCardAction.execute();
	}	
}
