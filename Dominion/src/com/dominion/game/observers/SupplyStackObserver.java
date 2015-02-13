package com.dominion.game.observers;

import java.util.Observable;
import java.util.Observer;

import com.dominion.game.CardCollection;
import com.dominion.game.GameState;
import com.dominion.game.SupplyStack;
import com.dominion.game.interfaces.messages.UpdateSupplyMessage;
import com.dominion.game.interfaces.messages.UpdateTrashPileMessage;

public class SupplyStackObserver implements Observer {

	private GameState state;
	
	public SupplyStackObserver(GameState state) {
		this.state = state;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		SupplyStack stack = (SupplyStack)o;
		state.broadcastToAllPlayers(new UpdateSupplyMessage(stack.getStacks()));
	}
}
