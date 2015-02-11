package com.dominion.game.actions;

import com.dominion.game.GameState;
import com.dominion.game.Player;
import com.dominion.game.cards.Card;
import com.dominion.game.interfaces.messages.CardRevealedMessage;

public class SpyAction extends AttackAction {
	@Override
	public void execute(GameState state) {
		// Applies to current player as well
		action(state, state.getCurrentPlayer());
		
		super.execute(state);
	}

	@Override
	public void executeAttackOnPlayer(GameState state, Player victim) {
		action(state, victim);
	}	
	
	private void action(GameState state, Player victim) {
		// Reveal the top card
		Card card = victim.drawCard();
		state.broadcastToAllPlayers(new CardRevealedMessage(victim, card));
		
		// You may discard this card or leave it on the deck
		if (state.getCurrentPlayer().wantsToDiscardCard(card)) {
			victim.addCardToDiscardPile(card);
		} else {
			victim.addCardToCardDeck(card);
		}
	}
}
