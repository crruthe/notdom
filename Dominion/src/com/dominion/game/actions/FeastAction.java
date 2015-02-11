package com.dominion.game.actions;

import com.dominion.game.Player;
import com.dominion.game.cards.Card;

public class FeastAction implements CardAction {
	private Card trashCard;
	
	public FeastAction(Card trashCard) {
		this.trashCard = trashCard;
	}

	@Override
	public void execute(Player player) {
		player.trashCardFromPlayArea(trashCard);
		
		CardAction gainCardAction = new GainCardAction(5);
		gainCardAction.execute(player);
	}	
}
