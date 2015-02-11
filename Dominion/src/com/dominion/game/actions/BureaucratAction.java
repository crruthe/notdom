package com.dominion.game.actions;

import com.dominion.game.Player;
import com.dominion.game.cards.Card;
import com.dominion.game.cards.basic.SilverCard;

public class BureaucratAction extends AttackAction {
	@Override
	public void execute(Player player) {
		player.gainCardFromSupplyToDeck(SilverCard.NAME);
		super.execute(player);
	}

	@Override
	public void executeAttackOnPlayer(Player attacker, Player victim) {
		Card card = victim.getVictoryCardToReveal();
		if (card != null) {
			victim.moveCardFromHandToDeck(card);
		}		
	}	
}
