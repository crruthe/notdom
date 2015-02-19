package com.dominion.game.actions;

import com.dominion.game.GameState;
import com.dominion.game.Player;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.basic.SilverCard;
import com.dominion.game.interfaces.messages.CardRevealedMessage;
import com.dominion.game.interfaces.messages.HandRevealedMessage;

public class BureaucratAction extends AttackAction {
	@Override
	public void execute(GameState state) {
		
		// Add a silver to the top of their deck
		Card card = state.getGameBoard().removeCardFromSupply(SilverCard.class);
		state.getCurrentPlayer().addCardToCardDeck(card);
		
		super.execute(state);
	}

	@Override
	public void executeAttackOnPlayer(GameState state, Player victim) {		
		Card card = victim.getVictoryCardToReveal();
		if (card != null) {
			// Reveal the victory card and place it on the top of their deck
			state.broadcastToAllPlayers(new CardRevealedMessage(victim, card));
			victim.moveCardFromHandToDeck(card);
		} else {
			// Player had no victory cards, so reveal their whole hand
			state.broadcastToAllPlayers(new HandRevealedMessage(victim, victim.getHand()));
		}
	}	
}
