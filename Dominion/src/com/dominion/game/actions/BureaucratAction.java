package com.dominion.game.actions;

import com.dominion.game.Player;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.basic.SilverCard;

public class BureaucratAction extends AttackAction {
	@Override
	public void execute() {
		player.gainCardFromSupplyToDeck(SilverCard.class.getName());
		super.execute();
	}

	@Override
	public void executeAttackOnPlayer(Player attackPlayer) {
		Card card = attackPlayer.getVictoryCardToReveal();
		if (card != null) {
			attackPlayer.moveCardFromHandToDeck(card);
		}		
	}	
}
