package com.dominion.game.cards.kingdom;

import java.util.Collection;
import java.util.LinkedList;

import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.RemodelAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.visitors.CardVisitor;

public class RemodelCard implements ActionCard {
	public static final int COST = 4;
	public static final String NAME = "Remodel";
	
	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public Collection<CardAction> buildActionList() {
		LinkedList<CardAction> cardActions = new LinkedList<CardAction>();
		
		cardActions.add(new RemodelAction());
		
		return cardActions;
	}

	@Override
	public boolean equals(Object obj) {
		// Cards are the same if names match (even if different instances)
		return NAME.equals(((Card)obj).getName());
	}
	
	@Override
	public int getCost() {
		return COST;
	}

	@Override
	public String getName() {
		return NAME;
	}
}
