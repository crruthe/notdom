package com.dominion.game.cards.kingdom;

import java.util.Collection;
import java.util.LinkedList;

import com.dominion.game.actions.AdventurerAction;
import com.dominion.game.actions.CardAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.visitors.CardVisitor;

public class AdventurerCard implements ActionCard {

	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String getDescription() {
		return "Adventurer";
	}

	public static final int COST = 6;
	
	@Override
	public int getCost() {
		return COST;
	}

	@Override
	public Collection<CardAction> buildActionList() {
		LinkedList<CardAction> cardActions = new LinkedList<CardAction>();
		
		cardActions.add(new AdventurerAction());
		
		return cardActions;
	}
}
