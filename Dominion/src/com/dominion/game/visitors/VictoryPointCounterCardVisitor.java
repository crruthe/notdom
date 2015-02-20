package com.dominion.game.visitors;

import com.dominion.game.cards.VictoryCard;
import com.dominion.game.cards.kingdom.DukeCard;
import com.dominion.game.cards.kingdom.GardensCard;

public class VictoryPointCounterCardVisitor extends CardVisitor {
	private int numOfCards = 0;
	private int numOfDuchies = 0;
	private int victoryPoints = 0;
	
	
	/**
	 * @return the victoryPoints
	 */
	public int getVictoryPoints() {
		return victoryPoints;
	}

	/**
	 * @param numOfCards the numberOfCards to set
	 */
	public void setNumOfCards(int numOfCards) {
		this.numOfCards = numOfCards;
	}

	public void setNumOfDuchies(int numOfDuchies) {
		this.numOfDuchies = numOfDuchies;
	}	

	@Override
	public void visit(DukeCard dukeCard) {
		victoryPoints += (int)(numOfDuchies * DukeCard.POINTS_PER_DUCHY);
	}
	
	@Override
	public void visit(GardensCard gardensCard) {
		victoryPoints += (int)(numOfCards / GardensCard.CARDS_PER_POINT);
	}

	@Override
	public void visit(VictoryCard victoryCard) {
		victoryPoints += victoryCard.getVictoryPoints();
	}	
}
