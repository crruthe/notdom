package com.dominion.game.actions;

import java.util.HashMap;

import com.dominion.game.GameState;
import com.dominion.game.cards.Card;
import com.dominion.game.interfaces.messages.ActionSelectedMessage;
import com.dominion.game.interfaces.messages.CardTrashedMessage;

/**
 * 
 * @author Vomit
 *
 * Choose one: +2 Cards; or +2 Coins; or trash 2 cards from your hand.
 */
public class StewardAction implements CardAction {
	@Override
	public void execute(GameState state) {
		
		HashMap<String, CardAction> actions = new HashMap<String, CardAction>();
		
		actions.put("+2 Card", new PlusCardAction(2));
		actions.put("+2 Coin", new PlusCoinAction(2));
		actions.put("Trash 2 Cards", new CardAction() {			
			@Override
			public void execute(GameState state) {
				for (int i = 0; i < 2; i++) {
					// No cards to trash
					if (state.getCurrentPlayer().getHandSize() == 0) {
						return;
					}
					
					Card trashCard = null;
					while (trashCard == null) {
						trashCard = state.getCurrentPlayer().getCardToTrashFromHand();
					}					

					state.broadcastToAllPlayers(new CardTrashedMessage(state.getCurrentPlayer(), trashCard));

					state.getCurrentPlayer().removeFromHand(trashCard);
					state.getGameBoard().addToTrashPile(trashCard);					
				}				
			}
		});		
		
		String action = null;
		while (!actions.containsKey(action)) {
			action = state.getCurrentPlayer().getCardActionToPlay(actions);
		}
		
    	state.broadcastToAllPlayers(new ActionSelectedMessage(state.getCurrentPlayer(), action));
		
		// Then perform this action
		actions.get(action).execute(state);
	}
}
