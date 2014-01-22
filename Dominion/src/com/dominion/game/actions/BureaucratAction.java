package com.dominion.game.actions;

import com.dominion.game.Player;
import com.dominion.game.cards.Card;

public class BureaucratAction extends AttackAction {
	@Override
	public void execute() {
		player.addCardToDeck(player.getGameBoard().getSilverCard());
		super.execute();
	}

	@Override
	public void executeAttackOnPlayer(Player attackPlayer) {
		Card card = attackPlayer.getPlayerInterface().getVictoryCardToReveal();
		if (card != null) {
			attackPlayer.moveCardFromHandToDeck(card);
		}		
	}	
}
