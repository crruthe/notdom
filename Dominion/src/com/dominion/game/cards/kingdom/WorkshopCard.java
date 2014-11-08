package com.dominion.game.cards.kingdom;

import java.util.Collection;
import java.util.LinkedList;

import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.GainCardAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.visitors.CardVisitor;

public class WorkshopCard implements ActionCard {
	public static final int COST = 3;
	public static final String NAME = "Workshop";
	private final int GAIN_COST = 4;

	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public Collection<CardAction> buildActionList() {
		LinkedList<CardAction> cardActions = new LinkedList<CardAction>();
		
		cardActions.add(new GainCardAction(GAIN_COST));
		
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
