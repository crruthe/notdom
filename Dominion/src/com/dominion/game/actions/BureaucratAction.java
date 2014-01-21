package com.dominion.game.actions;

import com.dominion.game.GameMaster;
import com.dominion.game.Player;
import com.dominion.game.cards.Card;

public class BureaucratAction extends AttackAction {
	@Override
	public void execute() {
		player.getCardDeck().addCard(GameMaster.getInstance().getGameBoard().getSilverCard());
		super.execute();
	}

	@Override
	public void executeAttackOnPlayer(Player player) {
		Card card = player.getPlayerInterface().getVictoryCardToReveal();
		if (card != null) {
			player.getCardDeck().addCard(card);
		}		
	}	
}
