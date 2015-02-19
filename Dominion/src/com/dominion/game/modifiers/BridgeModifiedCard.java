package com.dominion.game.modifiers;

import com.dominion.game.cards.Card;
import com.dominion.game.visitors.CardVisitor;

public class BridgeModifiedCard extends Card implements ModifiedCard {
	private Card parentCard;
	
	public BridgeModifiedCard(Card parentCard) {
		this.parentCard = parentCard;
	}
	
	
	@Override
	public Card getParentCard() {
		return parentCard;
	}


	@Override
	public void accept(CardVisitor visitor) {
		throw new RuntimeException("Cannot visit a modified card");		
	}


	@Override
	public String getName() {
		return parentCard.getName();
	}


	@Override
	public int getCost() {
		// Return new cost, but don't go below zero
		return Math.max(parentCard.getCost() - 1, 0);
	}	
}
