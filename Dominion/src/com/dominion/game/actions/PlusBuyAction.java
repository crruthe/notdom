package com.dominion.game.actions;


public class PlusBuyAction extends CardAction {
	private int numBuys;
	
	public PlusBuyAction(int numBuys) {
		this.numBuys = numBuys;
	}

	@Override
	public void execute() {
		player.getTurnState().incrementBuys(numBuys);		
	}	
}
