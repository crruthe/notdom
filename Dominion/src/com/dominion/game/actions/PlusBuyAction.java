package com.dominion.game.actions;

import com.dominion.game.GameState;


public class PlusBuyAction implements CardAction {
	private int numBuys;
	
	public PlusBuyAction(int numBuys) {
		this.numBuys = numBuys;
	}

	@Override
	public void execute(GameState state) {
		state.getTurnState().incrementBuys(numBuys);		
	}	
}
