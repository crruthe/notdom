package com.dominion.game.actions;

import com.dominion.game.Player;
import com.dominion.game.cards.Card;
import com.dominion.game.interfaces.messages.CardRevealedMessage;

public class SpyAction extends AttackAction {
	@Override
	public void execute() {
		action(player);
		super.execute();
	}

	@Override
	public void executeAttackOnPlayer(Player attackPlayer) {
		action(attackPlayer);
	}	
	
	private void action(Player attackPlayer) {
		// Reveal the top card
		Card card = attackPlayer.drawCard();
		player.invokeMessageAll(new CardRevealedMessage(attackPlayer, card));
	
		
		// You may discard this card or leave it on the deck
		if (player.wantsToDiscardCard(card)) {
			attackPlayer.addCardToDiscardPile(card);
		} else {
			attackPlayer.addCardToCardDeck(card);
		}
	}
}
