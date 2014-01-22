package com.dominion.game.actions;

import com.dominion.game.Player;

abstract public class CardAction {
	
	protected Player player;
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	abstract public void execute();
}
