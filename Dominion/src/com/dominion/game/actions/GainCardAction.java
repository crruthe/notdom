package com.dominion.game.actions;

import java.util.List;

import com.dominion.game.GameState;
import com.dominion.game.Player;
import com.dominion.game.cards.Card;
import com.dominion.game.interfaces.messages.CardGainedMessage;

public class GainCardAction implements CardAction {
	private final int gainCost;
	private Player player;
	private boolean mustGain = false;
	
	// Saboteur action
	public GainCardAction(Player player, int gainCost, boolean mustGain) {
		this.gainCost = gainCost;
		this.player = player;
		this.mustGain = mustGain;
	}

	public GainCardAction(int gainCost, boolean mustGain) {
		this.gainCost = gainCost;
		this.mustGain = mustGain;
	}
	
	@Override
	public void execute(GameState state) {
		// gain card action can be applied to other players, e.g. Saboteur
		if (player == null) {
			player = state.getCurrentPlayer();
		}
		
		List<Card> cards = state.listCardsFilterByCost(gainCost);
		
		// no cards available to gain, so return
		if (cards.size() == 0) {
			return;
		}

		Card card = null;
		
		// if player must gain, cycle until they select a card
		do {
			card = player.getCardToGain(cards, gainCost);
		} while(mustGain && card == null);
		
		
		if (card != null) {
			state.getGameBoard().removeCardFromSupply(card.getClass());
			player.addCardToDiscardPile(card);
			state.broadcastToAllPlayers(new CardGainedMessage(player, card));
		}
	}
}
