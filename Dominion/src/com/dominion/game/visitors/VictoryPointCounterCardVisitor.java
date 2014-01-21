package com.dominion.game.visitors;

import com.dominion.game.cards.VictoryCard;
import com.dominion.game.cards.kingdom.GardensCard;

public class VictoryPointCounterCardVisitor extends CardVisitor {
	private int numberOfCards;
	private int victoryPoints;
	
	
	/**
	 * @param numberOfCards the numberOfCards to set
	 */
	public void setNumberOfCards(int numberOfCards) {
		this.numberOfCards = numberOfCards;
	}

	/**
	 * @return the victoryPoints
	 */
	public int getVictoryPoints() {
		return victoryPoints;
	}

	@Override
	public void visit(VictoryCard victoryCard) {
		victoryPoints += victoryCard.getVictoryPoints();
	}	

	@Override
	public void visit(GardensCard gardensCard) {
		victoryPoints += (int)(numberOfCards / 10);
	}
}
