package com.dominion.game.actions;

import com.dominion.game.Player;

abstract public class CardAction {
	protected Player player;
	
	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	abstract public void execute();
}
