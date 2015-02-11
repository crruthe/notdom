package com.dominion.game.actions;

import java.util.LinkedList;

import com.dominion.game.Player;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.TreasureCard;
import com.dominion.game.interfaces.messages.CardRevealedMessage;
import com.dominion.game.interfaces.messages.ChooseIfGainCardMessage;

public class ThiefAction extends AttackAction {
	@Override
	public void executeAttackOnPlayer(Player attacker, Player victim) {
		LinkedList<Card> discard = new LinkedList<Card>();
		
		// Reveal the top two cards of their deck
		Card first = victim.drawCard();
		Card second = victim.drawCard();		
		attacker.invokeMessageAll(new CardRevealedMessage(victim, first));
		attacker.invokeMessageAll(new CardRevealedMessage(victim, second));
		

		// If the any of the cards are treasure, you must trash one, discard the others
		
		Card trash = null;
		if (first instanceof TreasureCard && second instanceof TreasureCard) {
			if (attacker.wantsToTrashCard(first)) {
				victim.trashCard(first);
				trash = first;
				discard.add(second);
			} else {
				victim.trashCard(second);
				trash = second;
				discard.add(first);
			}
		} else if (first instanceof TreasureCard) {
			victim.trashCard(first);
			trash = first;
			discard.add(second);
		} else if (second instanceof TreasureCard) {
			victim.trashCard(second);
			trash = second;
			discard.add(first);
		} else {
			discard.add(first);
			discard.add(second);
		}
		
		// You may gain this trashed card
		if (trash != null) {
			ChooseIfGainCardMessage message = new ChooseIfGainCardMessage(trash);
			attacker.invokeMessage(message);

			if (message.isYes()) {
				attacker.addCardToDiscardPile(trash);
			}
		}
		
		// Discard the rest of the cards
		for (Card card: discard) {
			victim.addCardToDiscardPile(card);
		}
	}	
}
