package com.dominion.game.actions;

import com.dominion.game.GameState;
import com.dominion.game.Player;

public abstract class AttackAction implements CardAction {

	@Override
	public void execute(GameState state) {
		attackOtherPlayers(state);
	}
	
	protected void attackOtherPlayers(GameState state) {		
		for (Player victim : state.getOtherPlayers()) {
			// If they played a moat, they are immune to attacks
			if (!victim.isImmune()) {
				executeAttackOnPlayer(state, victim);
			}
		}
	}
	
	abstract public void executeAttackOnPlayer(GameState state, Player victim);
}
