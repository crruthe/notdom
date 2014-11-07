package com.dominion.game.cards.kingdom;

import java.util.Collection;
import java.util.LinkedList;

import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.LibraryAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.visitors.CardVisitor;

public class LibraryCard implements ActionCard {

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
		return "Library";
	}

	public static final int COST = 5;
	
	@Override
	public int getCost() {
		return COST;
	}

	@Override
	public Collection<CardAction> buildActionList() {
		LinkedList<CardAction> cardActions = new LinkedList<CardAction>();
		
		cardActions.add(new LibraryAction());
		
		return cardActions;
	}
}
