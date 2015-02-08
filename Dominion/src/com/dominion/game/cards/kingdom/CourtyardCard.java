package com.dominion.game.cards.kingdom;

import java.util.Collection;
import java.util.LinkedList;

import com.dominion.game.actions.AdventurerAction;
import com.dominion.game.actions.CardAction;
import com.dominion.game.actions.CourtyardAction;
import com.dominion.game.actions.PlusCardAction;
import com.dominion.game.cards.ActionCard;
import com.dominion.game.cards.Card;
import com.dominion.game.visitors.CardVisitor;

public class CourtyardCard implements ActionCard {
	public static final int COST = 2;	
	public static final String NAME = "Courtyard";
	private final int PLUS_CARDS = 3;

	@Override
	public void accept(CardVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public Collection<CardAction> buildActionList() {
		LinkedList<CardAction> cardActions = new LinkedList<CardAction>();
		
		cardActions.add(new PlusCardAction(PLUS_CARDS));
		cardActions.add(new CourtyardAction());
		
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