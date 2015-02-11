package com.dominion.game.actions;

import com.dominion.game.Player;
import com.dominion.game.cards.Card;
import com.dominion.game.interfaces.messages.CardRevealedMessage;
import com.dominion.game.interfaces.messages.ChooseIfDiscardCardMessage;

public class SpyAction extends AttackAction {
	@Override
	public void execute(Player player) {
		// Applies to attacking player as well
		action(player, player);
		
		super.execute(player);
	}

	@Override
	public void executeAttackOnPlayer(Player attacker, Player victim) {
		action(attacker, victim);
	}	
	
	private void action(Player attacker, Player victim) {
		// Reveal the top card
		Card card = victim.drawCard();
		attacker.invokeMessageAll(new CardRevealedMessage(victim, card));
	
		ChooseIfDiscardCardMessage message = new ChooseIfDiscardCardMessage(card);
		attacker.invokeMessage(message);
		
		// You may discard this card or leave it on the deck
		if (message.isYes()) {
			victim.addCardToDiscardPile(card);
		} else {
			victim.addCardToCardDeck(card);
		}
	}
}
