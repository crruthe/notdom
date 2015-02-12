package com.dominion.game.observers;

import java.util.Observable;
import java.util.Observer;

import com.dominion.game.CardCollection;
import com.dominion.game.GameState;
import com.dominion.game.interfaces.messages.UpdateTrashPileMessage;

public class TrashPileObserver implements Observer {

	private GameState state;
	
	public TrashPileObserver(GameState state) {
		this.state = state;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		CardCollection cards = (CardCollection)o;
		state.broadcastToAllPlayers(new UpdateTrashPileMessage(cards.getCards()));
	}
}
