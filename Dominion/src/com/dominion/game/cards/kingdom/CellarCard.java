package com.dominion.game.cards.kingdom;

import java.util.Collection;
import java.util.LinkedList;

import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.CellarAction;
import com.dominion.game.actions.PlusActionAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.visitors.CardVisitor;

public class CellarCard implements ActionCard {

	@Override
	public boolean equals(Object obj) {
		// Only really card if card types match
		return this.getClass().isInstance(obj);
	}
	
	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String getDescription() {
		return "Cellar";
	}

	public static final int COST = 2;
	
	@Override
	public int getCost() {
		return COST;
	}

	private final int PLUS_ACTIONS = 1;
	
	@Override
	public Collection<CardAction> buildActionList() {
		LinkedList<CardAction> cardActions = new LinkedList<CardAction>();
		
		cardActions.add(new PlusActionAction(PLUS_ACTIONS));
		cardActions.add(new CellarAction());
		
		return cardActions;
	}
}
