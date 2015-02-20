package com.dominion.game.visitors;

import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.TreasureCard;
import com.dominion.game.cards.VictoryCard;
import com.dominion.game.cards.basic.CurseCard;
import com.dominion.game.cards.kingdom.DukeCard;
import com.dominion.game.cards.kingdom.GardensCard;

/**
 * @author Vomit
 *  
 * Implements the visitor design pattern.
 * 
 * Given a collection of Cards, this allows access to the 
 * each card type without needing a list of 'instanceof' checks
 * to determine the subclass.
 */
public abstract class CardVisitor {
	public void visit(VictoryCard victoryCard) {
		// do nothing by default
	}
	
	public void visit(TreasureCard treasureCard) {
		// do nothing by default		
	}
	
	public void visit(CurseCard curseCard) {
		// do nothing by default		
	}
	
	public void visit(ActionCard actionCard) {
		// do nothing by default		
	}
	
	public void visit(GardensCard gardensCard) {
		// do nothing by default		
	}

	public void visit(DukeCard dukeCard) {
		// do nothing by default		
	}
}
