package com.dominion.game.actions;

import com.dominion.game.GameState;
import com.dominion.game.Player;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.basic.CurseCard;

public class WitchAction extends AttackAction {
	@Override
	public void executeAttackOnPlayer(GameState state, Player victim) {
		Card curse = state.getGameBoard().removeCardFromSupply(CurseCard.class);
		victim.addCardToDiscardPile(curse);
	}
}
