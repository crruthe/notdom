package com.dominion.game.cards.kingdom;

import java.util.Collection;
import java.util.LinkedList;

import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.NoAction;
import com.dominion.game.actions.PlusCardAction;
import com.dominion.game.cards.ReactionCard;
import com.dominion.game.visitors.CardVisitor;

public class MoatCard implements ReactionCard {
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
		return "Moat";
	}

	public static final int COST = 2;
	
	@Override
	public int getCost() {
		return COST;
	}

	private final int PLUS_CARDS = 2;
	
	@Override
	public Collection<CardAction> buildActionList() {
		LinkedList<CardAction> cardActions = new LinkedList<CardAction>();
		
		cardActions.add(new PlusCardAction(PLUS_CARDS));
		
		return cardActions;
	}

	@Override
	public boolean grantsImmunity() {
		return true;
	}

	@Override
	public CardAction getReaction() {
		return new NoAction();
	}
}
