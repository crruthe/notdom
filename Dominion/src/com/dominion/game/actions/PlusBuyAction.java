package com.dominion.game.actions;

import com.dominion.game.Player;


public class PlusBuyAction implements CardAction {
	private int numBuys;
	
	public PlusBuyAction(int numBuys) {
		this.numBuys = numBuys;
	}

	@Override
	public void execute(Player player) {
		player.incrementBuys(numBuys);		
	}	
}
