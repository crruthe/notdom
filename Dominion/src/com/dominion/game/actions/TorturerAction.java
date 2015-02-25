package com.dominion.game.actions;

import java.util.HashMap;

import com.dominion.game.GameState;
import com.dominion.game.Player;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.basic.CurseCard;
import com.dominion.game.interfaces.messages.ActionSelectedMessage;
import com.dominion.game.interfaces.messages.CardGainedMessage;

/**
 * Each other player chooses one: 
 * he discards 2 cards; 
 * or he gains a Curse card, putting it in his hand.
 * 
 * @author user
 *
 */
public class TorturerAction extends AttackAction  {
	
	/**
	 * Player must discard two cards, if possible
	 * @author user
	 *
	 */
	class DiscardCardTorturerAction implements CardAction {
		
		private int numOfCards;
		private Player player;
		
		public DiscardCardTorturerAction(Player player, int numOfCards) {
			this.player = player;
			this.numOfCards = numOfCards;
		}
		
		@Override
		public void execute(GameState state) {
			for (int i = 0; i < numOfCards; i++) {
				// No cards to discard
				if (player.getHand().isEmpty()) {
					break;
				}
				
				// Player must discard cards
				Card card = null;
				while (card == null) {
					card = player.getCardToDiscard();
				}
				player.discardCardFromHand(card);			
			}
		}		
	}
	
	/**
	 * Player gains a curse and puts it in their hand
	 * @author user
	 *
	 */
	class GainCurseTorturerAction implements CardAction {
		
		private Player player;
		
		public GainCurseTorturerAction(Player player) {
			this.player = player;
		}
		
		@Override
		public void execute(GameState state) {
			
			if (player == null) {
				player = state.getCurrentPlayer();
			}
			
			Card curse = state.getGameBoard().removeCardFromSupply(CurseCard.class);
			
			// No curses left
			if (curse == null) {
				return;
			}
			player.addCardToHand(curse);
			state.broadcastToAllPlayers(new CardGainedMessage(player, curse));		
		}
		
	}

	@Override
	public void executeAttackOnPlayer(GameState state, Player victim) {
		HashMap<String, CardAction> actions = new HashMap<String, CardAction>();
		
		actions.put("Discard 2 cards", new DiscardCardTorturerAction(victim, 2));
		actions.put("Gain a curse", new GainCurseTorturerAction(victim));
		
		String action = null;
		while (!actions.containsKey(action)) {
			action = victim.getCardActionToPlay(actions);
		}

		
    	state.broadcastToAllPlayers(new ActionSelectedMessage(victim, action));
		
		// Then perform this action
		actions.get(action).execute(state);
	}
}
