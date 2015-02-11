package com.dominion.game.actions;

import java.util.List;

import com.dominion.game.GameState;
import com.dominion.game.cards.Card;

public class GainCardAction implements CardAction {
	private final int gainCost;
	
	public GainCardAction(int gainCost) {
		this.gainCost = gainCost;
	}
	
	@Override
	public void execute(GameState state) {
		List<Card> cards = state.getGameBoard().listCardsFilterByCost(gainCost);
		Card card = state.getCurrentPlayer().getCardToBuy(cards);
		
		if (card != null) {
			state.getGameBoard().removeCardFromSupply(card.getClass());
			state.getCurrentPlayer().addCardToDiscardPile(card);
		}
	}
}
